package genetic.judgment;

import java.util.Random;

import auction.Auction;
import auction.BiddingAgent.Attitude;
import genetic.DecisionNode;

public class JN5 extends JudgmentNode {
	private final float timeStepPercentageThreshold;
	
	public JN5(DecisionNode destination1, DecisionNode destination2) {
		super(destination1, destination2);
		timeStepPercentageThreshold = (new Random().nextInt(91) + 5) / 100.f; // Percentage of passed time steps after which to take destination2
	}
	
	@Override
	public DecisionNode makeDecision(Auction auction, Attitude attitude) {
		float timeStepPercentage = (float) (auction.getCurrentTimeStep() / auction.getTotalTimeSteps() * 10);
		
		return (timeStepPercentage <= timeStepPercentageThreshold) ? destination1 : destination2;
	}
}
