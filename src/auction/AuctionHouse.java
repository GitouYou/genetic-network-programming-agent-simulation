package auction;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class AuctionHouse extends Agent {
	private ArrayList<BiddingAgent> bidders;
	private ArrayList<Auction> auctions;
	
	class AuctionHouseBehavior extends CyclicBehaviour {
		public AuctionHouseBehavior(Agent a) {
			super(a);
		}
		
		public void action() {
			ACLMessage msg = blockingReceive();

			String[] msgParts = msg.getContent().split("-");
			if (msgParts[0].equals("Client")) {
				//clientCommunication(msgParts, msg);
			}
			else if (msgParts[0].equals("Shop")) {
				//shopCommunication(msgParts, msg);
			}
		}
	}
	
	@Override
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		
		bidders = new ArrayList<BiddingAgent>();
		auctions = new ArrayList<Auction>();
		
		sd.setType("AuctionHouse");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		AuctionHouseBehavior c = new AuctionHouseBehavior(this);
		addBehaviour(c);
	}
}
