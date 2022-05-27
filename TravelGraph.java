package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> cityMap;

    public TravelGraph() {
        this.cityMap = new HashMap<>();
    }

    /**
     * Adds a city (vertex) to the Travel Graph
     *
     * @param vertex the City to be added
     */
    @Override
    public void addVertex(City vertex) {
        this.cityMap.put(vertex.toString(), vertex);
    }


    /**
     * Adds a transport (edge) to the Travel Graph
     *
     * @param origin the origin of the edge.
     * @param edge   - the transport to be added
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        this.cityMap.get(origin.toString()).addOut(edge); //added implementation
    }

    /**
     * Gets all the verticies ia given graph
     * @return the vertcies in a graph
     */
    @Override
    public Set<City> getVertices() {
        return new HashSet<>(this.cityMap.values());
    }

    /**
     * Gets the source city of an given edge
     * @param edge the edge
     * @return source city
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * Gets the target city of an given edge
     * @param edge the edge
     * @return target city
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * Gets the outgoing routes from a given input city
     * @param fromVertex the vertex
     * @return the routes available to travel from the vertex
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * Gets a city by name
     *
     * @param cityName the name of the city as a string
     * @return the City object associated with the name
     */
    public City getCity(String cityName) {
        if (this.cityMap.containsKey(cityName)) {
            return this.cityMap.get(cityName);
        } else throw new IllegalArgumentException(cityName + " does not exist.");
    }


}