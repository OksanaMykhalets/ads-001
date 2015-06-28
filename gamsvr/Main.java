import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static Graph graph;
	public static long[] latencies;
	public static List<Integer> possibleServersVertices;
	public static long finalLatency = Long.MAX_VALUE;

	public static void main(String[] args) {
		readGraphFromFile();
		findMinimalLatency();
		writeResultToFile();
	}

	public static void readGraphFromFile() {
		try {
			Scanner sc = new Scanner(new File("gamsvr.in"));

			String string = sc.nextLine();
			String[] parts = string.split(" ");
			int numberOfVertices = Integer.parseInt(parts[0]);
			int numberOfEdges = Integer.parseInt(parts[1]);

			graph = new Graph(numberOfVertices + 1);

			string = sc.nextLine();
			parts = string.split(" ");
			ArrayList<Integer> clients = new ArrayList<>();
			for (int i = 0; i < parts.length; i++) {
				clients.add(Integer.valueOf(parts[i]));
			}

			possibleServersVertices = new ArrayList<>();
			for (int i = 1; i <= numberOfVertices; i++) {
				if (!clients.contains(i)) {
					possibleServersVertices.add(i);
				}
			}

			for (int i = 0; i < numberOfEdges; i++) {
				int vertexLabel1 = sc.nextInt();
				int vertexLabel2 = sc.nextInt();
				int edgeWeight = sc.nextInt();

				Vertex startVertex = graph.getVertices().get(vertexLabel1);
				Vertex endVertex = graph.getVertices().get(vertexLabel2);

				Edge edge = new Edge();
				edge.setStartVertex(startVertex);
				edge.setEndVertex(endVertex);
				edge.setWeight(edgeWeight);
				graph.getEdges().add(edge);
				startVertex.getOutboundEdges().add(edge);

				Edge reverseEdge = new Edge();
				reverseEdge.setStartVertex(endVertex);
				reverseEdge.setEndVertex(startVertex);
				reverseEdge.setWeight(edgeWeight);
				graph.getEdges().add(reverseEdge);
				endVertex.getOutboundEdges().add(reverseEdge);

			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void findMinimalLatency() {
		for (int possibleServer : possibleServersVertices) {
			dijkstra(graph.getVertices().get(possibleServer));
			for (int i = 0; i < latencies.length; i++) {
				if (possibleServersVertices.contains(i)) {
					latencies[i] = 0;
				}
			}

			long maximalLatency = 0;
			for (long latency : latencies) {
				if (latency > maximalLatency) {
					maximalLatency = latency;
				}
			}
			if (maximalLatency < finalLatency) {
				finalLatency = maximalLatency;
			}
		}
	}

	public static void dijkstra(Vertex startVertex) {
		long infinity = Long.MAX_VALUE;
		latencies = new long[graph.getVertices().size()];
		for (int i = 0; i < graph.getVertices().size(); i++) {
			latencies[i] = infinity;
		}
		latencies[startVertex.getLabel()] = 0;

		List<Vertex> unprocessedVertices = new ArrayList<>(graph.getVertices());

		unprocessedVertices.remove(graph.getVertices().get(0));
		latencies[0] = 0;

		while (unprocessedVertices.size() > 0) {
			Vertex currentVertex = unprocessedVertices.get(0);
			for (int i = 0; i < unprocessedVertices.size(); i++) {
				Vertex nextVertex = unprocessedVertices.get(i);
				if (latencies[nextVertex.getLabel()] < latencies[currentVertex
						.getLabel()]) {
					currentVertex = nextVertex;
				}
			}

			Iterator<Edge> iterator = currentVertex.getOutboundEdges()
					.iterator();
			while (iterator.hasNext()) {
				Edge currentEdge = iterator.next();
				Vertex neighboringVertex = currentEdge.getEndVertex();
				if (unprocessedVertices.contains(neighboringVertex)) {
					if (latencies[neighboringVertex.getLabel()] > latencies[currentVertex
							.getLabel()] + currentEdge.getWeight()) {
						latencies[neighboringVertex.getLabel()] = latencies[currentVertex
								.getLabel()] + currentEdge.getWeight();
					}
				}
			}
			unprocessedVertices.remove(currentVertex);
		}

	}

	public static void writeResultToFile() {
		writeResultToFile("gamsvr.out");
	}

	public static void writeResultToFile(String fileName) {
		File outputFile = new File(fileName);
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write("" + finalLatency);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Can't save result.");
			System.exit(42);
		}
	}

}
