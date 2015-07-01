import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Map.Entry;

public class Main {

	public static Graph graph;
	public static ArrayList<Vertex> allVertices;
	public static int maximalWordsChain;

	public static void main(String[] args) {
		readVerticesFromFile();
		createAllGraphsEdges();
		getAllVertices();
		findMaximalWordChain();
		writeResultToFile();
	}

	public static void readVerticesFromFile() {
		try {
			Scanner scanner = new Scanner(new File("wchain.in"));
			graph = new Graph();
			int numberOfVertices = scanner.nextInt();
			while (scanner.hasNext()) {
				String vertexLabel = scanner.next();
				Vertex vertex = new Vertex();
				vertex.setLabel(vertexLabel);
				graph.getVertices().get(vertexLabel.length())
						.put(vertexLabel, vertex);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void createAllGraphsEdges() {
		for (int i = 50; i > 1; i--) {
			createLocalGraphsEdges(i, i - 1);
		}
	}

	public static void createLocalGraphsEdges(int upperMapIndex,
			int lowerMapIndex) {
		
		Iterator<Entry<String, Vertex>> upperIterator = graph.getVertices()
				.get(upperMapIndex).entrySet().iterator();
		while (upperIterator.hasNext()) {
			Vertex upperVertex = upperIterator.next().getValue();
			Iterator<Entry<String, Vertex>> lowerIterator = graph.getVertices()
					.get(lowerMapIndex).entrySet().iterator();
			while (lowerIterator.hasNext()) {
				Vertex lowerVertex = lowerIterator.next().getValue();
				for (int i = 0; i < upperVertex.getLabel().length(); i++) {
					StringBuilder sb = new StringBuilder(upperVertex.getLabel());
					String possibleCombination = sb.deleteCharAt(i).toString();
					if (lowerVertex.getLabel().equals(possibleCombination)) {
						Edge edge = new Edge();
						edge.setStartVertex(upperVertex);
						edge.setEndVertex(lowerVertex);
						graph.getEdges().add(edge);
						upperVertex.getOutboundEdges().add(edge);
					}
				}
			}
		}
	}

	public static void getAllVertices() {
		allVertices = new ArrayList<Vertex>();
		for (int i = 1; i <= 50; i++) {
			Iterator<Entry<String, Vertex>> verticesIterator = graph
					.getVertices().get(i).entrySet().iterator();
			while (verticesIterator.hasNext()) {
				Vertex vertex = verticesIterator.next().getValue();
				allVertices.add(vertex);
			}
		}
	}

	public static void findMaximalWordChain() {
		ArrayList<Vertex> verticesWithoutInboundEdges = allVertices;
		Iterator<Edge> iterator = graph.getEdges().iterator();
		while (iterator.hasNext()) {
			Vertex endVertex = iterator.next().getEndVertex();
			verticesWithoutInboundEdges.remove(endVertex);
		}
		for (Vertex vertex : verticesWithoutInboundEdges) {
			findMaximalWordChainForOneVertex(vertex);
		}
	}

	public static void findMaximalWordChainForOneVertex(Vertex startVertex) {
		LinkedList<Vertex> currentLayerVertices = new LinkedList<>();
		LinkedList<Vertex> nextLayerVertices = new LinkedList<>();

		currentLayerVertices.add(startVertex);
		int localMaximalWordsChain = 0;

		while (currentLayerVertices.size() > 0) {
			localMaximalWordsChain++;
			Iterator<Vertex> vertexIterator = currentLayerVertices.iterator();
			while (vertexIterator.hasNext()) {
				Vertex vertex = vertexIterator.next();
				Iterator<Edge> edgeIterator = vertex.getOutboundEdges()
						.iterator();
				while (edgeIterator.hasNext()) {
					Vertex nextVertex = edgeIterator.next().getEndVertex();
					nextLayerVertices.add(nextVertex);
				}
			}
			currentLayerVertices = nextLayerVertices;
			nextLayerVertices = new LinkedList<>();
		}

		if (localMaximalWordsChain > maximalWordsChain) {
			maximalWordsChain = localMaximalWordsChain;
		}
	}

	public static void writeResultToFile() {
		writeResultToFile("wchain.out");
	}

	public static void writeResultToFile(String fileName) {
		File outputFile = new File(fileName);
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write("" + maximalWordsChain);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Can't save result.");
			System.exit(42);
		}
	}

}
