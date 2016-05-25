package auction;
import java.util.Random;

import genetic.DecisionTree;
import jade.core.Agent;

public class BiddingAgent extends Agent {
	public enum Attitude { ATT1, ATT2, ATT3 };
	
	private final Attitude attitude;
	private final DecisionTree decisionTree;
	
	public BiddingAgent() {
		attitude = Attitude.values()[new Random().nextInt(Attitude.values().length)];
		decisionTree = new DecisionTree();
	}
}
