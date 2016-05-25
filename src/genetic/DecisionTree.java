package genetic;

import java.util.ArrayList;

public class DecisionTree {
	private ArrayList<Node> nodes;

	public DecisionTree() {
		DecisionTreeGenerator generator = new DecisionTreeGenerator();
		nodes = generator.getGeneratedTree();
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (Node node : nodes) output += node + "\n";
		
		return output;
	}
}
