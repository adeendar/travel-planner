package sol;

import src.IBFS;
import src.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * The BFS class implements a breadth-first search algorithim that is used
 * to find the most direct route from a vertex  to another
 *
 * @param <V>
 * @param <E>
 */
public class BFS<V, E> implements IBFS<V, E> {

    /**
     * This method implements a classic BFS algorithim keeping
     * track of visited Edges and returning that in a list if a path
     * is found from the start vertex to the end vertex
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        LinkedList<E> toCheck = new LinkedList<E>(graph.getOutgoingEdges(start)); //keeps track of edges to visit
        HashSet<E> visited = new HashSet<>(); //used to track visited edges
        HashMap<V, E> cameFrom = new HashMap<>(); //used to store kv pairs of the vertex and its corresponding edge
        LinkedList<E> path = new LinkedList<>();


        while (!toCheck.isEmpty()) {
            E checkingEdge = toCheck.removeFirst();
            V checkingCity = graph.getEdgeTarget(checkingEdge);

            if (!cameFrom.containsKey(checkingCity)) {
                cameFrom.put(checkingCity, checkingEdge);
            }

            if (end.equals(checkingCity)) {
                while (!start.equals(graph.getEdgeSource(checkingEdge))) {
                    path.addFirst(checkingEdge);
                    checkingEdge = cameFrom.get(graph.getEdgeSource(checkingEdge));
                }
                path.addFirst(checkingEdge);
                return path;
            }

            visited.add(checkingEdge);
            for (E neighboringEdge : graph.getOutgoingEdges(checkingCity)) {
                if (!visited.contains(neighboringEdge)) {
                    toCheck.addLast(neighboringEdge);
                }
            }
        }
        return path;
    }
}

