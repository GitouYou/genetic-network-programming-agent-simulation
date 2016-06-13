package genetic.judgment;

import auction.Auction;
import bidders.BiddingAgent.Attitude;
import genetic.Node;

public abstract class JudgmentNode extends Node {
	protected Node destination1, destination2;
	
	public JudgmentNode() {}
	
	public Node getDestination1() {
		return destination1;
	}
	
	public Node getDestination2() {
		return destination2;
	}
	
	@Override
	public void addDestination(Node destination) {
		if (destination1 == null) destination1 = destination;
		else if (destination2 == null) destination2 = destination;
	}
	
	protected void checkDestinationNodes() {
		if (destination1 == null || destination2 == null) {
			throw new NullPointerException("Destination nodes haven't been initialized!");
		}
	}
	
	public int getBidIncrease() {
		return -1;
	}
	
	public final Node getNextNode(Auction auction, Attitude attitude) {
		return makeDecision(auction, attitude);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + hashCode() + ")\n   -> " + destination1.getClass().getSimpleName() + " (" + destination1.hashCode() + "), " + destination2.getClass().getSimpleName() + " (" + destination2.hashCode() + ")";
	}
	
	protected abstract Node makeDecision(Auction state, Attitude attitude);
}
