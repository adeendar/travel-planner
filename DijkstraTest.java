package test;

import org.junit.Assert;
import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IDijkstra;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 * <p>
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    private TravelGraph travelGraph;
    private City dc;
    private City philly;
    private City ny;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     * <p>
     * TODO: create more setup methods!
     */


    private void createTravelGraph3() {
        this.travelGraph = new TravelGraph();
        this.dc = new City("dc");
        this.philly = new City("philly");
        this.ny = new City("ny");

        this.travelGraph.addVertex(this.dc);
        this.travelGraph.addVertex(this.philly);
        this.travelGraph.addVertex(this.ny);

        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.ny, TransportType.PLANE, 300, 200));
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.philly, TransportType.BUS, 30, 210));
        this.travelGraph.addEdge(this.philly, new Transport(this.philly, this.ny, TransportType.TRAIN, 40, 150));
        this.travelGraph.addEdge(this.philly, new Transport(this.philly, this.dc, TransportType.TRAIN, 40, 150));


    }

    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * Tests a simple Dijkstra implementation
     */
    @Test
    public void testCities2() { //needs some exception if the path does not exist.
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities2.csv", "data/transport2.csv");
        List<Transport> route1 = travelCtrl.cheapestRoute("Boston", "Durham"); //vice versa throws an error
        List<Transport> route2 = travelCtrl.fastestRoute("Boston", "Durham");
        Assert.assertEquals(route1, route2);
    }

    /**
     * Tests if an edge doesn't exist, then empty path is returned
     */
    @Test
    public void testEdgeDoesntExist() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities2.csv", "data/transport2.csv");
        Assert.assertEquals(travelCtrl.fastestRoute("Durham", "Boston"), new LinkedList<>());
        Assert.assertEquals(travelCtrl.cheapestRoute("Durham", "Boston"), new LinkedList<>());
    }

    /**
     * Tests for Dijsktra's on the same vertex (returns empty)
     */
    @Test
    public void sameVertex() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities2.csv", "data/transport2.csv");
        Assert.assertEquals(travelCtrl.cheapestRoute("Boston", "Boston"), new LinkedList<>());
    }

    /**
     * Tests when origin and desination are the same
     */
    @Test
    public void testSingleCity() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities3.csv", "data/transport3.csv");
        Assert.assertEquals(travelCtrl.cheapestRoute("Dallas", "Dallas"), new LinkedList<>());
    }

    /**
     * Tests when multiple routes are given for the same path
     */
    @Test
    public void multiple() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities5.csv", "data/transport5.csv");
        Assert.assertEquals(travelCtrl.getTotalPrice
                (travelCtrl.cheapestRoute("BlueRoom", "Andrews")), 13, DELTA);
    }
}
