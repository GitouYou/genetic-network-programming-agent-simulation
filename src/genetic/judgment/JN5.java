package genetic.judgment;

import java.util.Random;

import auction.Auction;
import bidders.BiddingAgent.Attitude;
import genetic.Node;

public class JN5 extends JudgmentNode {
	private final double timeStepPercentageThreshold = (new Random().nextInt(91) + 5) / 100.; // Percentage of passed time steps after which to take destination2
	
	@Override
	public Node makeDecision(Auction auction, Attitude attitude) {
		checkDestinationNodes();
		
		double timeStepPercentage = auction.getCurrentTimeStep() / (auction.getTotalTimeSteps() * 10.);
		
		return (timeStepPercentage <= timeStepPercentageThreshold) ? destination1 : destination2;
	}
}
