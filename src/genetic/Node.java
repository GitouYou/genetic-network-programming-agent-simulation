package genetic;

import auction.Auction;
import bidders.BiddingAgent.Attitude;

public abstract class Node {
	public abstract int getBidIncrease();
	
	public abstract Node getNextNode(Auction auction, Attitude attitude);
	
	public abstract void addDestination(Node destination);
	
	@Override
	public abstract String toString();
}
