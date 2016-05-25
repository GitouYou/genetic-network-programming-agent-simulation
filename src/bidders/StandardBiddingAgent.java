package bidders;

import auction.Auction;
import auction.Good;

public class StandardBiddingAgent extends BiddingAgent {
	@Override
	protected double desperateBid(Good good, Auction auction) {
		double kde = .7;
		double betade = 5;
		
		return b(auction, good, kde, betade);
	}

	@Override
	protected double bargainBid(Good good, Auction auction) {
		double kdb = .3;
		double betadb = .3;
		
		return b(auction, good, kdb, betadb);
	}

	@Override
	protected double desperateBargainBid(Good good, Auction auction) {
		double krt = .6;
		double betart = 4;
		
		return b(auction, good, krt, betart);
	}
	
	private double b(Good good, Auction auction, double k, double beta) {
		int T = auction.getTotalTimeSteps();
		int t = auction.getCurrentTimeStep() % T;
		int P = auction.getCurrentBidPrice(good);
		double PP = goodsInterested.get(good).getPrivatePrice();
		
		return (double) t / T * P + alpha(k, beta, t, T) * (PP - (double) t / T * P);
	}
	
	private double alpha(double k, double beta, int t, int T) {
		return k + (1 - k) * Math.pow((double) t / T, 1 / beta);
	}
}
