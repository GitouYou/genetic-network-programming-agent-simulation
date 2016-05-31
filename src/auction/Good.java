package auction;

import java.util.Random;

public class Good {
	private final double commonPrice; // From 50 to 200
	
	public Good() {
		commonPrice = new Random().nextInt(151) + 50;
	}
	
	public Good(double commonPrice) {
		this.commonPrice = commonPrice;
	}
	
	public double getCommonPrice() {
		return commonPrice;
	}
}
