package genetic;

import java.util.ArrayList;

import auction.Auction;
import bidders.BiddingAgent.Attitude;

public class DecisionTree {
	private ArrayList<Node> nodes;
	private Node currentNode;

	public DecisionTree() {
		nodes = new DecisionTreeGenerator().getGeneratedTree();
		currentNode = nodes.get(0);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public int makeDecision(Auction auction, Attitude attitude) {
		do {
			currentNode = currentNode.getNextNode(auction, attitude);
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
