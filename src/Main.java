import auction.AuctionHouse;
import bidders.GnpBiddingAgent;
import bidders.StandardBiddingAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		
		Profile p = new ProfileImpl();
		ContainerController cc = rt.createMainContainer(p);
		
		try {
			/*AgentController rma = cc.createNewAgent("RMA", "jade.tools.rma.rma", null);
			rma.start();*/
			
			AgentController auctionHouse = cc.acceptNewAgent("AuctionHouse", new AuctionHouse());
			auctionHouse.start();
			
			AgentController gnp1 = cc.acceptNewAgent("bidder-gnp-1", new GnpBiddingAgent());
			
			AgentController std1 = cc.acceptNewAgent("bidder-std-1", new StandardBiddingAgent());
			
			gnp1.start();
			
			std1.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}
