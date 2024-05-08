package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	private Map<V, Map<V, Integer>> weightedGraph;
	private HashSet<V> visitedSet;
	
	
	
	
	
	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		this.weightedGraph = new HashMap<>();
		this.observerList = new ArrayList<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		this.weightedGraph.put(vertex, new HashMap<>());
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		for (V curr : this.weightedGraph.keySet()) {
			if (curr.equals(vertex)) {
				return true;
			}
		}
		return false;
	}
	

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if (!(this.containsVertex(from)) || !(this.containsVertex(to)) ||
				weight < 0) {
			throw new IllegalArgumentException();
		}
		this.weightedGraph.get(from).put(to, weight);
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if (!(weightedGraph.containsKey(from)) 
				|| !(weightedGraph.containsKey(to))) {
			throw new IllegalArgumentException();
		}
		Map<V, Integer> neighbors = weightedGraph.get(from);
		if (neighbors.containsKey(to)) {
			return neighbors.get(to);
		} else {
			return null;
		}
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		visitedSet = new HashSet<>();
		Queue<V> queue = new LinkedList<>();
		queue.add(start);
		while (!(queue.isEmpty())) {
			V vertex = queue.remove();
			if (!(visitedSet.contains(vertex))) {
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(vertex);
				}
				visitedSet.add(vertex);
				if (vertex.equals(end)) {
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				for (V neighbor : weightedGraph.get(vertex).keySet()) {
					if (!(visitedSet.contains(neighbor))) {
						queue.add(neighbor);
					}
				}
			}
		}
	}
	
	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		visitedSet = new HashSet<>();
		Stack<V> stack = new Stack<>();
		stack.push(start);
		while(!(stack.isEmpty())) {
			V vertex = stack.pop();
			if (!(visitedSet.contains(vertex))) {
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(vertex);
				}
				visitedSet.add(vertex);
				if (vertex.equals(end)) {
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				for (V neighbor : weightedGraph.get(vertex).keySet()) {
					if (!(visitedSet.contains(neighbor))) {
						stack.push(neighbor);
					}
				}
			}
		}
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		// Distances to each vertex: path cost
        Map<V, Integer> distances = new HashMap<>();
        for (V vertex : weightedGraph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        // Path from each vertex
        Map<V, V> paths = new HashMap<>();
        // Finished vertices
        Set<V> finished = new HashSet<>();
        // Vertices to be processed, ordered by distance
        Queue<V> queue = 
        		new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.add(start);
        while (!queue.isEmpty()) {
            V current = queue.remove();
            if (finished.contains(current)) {
                continue;
            }
            finished.add(current);
            // Notify added to finishedSet
    		for (GraphAlgorithmObserver<V> observer : observerList) {
    			observer.notifyDijkstraVertexFinished(current, 
    					distances.get(current));
    		}
         // Update the distances of the neighbors of the current vertex
            Map<V, Integer> neighbors = weightedGraph.get(current);
            for (V neighbor : neighbors.keySet()) {
                int weight = neighbors.get(neighbor);
                int distance = distances.get(current) + weight;
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    paths.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        // Construct the path from start to end using the paths map
        List<V> path = new ArrayList<>();
        V current = end;
        while (current != null) {
            path.add(current);
            current = paths.get(current);
        }
        Collections.reverse(path);
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(path);
		}
    }
}
	

