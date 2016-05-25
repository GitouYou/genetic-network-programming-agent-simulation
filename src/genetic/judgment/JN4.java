package genetic.judgment;

import java.util.Random;

import auction.Auction;
import auction.BiddingAgent.Attitude;
import genetic.Node;

public class JN4 extends JudgmentNode {
	private final Node[] decisions = new Node[Attitude.values().length];
	
	{
		for (Attitude att : Attitude.values()) {
			decisions[att.ordinal()] = (new Random().nextBoolean()) ? destination1 : destination2;
		}
	}

	@Override
	public Node makeDecision(Auction auction, Attitude attitude) {
		checkDestinationNodes();

		return decisions[attitude.ordinal()];
	}
}
