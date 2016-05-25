package genetic;

import java.util.Random;

public class ProcessingNode extends Node {
	private final int bidIncrease;
	private Node nextNode;
	
	public ProcessingNode() {
		bidIncrease = new Random(11).nextInt();
	}
	
	public ProcessingNode(Node nextNode) {
		this();
		this.nextNode = nextNode;
	}
	
	public int getBidIncrease() {
		return bidIncrease;
	}
	
	public Node getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + hashCode() + ")\n   -> " + nextNode.getClass().getSimpleName() + " (" + nextNode.hashCode() + ")";
	}
}
