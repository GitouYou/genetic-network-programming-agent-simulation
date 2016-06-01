package auction;

import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import charts.BarChart;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AuctionHouse extends Agent {
	private static final long serialVersionUID = 4594153245533339995L;
	private static final int NUM_AGENTS = 40;
	private static final int NUM_AUCTIONS = 1;

	private ArrayList<Auction> auctions;
	private int currentAuction;
	private ArrayList<AID> bidders;   
	private double highestBidValue = 0;
	private AID highestBidAid = null;
	private int receivedBids;
	public static ArrayList<ArrayList<Integer>> auctionStats = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<Integer> goodStats = new ArrayList<Integer>();

	private class StartBehavior extends CyclicBehaviour {
		private static final long serialVersionUID = 5337730145098279751L;

		public StartBehavior(Agent a) {
			super(a);
		}

		public void action() {
			ACLMessage msg = blockingReceive();

			String[] msgParts = msg.getContent().split("_");
			if (msgParts[0].equals("Bidder")) {
				ACLMessage reply = msg.createReply();
				String replyContent = "AuctionHouse_";

				if (msgParts[1].equals("Register")) {
					bidders.add(msg.getSender());
					reply.setContent(replyContent + "RegisterSuccess");
					reply.setPerformative(ACLMessage.CONFIRM);
					//System.out.println("[" + getLocalName() + "] MSG: " + reply.getContent());
					send(reply);
					System.out.println("Bidder " + msg.getSender().getLocalName() + " entered the auction house.");

					if (bidders.size() == NUM_AGENTS) {
						addBehaviour(new RunAuctionBehavior(AuctionHouse.this));
						startBidding();
						removeBehaviour(this);
					}
				}
			}
		}
	}

	private class RunAuctionBehavior extends CyclicBehaviour {
		private static final long serialVersionUID = -2714043724363600710L;

		public RunAuctionBehavior(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage msg = blockingReceive();

			String[] msgParts = msg.getContent().split("_");

			if (!msgParts[0].equals("Bidder")) return;

			if (msgParts[1].equals("Bid")) {
				double bid = Double.parseDouble(msgParts[2]);
				AID bidder = msg.getSender();

				if (bid > highestBidValue) {
					highestBidValue = bid;
					highestBidAid = bidder;
				}

				++receivedBids;
				if (receivedBids == NUM_AGENTS) advanceTimeStep();
			}
		}

		private void advanceTimeStep() {
			Auction auction = auctions.get(currentAuction);
			int currentGood = auction.getCurrentGoodNumber();
			auction.advanceTimeStep();
			int newGood = auction.getCurrentGoodNumber();
			if (currentGood != newGood) {
				endGood();
			}
			else {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

				for (AID bidder : bidders) {
					msg.addReceiver(bidder);
				}

				String msgContent = "AuctionHouse_NextBid_" + highestBidValue;
				msg.setContent(msgContent);
				//System.out.println("[" + getLocalName() + "] MSG: " + msg.getContent());
				send(msg);

				receivedBids = 0;

				//addBehaviour(new StartBehavior(AuctionHouse.this));
				startBidding();
				//removeBehaviour(this);
			}
		}

		private void endGood() {
			AID winner = highestBidAid;

			System.out.println("Auction " + currentAuction + " good " + auctions.get(currentAuction).getCurrentGoodNumber() + " won by " + winner.getLocalName() + " for " + highestBidValue);
			
			//Good stats to create charts - [goodNumber, sdt(0)/gnp(1), winnerBid]
			goodStats.add(auctions.get(currentAuction).getCurrentGoodNumber());
			String[] nameParts = (winner.getLocalName().split("-"));
			if(nameParts[1].equals("std")){
				goodStats.add(0);
			}else {
				goodStats.add(1);
			}
			goodStats.add((int)highestBidValue);
			System.out.println(goodStats);
			auctionStats.add((ArrayList<Integer>) goodStats.clone());
			goodStats.clear();
			System.out.println(auctionStats);
			
					
			receivedBids = 0;
			highestBidValue = 0;
			highestBidAid = null;

			Auction auction = auctions.get(currentAuction);
			if (auction.getCurrentGoodNumber() >= 10) {
				endAuction();
			}
			else {			
				//addBehaviour(new StartBehavior(AuctionHouse.this));
				startBidding();
				//removeBehaviour(this);
			}
		}

		private void endAuction() {
			//auctionStats.add(goodStats);
			
			//System.out.println(auctionStats);
			
			++currentAuction;
			
			if (currentAuction < NUM_AUCTIONS) {
				//addBehaviour(new StartBehavior(AuctionHouse.this));
				startBidding();
			} else {
				final BarChart bar = new BarChart("Auction "+currentAuction);
		        bar.pack();
		        RefineryUtilities.centerFrameOnScreen(bar);
		        bar.setVisible(true);
			}
			
			//removeBehaviour(this);
		}
	}

	@Override
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());

		auctions = new ArrayList<Auction>();
		for (int i = 0; i < NUM_AUCTIONS; ++i) auctions.add(new Auction());
		currentAuction = 0;
		bidders = new ArrayList<AID>();
		receivedBids = 0;

		sd.setType("AuctionHouse");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		StartBehavior b = new StartBehavior(this);
		addBehaviour(b);
	}

	private void startBidding() {
		// StartBidding_totalTimeSteps_currentTimeStep_currentGoodNumber_commonPrice
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);

		for (AID bidder : bidders) {
			msg.addReceiver(bidder);
		}

		String msgContent = "AuctionHouse_StartBidding_";
		Auction auction = auctions.get(currentAuction);
		msgContent += auction.getTotalTimeSteps() + "_";
		msgContent += auction.getCurrentTimeStep() + "_";
		msgContent += auction.getCurrentGoodNumber() + "_";
		msgContent += auction.getCurrentGood().getCommonPrice();
		msg.setContent(msgContent);
		send(msg);
		//System.out.println("[" + getLocalName() + "] MSG: " + msg.getContent());
	}
}
