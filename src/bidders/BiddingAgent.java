package bidders;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import auction.Auction;
import auction.Good;
import jade.core.Agent;

public abstract class BiddingAgent extends Agent {
	private static final long serialVersionUID = -523845231023002319L;
	
	public enum Attitude { ATT1, ATT2, ATT3 };

	protected Map<Good, GoodValuation> goodsInterested;

	public BiddingAgent() {
		goodsInterested = new HashMap<Good, GoodValuation>();
	}

	public final void addGood(Good good) {
		Attitude attitude = Attitude.values()[new Random().nextInt(Attitude.values().length)];
		double privatePrice = -1;
		
		goodsInterested.put(good, new GoodValuation(attitude, privatePrice));
	}

	protected final double makeBid(Good good, Auction auction) {
		Attitude attitudeTowardsGood = goodsInterested.get(good).getAttitude();

		switch (attitudeTowardsGood) {
		case ATT1:
			return desperateBid(good, auction);
		case ATT2:
			return bargainBid(good, auction);
		case ATT3:
			return desperateBargainBid(good, auction);
		default:
			return 0;
		}
	}

	protected abstract double desperateBid(Good good, Auction auction);
	protected abstract double bargainBid(Good good, Auction auction);
	protected abstract double desperateBargainBid(Good good, Auction auction);
}
