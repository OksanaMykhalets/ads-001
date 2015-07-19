import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static Graph graph;
	public static int maximalWordsChain;
	public static ArrayList<HashSet<String>> allWords;

	static {
		graph = new Graph();
		allWords = new ArrayList<HashSet<String>>();
		for (int i = 0; i <= 50; i++) {
			allWords.add(new HashSet<String>());
		}
	}

	public static void main(String[] args) {
		readVerticesFromFile();
		createGraph();
		findMaximalWordChain();
		writeResultToFile();
	}

	public static void readVerticesFromFile() {
		try {
			Scanner scanner = new Scanner(new File("wchain.in"));
			int numberOfVertices = scanner.nextInt();
			while (scanner.hasNext()) {
				String inputWord = scanner.next();
				allWords.get(inputWord.length()).add(inputWord);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void createGraph() {
		for (int i = 50; i > 1; i--) {
			findAllSubstrings(i);
		}
	}

	public static void findAllSubstrings(int upperBucketIndex) {
		HashSet<String> upperBucket = allWords.get(upperBucketIndex);
		HashSet<String> lowerBucket = allWords.get(upperBucketIndex - 1);
		for (String upperBucketWord : upperBucket) {
			for (int i = 0; i < upperBucketWord.length(); i++) {
				StringBuilder sb = new StringBuilder(upperBucketWord);
				String lowerBucketWord = sb.deleteCharAt(i).toString();
				if (lowerBucket.contains(lowerBucketWord)) {
					Vertex upperVertex;
					Vertex lowerVertex;
					if (graph.vertices.containsKey(upperBucketWord)) {
						upperVertex = graph.vertices.get(upperBucketWord);
					} else {
						upperVertex = new Vertex();
						graph.vertices.put(upperBucketWord, upperVertex);
					}
					if (graph.vertices.containsKey(lowerBucketWord)) {
						lowerVertex = graph.vertices.get(lowerBucketWord);
					} else {
						lowerVertex = new Vertex();
						lowerVertex.isDerived = true;
						graph.vertices.put(lowerBucketWord, lowerVertex);
					}
					if (!upperVertex.derivedVertices.contains(lowerVertex)) {
						upperVertex.derivedVertices.add(lowerVertex);
					}
				}
			}
		}
	}

	public static void findMaximalWordChain() {
		ArrayList<Vertex> verticesWithoutInboundEdges = new ArrayList<Vertex>(
				graph.vertices.values());
		Iterator<Vertex> iterator = verticesWithoutInboundEdges.iterator();
		while (iterator.hasNext()) {
			Vertex vertex = iterator.next();
			if (vertex.isDerived) {
				iterator.remove();
			}
		}
		if (verticesWithoutInboundEdges.size() == 0) {
			maximalWordsChain = 1;
		} else {
			for (Vertex vertex : verticesWithoutInboundEdges) {
				findMaximalWordChainForOneVertex(vertex);
			}
		}

	}

	public static void findMaximalWordChainForOneVertex(Vertex startVertex) {
		ArrayList<Vertex> currentLayerVertices = new ArrayList<Vertex>();
		ArrayList<Vertex> nextLayerVertices = new ArrayList<Vertex>();
		currentLayerVertices.add(startVertex);
		int localMaximalWordsChain = 0;

		while (currentLayerVertices.size() > 0) {
			localMaximalWordsChain++;
			for (Vertex vertex : currentLayerVertices) {
				for (Vertex derivedVertex : vertex.derivedVertices) {
					nextLayerVertices.add(derivedVertex);
				}
			}
			currentLayerVertices = nextLayerVertices;
			nextLayerVertices = new ArrayList<Vertex>();
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
