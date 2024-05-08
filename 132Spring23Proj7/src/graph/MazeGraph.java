package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {


	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		// Go through the x and y, checking each side of each juncture
		// if there is not a wall, create a juncture
		// populate the maze with junctures:
		Juncture[][] junctureMatrix = 
				new Juncture[maze.getMazeWidth()][maze.getMazeHeight()];
		
		
		for (int width = 0; width < maze.getMazeWidth(); width++) {
			for (int height = 0; height < maze.getMazeHeight(); height++) {
				Juncture curr = new Juncture(width, height);
				this.addVertex(curr);
				junctureMatrix[width][height] = curr;
			}
		}
		// Loop to add edges
		for (int width = 0; width < maze.getMazeWidth(); width++) {
			for (int height = 0; height < maze.getMazeHeight(); height++) {
				if (!(maze.isWallAbove(junctureMatrix[width][height]))) {
					this.addEdge(junctureMatrix[width][height], 
							junctureMatrix[width][height - 1], 
							maze.getWeightAbove(junctureMatrix[width][height]));
				}
				if (!(maze.isWallBelow(junctureMatrix[width][height]))) {
					this.addEdge(junctureMatrix[width][height], 
							junctureMatrix[width][height + 1],
							maze.getWeightBelow(junctureMatrix[width][height]));
				}
				if (!(maze.isWallToLeft(junctureMatrix[width][height]))) {
					this.addEdge(junctureMatrix[width][height], 
						junctureMatrix[width - 1][height],
						maze.getWeightToLeft(junctureMatrix[width][height]));
				}
				if (!(maze.isWallToRight(junctureMatrix[width][height]))) {
					this.addEdge(junctureMatrix[width][height], 
						junctureMatrix[width + 1][height],
						maze.getWeightToRight(junctureMatrix[width][height]));
				}
			}
		}
	}
}
