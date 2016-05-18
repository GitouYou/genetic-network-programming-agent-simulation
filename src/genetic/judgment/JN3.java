package genetic.judgment;

import genetic.AuctionInfo;
import genetic.DecisionNode;

public class JN3 extends JudgmentNode {
	public JN3(DecisionNode destination1, DecisionNode destination2) {
		super(destination1, destination2);
	}
	
	@Override
	public DecisionNode getDecision(AuctionInfo state) {
		return null;
	}
}
