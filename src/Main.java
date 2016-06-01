import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

import auction.AuctionHouse;
import bidders.GnpBiddingAgent;
import bidders.StandardBiddingAgent;

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

			ArrayList<AgentController> agents = new ArrayList<AgentController>();
			for (int i = 1; i <= 10; ++i) {
				agents.add(cc.acceptNewAgent("bidder-gnp-" + i, new GnpBiddingAgent()));
				agents.add(cc.acceptNewAgent("bidder-std-" + i, new StandardBiddingAgent()));
			}

			/*try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/

			for (AgentController agent : agents) {
				agent.start();
			}
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		/*
        PieChart pie = new PieChart("Comparison", "Which operating system are you using?");
        pie.pack();
        pie.setVisible(true);
        final BarChart bar = new BarChart("Bar Chart Demo");
        bar.pack();
        RefineryUtilities.centerFrameOnScreen(bar);
        bar.setVisible(true);
		 */
	}
}
