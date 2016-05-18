package genetic.judgment;

import genetic.AuctionInfo;
import genetic.DecisionNode;

public class JN5 extends JudgmentNode {
	public JN5(DecisionNode destination1, DecisionNode destination2) {
		super(destination1, destination2);
	}
	
	@Override
	public DecisionNode getDecision(AuctionInfo state) {
		return null;
	}
}
