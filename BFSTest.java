package test;

import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 * <p>
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     * <p>
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * Tests if program throws exception
     * when an invalid city is possed into the BFS algorithm
     */
    @Test
    public void cityDNE() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities1.csv", "data/transport1.csv");
        Exception falseCity = assertThrows(IllegalArgumentException.class,
                () -> travelCtrl.mostDirectRoute("Boston", "New York")); //Assert.assertEquals Illegal Argument
    }

    /**
     * Tests if the program knows how to handle a single vertex graph
     */
    @Test
    public void oneCityGraph() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities3.csv", "data/transport3.csv");
        assertEquals(travelCtrl.mostDirectRoute("Dallas", "Dallas").size(), 1);
    }

    /**
     * Tests that an empty list is returned when there is no available path between cities
     */
    @Test
    public void pathDNE() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities2.csv", "data/transport2.csv");
        assertEquals(travelCtrl.mostDirectRoute("Durham", "Boston").size(), 0);
    }

    /**
     * Tests a simple BFS
     */
    @Test
    public void basicBFS() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities2.csv", "data/transport2.csv");
        assertEquals(travelCtrl.mostDirectRoute("Washington", "Providence").size(), 2);
        assertEquals(travelCtrl.mostDirectRoute("Washington", "Providence")
                        .get(0).getSource().toString(), "Chicago");
    }

    /**
     * Tests that BFS can handle multiple direct routes and arbritarily
     * choses one
     */
    @Test
    public void multiplePaths() {
        TravelController travelCtrl = new TravelController();
        travelCtrl.load("data/cities5.csv", "data/transport5.csv");
        assertEquals(travelCtrl.mostDirectRoute("EmWool", "Ratty").size(), 2);
    }

}
