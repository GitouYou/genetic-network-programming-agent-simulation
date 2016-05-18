package genetic;

import java.util.ArrayList;

public class DecisionTree {
	private ArrayList<DecisionNode> nodes;
	
	public DecisionTree(ArrayList<DecisionNode> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<DecisionNode> getNodes() {
		return nodes;
	}
}
