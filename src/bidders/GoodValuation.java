package bidders;

import bidders.BiddingAgent.Attitude;

public class GoodValuation {
	private final Attitude attitude;
	private final double privatePrice;
	
	public GoodValuation(Attitude attitude, double privatePrice) {
		this.attitude = attitude;
		this.privatePrice = privatePrice;
	}
	
	public Attitude getAttitude() {
		return attitude;
	}
	
	public double getPrivatePrice() {
		return privatePrice;
	}
}
