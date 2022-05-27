package test;

import org.junit.Before;
import org.junit.Test;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 * <p>
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private TravelGraph graph;

    private City Andrews;
    private City VDub;
    private City Ratty;
    private City Sayles;
    private City BlueRoom;
    private City EmWool;

    private Transport RattyVDub;
    private Transport AndrewsSayles;
    private Transport AndrewsRatty;

    /**
     * Intializes the graph before tests are run
     */
    @Before
    public void createGraph() {
        TravelController controller = new TravelController(); //new
        System.out.println(
                controller.load("/Users/owencarson/Desktop/cs200/projects/travel-planner-adeendar-ocarson1/data/cities5.csv",
                        "/Users/owencarson/Desktop/cs200/projects/travel-planner-adeendar-ocarson1/data/transport5.csv"));

        this.graph = new TravelGraph();

        this.Andrews = new City("Andrews");
        this.VDub = new City("VDub");
        this.Ratty = new City("Ratty");
        this.Sayles = new City("Sayles");
        this.BlueRoom = new City("BlueRoom");
        this.EmWool = new City("EmWool");

        this.graph.addVertex(this.Andrews);
        this.graph.addVertex(this.VDub);
        this.graph.addVertex(this.Ratty);
        this.graph.addVertex(this.Sayles);
        this.graph.addVertex(this.BlueRoom);
        this.graph.addVertex(this.EmWool);

        this.RattyVDub = new Transport(this.Ratty, this.VDub, TransportType.BUS, 2, 10);
        this.AndrewsSayles = new Transport(this.Andrews, this.Sayles, TransportType.BUS, 10, 2);
        this.AndrewsRatty = new Transport(this.Andrews, this.Ratty, TransportType.TRAIN, 8, 6);

        this.graph.addEdge(this.Andrews, this.AndrewsRatty);
        this.graph.addEdge(this.Andrews, this.AndrewsSayles);

        this.graph.addEdge(this.Ratty, this.RattyVDub);
        this.graph.addEdge(this.Ratty, new Transport(this.Ratty, this.Sayles, TransportType.BUS, 2, 5));

        this.graph.addEdge(this.Sayles, new Transport(this.Sayles, this.Ratty, TransportType.BUS, 2, 5));
        this.graph.addEdge(this.Sayles, new Transport(this.Sayles, this.Andrews, TransportType.BUS, 10, 2));
        this.graph.addEdge(this.Sayles, new Transport(this.Sayles, this.EmWool, TransportType.TRAIN, 5, 8));

        this.graph.addEdge(this.EmWool, new Transport(this.EmWool, this.Andrews, TransportType.TRAIN, 5, 3));
        this.graph.addEdge(this.EmWool, new Transport(this.EmWool, this.Sayles, TransportType.TRAIN, 5, 8));

        this.graph.addEdge(this.BlueRoom, new Transport(this.BlueRoom, this.Sayles, TransportType.BUS, 3, 2));

    }

    /**
     * Tests if getVeritices is properly working
     */
    @Test
    public void testGetVertices() {
        // test getVertices to check this method AND addVertex
        assertEquals(this.graph.getVertices().size(), 6);
        assertTrue(this.graph.getVertices().contains(this.VDub));
        assertTrue(this.graph.getVertices().contains(this.Ratty));
        assertTrue(this.graph.getVertices().contains(this.BlueRoom));
        assertTrue(this.graph.getVertices().contains(this.Andrews));
        assertTrue(this.graph.getVertices().contains(this.Sayles));
        assertTrue(this.graph.getVertices().contains(this.EmWool));
    }

    /**
     * Testing getEdgeSource
     */
    @Test
    public void testGetEdgeSource() {
        assertEquals(this.graph.getEdgeSource(this.RattyVDub), this.Ratty);
    }

    /**
     * Testing getEdgeTarget
     */
    @Test
    public void testGetEdgeTarget() {
        assertEquals(this.graph.getEdgeTarget(this.RattyVDub), this.VDub);
    }

    /**
     * Testing getOutgoingEdges
     */
    @Test
    public void testGetOutGoingEdges() {
        assertTrue(this.graph.getOutgoingEdges(this.Andrews).contains(this.AndrewsRatty));
        assertTrue(this.graph.getOutgoingEdges(this.Andrews).contains(this.AndrewsSayles));
    }

    /**
     * Testing getCity
     */
    @Test
    public void testGetCity() {
        assertEquals(this.graph.getCity("Andrews"), this.Andrews);
    }

    /**
     * Testing getCity was cityName is invalid String
     */
    @Test
    public void cityDNE() {
        Exception falseCity = assertThrows(IllegalArgumentException.class,
                () -> this.graph.getCity("Keeney"));
    }
}
