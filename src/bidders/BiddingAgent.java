package bidders;
import java.util.Random;

import auction.Auction;
import auction.Good;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public abstract class BiddingAgent extends Agent {
	private static final long serialVersionUID = -523845231023002319L;

	public enum Attitude { DESPERATE, BARGAIN, REMAINING_TIME };

	private AID auctionHouseAid;
	//protected Map<Good, GoodValuation> goodsInterested;
	protected Auction currentAuction;
	protected Good currentGood;
	protected GoodValuation currentGoodValuation;

	private double fitnessValue;

	private class AuctionHouseRegisterBehavior extends OneShotBehaviour {
		private static final long serialVersionUID = 3360748631414660645L;

		public AuctionHouseRegisterBehavior(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			// Search AuctionHouse agent
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd1 = new ServiceDescription();

			sd1.setType("AuctionHouse");
			template.addServices(sd1);
			try {
				DFAgentDescription[] result = DFService.search(BiddingAgent.this, template);
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				auctionHouseAid = result[0].getName();
				msg.addReceiver(auctionHouseAid);

				msg.setContent("Bidder_Register");
				send(msg);
				System.out.println("[" + getLocalName() + "] MSG: " + msg.getContent());

				BidBehavior b = new BidBehavior(BiddingAgent.this);
				addBehaviour(b);
			} catch (FIPAException e) {
				e.printStackTrace();
			}
		}
	}

	private class BidBehavior extends CyclicBehaviour {
		private static final long serialVersionUID = 6195319282123524692L;

		public BidBehavior(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage msg = blockingReceive();

			String[] msgParts = msg.getContent().split("_");

			if (!msgParts[0].equals("AuctionHouse")) return;

			ACLMessage reply = msg.createReply();

			if (msgParts[1].equals("StartBidding")) {
				// StartBidding_totalTimeSteps_currentTimeStep_currentGoodNumber_commonPrice
				int oldGoodNumber;
				double highestBid;
				try {
					oldGoodNumber = currentAuction.getCurrentGoodNumber();
					highestBid = currentAuction.getHighestBid();
				} catch (NullPointerException e) {
					oldGoodNumber = -1;
					highestBid = 0;
				}
				int newGoodNumber = Integer.parseInt(msgParts[4]);
				double commonPrice = Double.parseDouble(msgParts[5]);
				if (oldGoodNumber != newGoodNumber) {
					highestBid = 0;
					changeCurrentGood(new Good(commonPrice));
				}
				currentAuction = new Auction(Integer.parseInt(msgParts[2]), Integer.parseInt(msgParts[3]), newGoodNumber, highestBid);

				restartBidding();
				bid(reply);
			}
			else if (msgParts[1].equals("NextBid")) {
				currentAuction.advanceTimeStep();
				currentAuction.setHighestBid(Double.parseDouble(msgParts[2]));
			}
			else if (msgParts[1].equals("YouWin!")) {
				double finalPrice = Double.parseDouble(msgParts[2]);
				double adding = currentGoodValuation.getPrivatePrice() - finalPrice;

				switch (currentGoodValuation.getAttitude()) {
				case DESPERATE:
					adding += currentGood.getCommonPrice() * .3;
					break;
				case BARGAIN:
					adding += currentGood.getCommonPrice() * .1;
					break;
				case REMAINING_TIME:
					adding += currentGood.getCommonPrice() * .2;
					break;
				}

				fitnessValue += adding;
			}
			else if (msgParts[1].equals("FitnessValue")) {
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("Bidder_FitnessValue_" + getFitnessValue());
				send(reply);
			}
			else if (msgParts[1].equals("Mutate")) {
				mutate();
			}
		}

		private void bid(ACLMessage reply) {
			String replyContent = "Bidder_Bid_";
			replyContent += makeBid(currentGood, currentAuction);

			reply.setContent(replyContent);
			reply.setPerformative(ACLMessage.PROPOSE);
			send(reply);
			System.out.println("[" + getLocalName() + "] MSG: " + reply.getContent());
		}
	}

	public BiddingAgent() {
		//goodsInterested = new HashMap<Good, GoodValuation>();
	}

	public final void changeCurrentGood(Good good) {
		Attitude attitude = Attitude.values()[new Random().nextInt(Attitude.values().length)];
		int privatePricePercentage = new Random().nextInt(31) + 85;
		double privatePrice = good.getCommonPrice() * (privatePricePercentage / 100.);

		//goodsInterested.put(good, new GoodValuation(attitude, privatePrice));
		currentGood = good;
		currentGoodValuation = new GoodValuation(attitude, privatePrice);
	}

	protected final double makeBid(Good good, Auction auction) {
		//Attitude attitudeTowardsGood = goodsInterested.get(good).getAttitude();
		Attitude attitudeTowardsGood = currentGoodValuation.getAttitude();
		double currentPrice = auction.getHighestBid();
		double priceIncrease;

		switch (attitudeTowardsGood) {
		case DESPERATE:
			priceIncrease = desperateBid(good, auction);
			break;
		case BARGAIN:
			priceIncrease = bargainBid(good, auction);
			break;
		case REMAINING_TIME:
			priceIncrease = remainingTimeBid(good, auction);
			break;
		default:
			priceIncrease = 0;
			break;
		}

		double newPrice = currentPrice + priceIncrease;

		if (newPrice > currentGoodValuation.getPrivatePrice()) return currentGoodValuation.getPrivatePrice();
		else return newPrice;
	}

	protected abstract double desperateBid(Good good, Auction auction);
	protected abstract double bargainBid(Good good, Auction auction);
	protected abstract double remainingTimeBid(Good good, Auction auction);
	protected abstract void restartBidding();
	protected abstract void mutate();

	@Override
	protected abstract void setup();

	protected void setup(String bidderType) {
		fitnessValue = 0;
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());

		// Add client to service
		sd.setType(bidderType);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// Create behavior
		AuctionHouseRegisterBehavior b = new AuctionHouseRegisterBehavior(this);
		addBehaviour(b);
	}

	private double getFitnessValue() {
		return fitnessValue;
	}
}
