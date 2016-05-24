package genetic.judgment;

import java.util.Random;

import auction.Auction;
import auction.BiddingAgent.Attitude;
import genetic.DecisionNode;

public class JN4 extends JudgmentNode {
	private final DecisionNode[] decisions = new DecisionNode[Attitude.values().length];
	
	public JN4(DecisionNode destination1, DecisionNode destination2) {
		super(destination1, destination2);
		
		for (Attitude att : Attitude.values()) {
			decisions[att.ordinal()] = (new Random().nextBoolean()) ? destination1 : destination2;
		}
	}
	
	@Override
	public DecisionNode makeDecision(Auction auction, Attitude attitude) {
		return decisions[attitude.ordinal()];
	}
}
