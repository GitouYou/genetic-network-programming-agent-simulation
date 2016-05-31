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

	public enum Attitude { ATT1, ATT2, ATT3 };

	private AID auctionHouseAid;
	//protected Map<Good, GoodValuation> goodsInterested;
	protected Auction currentAuction;
	protected Good currentGood;
	protected GoodValuation currentGoodValuation;

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
				try {
					oldGoodNumber = currentAuction.getCurrentGoodNumber();
				} catch (NullPointerException e) {
					oldGoodNumber = -1;
				}
				currentAuction = new Auction(Integer.parseInt(msgParts[2]), Integer.parseInt(msgParts[3]), Integer.parseInt(msgParts[4]));
				double commonPrice = Double.parseDouble(msgParts[5]);
				int newGoodNumber = currentAuction.getCurrentGoodNumber();
				if (oldGoodNumber != newGoodNumber) addGood(new Good(commonPrice));
				restartBidding();
				bid(reply);
			}
			else if (msgParts[1].equals("NextBid")) {
				currentAuction.advanceTimeStep();
				currentAuction.setHighestBid(Double.parseDouble(msgParts[2]));
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

	public final void addGood(Good good) {
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
		double currentPrice = auction.getHighestBid(good);
		double priceIncrease;

		switch (attitudeTowardsGood) {
		case ATT1:
			priceIncrease = desperateBid(good, auction);
			break;
		case ATT2:
			priceIncrease = bargainBid(good, auction);
			break;
		case ATT3:
			priceIncrease = desperateBargainBid(good, auction);
			break;
		default:
			priceIncrease = 0;
			break;
		}

		double newPrice = currentPrice + priceIncrease;

		if (newPrice > currentGoodValuation.getPrivatePrice()) return currentGoodValuation.getPrivatePrice();
		else return currentPrice + priceIncrease;
	}

	protected abstract double desperateBid(Good good, Auction auction);
	protected abstract double bargainBid(Good good, Auction auction);
	protected abstract double desperateBargainBid(Good good, Auction auction);
	protected abstract void restartBidding();

	@Override
	protected abstract void setup();

	protected void setup(String bidderType) {
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
}
