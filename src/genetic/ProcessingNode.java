package genetic;

import java.util.Random;

import auction.Auction;
import bidders.BiddingAgent.Attitude;

public class ProcessingNode extends Node {
	private final int bidIncrease;
	private Node nextNode;
	
	public ProcessingNode() {
		bidIncrease = new Random().nextInt(11);
	}
	
	public int getBidIncrease() {
		return bidIncrease;
	}
	
	public Node getNextNode(Auction auction, Attitude attitude) {
		return nextNode;
	}
	
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	
	@Override
	public void addDestination(Node destination) {
		setNextNode(destination);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + hashCode() + ")\n   -> " + nextNode.getClass().getSimpleName() + " (" + nextNode.hashCode() + ")";
	}
}
