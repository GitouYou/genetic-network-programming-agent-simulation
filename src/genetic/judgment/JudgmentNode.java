package genetic.judgment;

import genetic.AuctionInfo;
import genetic.DecisionNode;

public abstract class JudgmentNode extends DecisionNode {
	protected DecisionNode destination1, destination2;
	
	public JudgmentNode(DecisionNode destination1, DecisionNode destination2) {
		this.destination1 = destination1;
		this.destination2 = destination2;
	}
	
	public DecisionNode getDestination1() {
		return destination1;
	}
	
	public void setDestination1(DecisionNode destination1) {
		this.destination1 = destination1;
	}
	
	public DecisionNode getDestination2() {
		return destination2;
	}
	
	public void setDestination2(DecisionNode destination2) {
		this.destination2 = destination2;
	}
	
	protected abstract DecisionNode makeDecision(AuctionInfo state);
}
