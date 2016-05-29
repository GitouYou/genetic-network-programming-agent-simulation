package auction;

import jade.core.AID;

class HighestBid {
	private final double highestBid;
	private final AID aid;
	
	public HighestBid(double highestBid, AID aid) {
		this.highestBid = highestBid;
		this.aid = aid;
	}
	
	public double getHighestBid() {
		return highestBid;
	}
	
	public AID getAid() {
		return aid;
	}
}
