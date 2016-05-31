import org.jfree.ui.RefineryUtilities;

import charts.BarChart;
import charts.PieChart;
import genetic.DecisionTree;

public class Main {

	public static void main(String[] args) {
		/*DecisionTree tree = new DecisionTree();
		System.out.println(tree);*/
        PieChart pie = new PieChart("Comparison", "Which operating system are you using?");
        pie.pack();
        pie.setVisible(true);
        final BarChart bar = new BarChart("Bar Chart Demo");
        bar.pack();
        RefineryUtilities.centerFrameOnScreen(bar);
        bar.setVisible(true);
	}
}
