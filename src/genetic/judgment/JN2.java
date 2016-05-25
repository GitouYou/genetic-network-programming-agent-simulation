package genetic.judgment;

import java.util.Random;

import auction.Auction;
import bidders.BiddingAgent.Attitude;
import genetic.Node;

public class JN2 extends JudgmentNode {
	private final float timeStepPercentageThreshold = (new Random().nextInt(91) + 5) / 100.f; // Percentage of passed time steps after which to take destination2
	
	@Override
	public Node makeDecision(Auction auction, Attitude attitude) {
		checkDestinationNodes();
		
		float timeStepPercentage = (float) (auction.getCurrentTimeStep() % auction.getTotalTimeSteps() / auction.getTotalTimeSteps());
		
		return (timeStepPercentage <= timeStepPercentageThreshold) ? destination1 : destination2;
	}
}
