package sol;

import src.IDijkstra;
import src.IGraph;
import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    /**
     * This method impelements a dijkstra's algoritihm by setting
     * up a priority queue and priortizing a path (if it exists)
     * on a given edgeWeight.
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight the edge to prioritize on
     * @return
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        HashMap<V, Double> vertexValues = new HashMap<>(); //hashmap pairing vertexes to number values
        HashMap<V, E> cameFrom = new HashMap<>(); //hashmap pairing vertexes to the vertexes they came from

        //initialize all the vertex values as infinity and the source value as zero
        for (V v : graph.getVertices()) {
            vertexValues.put(v, Double.MAX_VALUE);
        }
        vertexValues.put(source, 0.0);

        //compare the vertexes based upon their number value from least to greatest
        Comparator<V> rank = (vertex1, vertex2) -> {
            double rank1 = vertexValues.get(vertex1);
            double rank2 = vertexValues.get(vertex2);
            return Double.compare(rank1, rank2);
        };

        //make a priority queue with all the vertexes using the rank comparator
        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(rank);
        toCheckQueue.addAll(vertexValues.keySet());


        while (!toCheckQueue.isEmpty()) { //while there are still vertexes to check
            V checkingV = toCheckQueue.poll(); //take the first vertex in the queue out and assign it to checkingV
            for (E e : graph.getOutgoingEdges(checkingV)) { //loop through each of checkingV's edges
                double toCompare = vertexValues.get(checkingV) + edgeWeight.apply(e); //calculate the value of checkingV plus that edge
                if (toCompare < vertexValues.get(graph.getEdgeTarget(e))) {
                    vertexValues.put(graph.getEdgeTarget(e), toCompare); //if this calculation is less than the value of the edge's target, replace the target value with the calculation
                    cameFrom.put(graph.getEdgeTarget(e), e); //pair the target and the edge leading up to it in cameFrom so we can backtrack where we came from.
                }
            }
        }

        LinkedList<E> returnList = new LinkedList<>();
        returnList.addFirst(cameFrom.get(destination)); //adds the edge leading to the destination first so that we can backtrack from it
        if (returnList.get(0) != null) { //checks if the prior line actually works-- if not, then the path does not exist
            while (graph.getEdgeSource(returnList.get(0)) != source) { //while returnList doesn't include the edge leading to the source
                    returnList.addFirst(cameFrom.get(graph.getEdgeSource(returnList.get(0)))); //add the edge paired with the source of the edge in the front of the returnList
            }
            return returnList;
        }
        //in the case that the path does not exist, return an empty list
        else return new LinkedList<>();
    }
}
