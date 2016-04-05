package ie.gmit.sw.traversers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;

public class TraversatorStats {
	private static List<Node> paths = new ArrayList<Node>();
	public static double depth = 0;
	public static double playerDepth = 0;

	public static void printStats(Node node, long time, int visitCount) {

		depth = 0;
		playerDepth = 0;
		paths.clear();
		while (node != null) {
			node = node.getParent();
			
			paths.add(node);
			if (node != null  && node.getNodeType()!=NodeType.PlayerNode) {
				//node.setNodeType(NodeType.WalkableNode);
				node.setColor(Color.yellow);
			}
			depth++;
			playerDepth++;
		}
		System.out.println("Visited " + visitCount + " nodes in " + time + "ms.");
		System.out.println("Found goal at a depth of " + String.format("%.0f", depth));
		System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f", Math.pow(visitCount, (1.00d / depth))));
		System.out.println("the size of list is: " + paths.size());
	}

	public static List<Node> getPaths() {
		Collections.reverse(paths);
		return paths;
	}
}