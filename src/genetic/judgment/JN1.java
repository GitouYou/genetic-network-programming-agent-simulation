package genetic.judgment;

import java.util.Random;

import auction.Auction;
import auction.BiddingAgent.Attitude;
import genetic.Node;

public class JN1 extends JudgmentNode {
	private final int goodNumberThreshold = new Random().nextInt(9); // Good number after which to take destination2
	
	@Override
	public Node makeDecision(Auction auction, Attitude attitude) {
		checkDestinationNodes();
		
		return (auction.getCurrentGoodNumber() <= goodNumberThreshold) ? destination1 : destination2;
	}
}
