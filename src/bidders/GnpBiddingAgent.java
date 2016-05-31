package bidders;

import auction.Auction;
import auction.Good;
import genetic.DecisionTree;

public class GnpBiddingAgent extends BiddingAgent {
	private static final long serialVersionUID = -3417985818116238286L;
	
	protected final DecisionTree decisionTree;
	
	public GnpBiddingAgent() {
		decisionTree = new DecisionTree();
		System.out.println(decisionTree);
	}
	
	@Override
	protected double desperateBid(Good good, Auction auction) {
		return decisionTree.makeDecision(auction, Attitude.ATT1);
	}
	
	@Override
	protected double bargainBid(Good good, Auction auction) {
		return decisionTree.makeDecision(auction, Attitude.ATT2);
	}
	
	@Override
	protected double desperateBargainBid(Good good, Auction auction) {
		return decisionTree.makeDecision(auction, Attitude.ATT3);
	}
	
	@Override
	protected void restartBidding() {
		decisionTree.restartTree();
	}
	
	@Override
	protected void setup() {
		setup("GnpBidder");
	}
}
