package genetic;

import java.util.ArrayList;
import java.util.Random;

import auction.Auction;
import bidders.BiddingAgent.Attitude;
import genetic.judgment.JN1;
import genetic.judgment.JN2;
import genetic.judgment.JN4;
import genetic.judgment.JN5;

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

	public void mutate() {
		Node randomNode = nodes.get(new Random().nextInt(nodes.size()));

		switch (new Random().nextInt(5)) {
		case 0:
			randomNode = new ProcessingNode();
			break;
		case 1:
			randomNode = new JN1();
			break;
		case 2:
			randomNode = new JN2();
			break;
		case 3:
			randomNode = new JN4();
			break;
		case 4:
			randomNode = new JN5();
			break;
		}

		for (int i = 0; i < 2; ++i) {
			int otherRandomNode = new Random().nextInt(nodes.size());

			Node chosenDestination = nodes.get(otherRandomNode);
			if (chosenDestination != randomNode) {
				randomNode.addDestination(chosenDestination);
			}
		}
	}

	@Override
	public String toString() {
		String output = "";

		for (Node node : nodes) output += node + "\n";

		return output;
	}
}
