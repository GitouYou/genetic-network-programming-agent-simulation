package genetic;

public class ProcessingNode extends DecisionNode {
	private int bidIncrease;
	private DecisionNode nextNode;
	
	public ProcessingNode(int bidIncrease, DecisionNode nextNode) {
		this.bidIncrease = bidIncrease;
		this.nextNode = nextNode;
	}
	
	public int getBidIncrease() {
		return bidIncrease;
	}
	
	public DecisionNode getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(DecisionNode nextNode) {
		this.nextNode = nextNode;
	}
}
