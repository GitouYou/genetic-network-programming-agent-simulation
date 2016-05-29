package auction;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AuctionHouse extends Agent {
	private static final long serialVersionUID = 4594153245533339995L;
	
	private ArrayList<Auction> auctions;
	
	class AuctionHouseBehavior extends CyclicBehaviour {
		private static final long serialVersionUID = 5337730145098279751L;

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
