package genetic.judgment;

import java.util.Random;

import auction.Auction;
import bidders.BiddingAgent.Attitude;
import genetic.Node;

public class JN4 extends JudgmentNode {
	private final boolean[] decisions = new boolean[Attitude.values().length];
	
	public JN4() {
		for (Attitude att : Attitude.values()) {
			decisions[att.ordinal()] = new Random().nextBoolean();
		}
	}

	@Override
	public Node makeDecision(Auction auction, Attitude attitude) {
		checkDestinationNodes();

		return decisions[attitude.ordinal()] ? destination1 : destination2;
	}
}
