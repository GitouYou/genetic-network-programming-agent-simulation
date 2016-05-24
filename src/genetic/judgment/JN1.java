package genetic.judgment;

import java.util.Random;

import auction.Auction;
import auction.BiddingAgent.Attitude;
import genetic.DecisionNode;

public class JN1 extends JudgmentNode {
	private final int goodNumberThreshold;
	
	public JN1(DecisionNode destination1, DecisionNode destination2) {
		super(destination1, destination2);
		goodNumberThreshold = new Random().nextInt(9); // Good number after which to take destination2
	}
	
	@Override
	public DecisionNode makeDecision(Auction auction, Attitude attitude) {
		return (auction.getCurrentGoodNumber() <= goodNumberThreshold) ? destination1 : destination2;
	}
}
