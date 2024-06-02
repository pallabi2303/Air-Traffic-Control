import java.util.*;

class Airport {
    String name;

    public Airport(String name) {
        this.name = name;
    }
}

class Edge {
    Airport source;
    Airport destination;
    int weight;

    public Edge(Airport source, Airport destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class AirTrafficGraph {
    private Map<Airport, List<Edge>> graph;

    public AirTrafficGraph() {
        this.graph = new HashMap<>();
    }

    public void addAirport(Airport airport) {
        graph.put(airport, new ArrayList<>());
    }

    public void addRoute(Airport source, Airport destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        graph.get(source).add(edge);
    }

    public List<Edge> getRoutes(Airport airport) {
        return graph.get(airport);
    }

    public Set<Edge> primMST(Airport root) {
        Set<Edge> mst = new HashSet<>();
        Set<Airport> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);

        visited.add(root);
        pq.addAll(graph.get(root));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Airport current = edge.destination;

            if (!visited.contains(current)) {
                visited.add(current);
                mst.add(edge);
                pq.addAll(graph.get(current));
            }
        }

        return mst;
    }
}

public class AirTrafficNetwork {
    public static void main(String[] args) {
        // Create airports
        Airport airportA = new Airport("Delhi");
        Airport airportB = new Airport("Bengaluru");
        Airport airportC = new Airport("Chennai");
        Airport airportD = new Airport("Bhubaneswar");
        
        // Create an instance of the air traffic graph
        AirTrafficGraph graph = new AirTrafficGraph();

        // Add airports to the graph
        graph.addAirport(airportA);
        graph.addAirport(airportB);
        graph.addAirport(airportC);
        graph.addAirport(airportD);

        // Add routes (connections) with weights (flight time)
        graph.addRoute(airportA, airportB, 3); // Flight time from A to B is 3 hours
        graph.addRoute(airportB, airportC, 1); // Flight time from B to C is 1 hours
        graph.addRoute(airportC, airportD, 2); // Flight time from C to D is 2 hours
        graph.addRoute(airportB, airportD, 2); // Flight time from B to D is 2 hours
        graph.addRoute(airportA, airportD, 2); // Flight time from A to D is 2 hours
        // Select a starting airport as the root node
        Airport root = airportA;

        // Compute the Minimum Spanning Tree (MST) using Prim's algorithm
        Set<Edge> mst = graph.primMST(root);

        // Print the MST edges
        System.out.println("Minimum Spanning Tree (MST):");
        for (Edge edge : mst) {
            System.out.println(edge.source.name + " to " + edge.destination.name + ": " + edge.weight + " hours");
        }
    }
}
