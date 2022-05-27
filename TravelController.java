package sol;

import src.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * The TravelController class acts as the main controller part of the
 * program and contains methods to load a graph and methods to calculate
 * routes based on different factors.
 */
public class TravelController implements ITravelController<City, Transport> {

    private TravelGraph graph;

    public TravelController() {
    }

    /**
     * The load method takes in a city and transport file
     * in order to contruct a graph from given csv files.
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return a message inidcating wether or not the method has succesfully run
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        Function<Map<String, String>, Void> buildEdges = map -> {
            this.graph.addEdge(this.graph.getCity(map.get("origin")),
                    new Transport(this.graph.getCity(map.get("origin")),
                            this.graph.getCity(map.get("destination")),
                            TransportType.fromString(map.get("type")),
                            Double.parseDouble(map.get("price")),
                            Double.parseDouble(map.get("duration"))));
            return null;
        };

        try {
            parser.parseTransportation(transportFile, buildEdges);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }


        return "Successfully loaded cities and transportation files.";
    }

    /**
     * This method returns the fastest route from a given
     * soucre to its destination using time as the prioritized
     * edgeWeight for Djikstra's algorithm
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path to get from source to destination
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> findFastest = transport -> {
            return transport.getMinutes();
        };

        Dijkstra<City, Transport> fastDijkstra = new Dijkstra<>();
        return fastDijkstra.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), findFastest);

    }

    /**
     * Finds the cheapest route from two cities, prioritizing
     * price as the pirmary edge weight.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path to get from two cities
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> findCheapest = transport -> {
            return transport.getPrice();
        };

        Dijkstra<City, Transport> cheapDijkstra = new Dijkstra<>();
        return cheapDijkstra.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), findCheapest);

    }

    /**
     * Finds the most direct route from two cities using a
     * BFS algorithm
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path from one city to another
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport> BFS = new BFS<>();
        return BFS.getPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination));
    }

    /**
     * Returns the total price of a given path
     * and is used for testing Dijkstra's
     * @param path the path to tally prices
     * @return the total price
     */
    public double getTotalPrice(List<Transport> path) {
        double price = 0;
        for (Transport edge : path) {
            price += edge.getPrice();
        }
        return price;
    }
}
