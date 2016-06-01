package genetic;

import java.util.ArrayList;

import auction.Auction;
import bidders.BiddingAgent.Attitude;

public class DecisionTree {
	private ArrayList<Node> nodes;
	private Node currentNode;
	private boolean disabled;

	public DecisionTree() {
		nodes = new DecisionTreeGenerator().getGeneratedTree();
		currentNode = nodes.get(0);
		disabled = false;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public int makeDecision(Auction auction, Attitude attitude) {
		if (disabled) return 0;
		
		int i = 0;
		
		do {
			currentNode = currentNode.getNextNode(auction, attitude);
			++i;
			if (i == 10) {
				disabled = true;
				return 0;
			}
		} while (currentNode.getBidIncrease() == -1);
		
		return currentNode.getBidIncrease();
	}
	
	public void restartTree() {
		currentNode = nodes.get(0);
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (Node node : nodes) output += node + "\n";
		
		return output;
	}
}
