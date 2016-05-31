package genetic;

import genetic.judgment.JN1;
import genetic.judgment.JN2;
import genetic.judgment.JN4;
import genetic.judgment.JN5;
import genetic.judgment.JudgmentNode;

import java.util.ArrayList;
import java.util.Random;

class DecisionTreeGenerator {
	private static int NUM_JUDGMENT_NODES = 9;
	private static int NUM_PROCESSING_NODES = 10;
	
	private ArrayList<JudgmentNode> judgmentNodes = new ArrayList<JudgmentNode>();
	private ArrayList<ProcessingNode> processingNodes = new ArrayList<ProcessingNode>();

	public DecisionTreeGenerator() {
		for (int i = 0; i < NUM_JUDGMENT_NODES; ++i) {
			JudgmentNode judgmentNode;
			int judgmentNodeType = new Random().nextInt(4);

			switch (judgmentNodeType) {
			case 0:
				judgmentNode = new JN1();
				break;
			case 1:
				judgmentNode = new JN2();
				break;
			case 2:
				judgmentNode = new JN4();
				break;
			case 3:
				judgmentNode = new JN5();
				break;
			default:
				judgmentNode = null;
				break;
			}

			judgmentNodes.add(judgmentNode);
		}

		for (int i = 0; i < NUM_PROCESSING_NODES; ++i) {
			ProcessingNode processingNode = new ProcessingNode();
			processingNodes.add(processingNode);
		}

		ArrayList<Node> allNodes = new ArrayList<Node>();
		allNodes.addAll(judgmentNodes);
		allNodes.addAll(processingNodes);

		// Complete the incomplete judgment nodes
		ArrayList<JudgmentNode> incompleteJudgmentNodes = getIncompleteJudgmentNodes();
		while (!incompleteJudgmentNodes.isEmpty()) {
			for (JudgmentNode node : incompleteJudgmentNodes) {
				int randomNode = new Random().nextInt(allNodes.size());

				Node chosenDestination = allNodes.get(randomNode);
				if (chosenDestination != node) {
					node.addDestination(chosenDestination);
				}
			}

			incompleteJudgmentNodes = getIncompleteJudgmentNodes();
		}

		// Complete the incomplete processing nodes
		ArrayList<ProcessingNode> incompleteProcessingNodes = getIncompleteProcessingNodes();
		while (!incompleteProcessingNodes.isEmpty()) {
			for (ProcessingNode node : incompleteProcessingNodes) {
				int randomNode = new Random().nextInt(allNodes.size());

				Node chosenDestination = allNodes.get(randomNode);
				if (chosenDestination != node) {
					node.setNextNode(chosenDestination);
				}
			}
			
			incompleteProcessingNodes = getIncompleteProcessingNodes();
		}
	}

	public ArrayList<Node> getGeneratedTree() {
		ArrayList<Node> generatedTree = new ArrayList<Node>();
		generatedTree.addAll(judgmentNodes);
		generatedTree.addAll(processingNodes);
		return generatedTree;
	}

	private ArrayList<JudgmentNode> getIncompleteJudgmentNodes() {
		ArrayList<JudgmentNode> incompleteNodes = new ArrayList<JudgmentNode>();

		for (JudgmentNode node : judgmentNodes) {
			if (node.getDestination1() == null || node.getDestination2() == null) {
				incompleteNodes.add(node);
			}
		}

		return incompleteNodes;
	}

	private ArrayList<ProcessingNode> getIncompleteProcessingNodes() {
		ArrayList<ProcessingNode> incompleteNodes = new ArrayList<ProcessingNode>();

		for (ProcessingNode node : processingNodes) {
			if (node.getNextNode(null, null) == null) {
				incompleteNodes.add(node);
			}
		}

		return incompleteNodes;
	}
}
