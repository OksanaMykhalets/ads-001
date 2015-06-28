import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static Graph graph;
	public static LinkedList<Vertex> result;

	public static void main(String[] args) {
		readGraphFromFile();
		topologicalSorting();
		writeResultToFile();
	}

	public static void readGraphFromFile() {
		try {
			Scanner sc = new Scanner(new File("govern.in"));
			graph = new Graph();

			int verticesCounter = 0;

			while (sc.hasNext()) {
				String startVertexStringLabel = sc.next();
				String endVertexStringLabel = sc.next();

				Vertex startVertex;
				Vertex endVertex;
				if (!graph.getVertices().containsKey(startVertexStringLabel)) {
					startVertex = new Vertex();
					startVertex.setStringLabel(startVertexStringLabel);
					startVertex.setIntegerLabel(verticesCounter);
					verticesCounter++;
					graph.getVertices()
							.put(startVertexStringLabel, startVertex);
				} else {
					startVertex = graph.getVertices().get(
							startVertexStringLabel);
				}

				if (!graph.getVertices().containsKey(endVertexStringLabel)) {
					endVertex = new Vertex();
					endVertex.setStringLabel(endVertexStringLabel);
					endVertex.setIntegerLabel(verticesCounter);
					verticesCounter++;
					graph.getVertices().put(endVertexStringLabel, endVertex);
				} else {
					endVertex = graph.getVertices().get(endVertexStringLabel);
				}

				Edge edge = new Edge();
				edge.setStartVertex(startVertex);
				edge.setEndVertex(endVertex);
				graph.getEdges().add(edge);
				startVertex.getOutboundEdges().add(edge);

			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}
	
	public static void topologicalSorting() {
		LinkedList<Vertex> verticesWithoutInboundEdges = new LinkedList<Vertex>(
				graph.getVertices().values());
		Iterator<Edge> iterator = graph.getEdges().iterator();
		while (iterator.hasNext()) {
			Vertex endVertex = iterator.next().getEndVertex();
			verticesWithoutInboundEdges.remove(endVertex);
		}
		dfs(verticesWithoutInboundEdges);

	}

	public static void dfs(
			LinkedList<Vertex> verticesWithoutInboundEdges) {

		LinkedList<Vertex> stack = verticesWithoutInboundEdges;
		result = new LinkedList<>();
		boolean[] isVisited = new boolean[graph.getVertices().size()];

		while (stack.size() > 0) {
			Vertex currentVertex = stack.peek();
			isVisited[currentVertex.getIntegerLabel()] = true;

			Iterator<Edge> iterator = currentVertex.getOutboundEdges()
					.iterator();
			boolean hasUnvisitedVertex = false;
			while (iterator.hasNext()) {
				Vertex neighboringVertex = iterator.next().getEndVertex();
				if (!isVisited[neighboringVertex.getIntegerLabel()]) {
					hasUnvisitedVertex = true;
					stack.push(neighboringVertex);
					break;
				}
			}
			if (!hasUnvisitedVertex) {
				result.add(currentVertex);
				stack.pop();
			}
		}
	}
	
	public static void writeResultToFile() {
        writeResultToFile("govern.out");
    }

    public static void writeResultToFile(String fileName) {
        File outputFile = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write("");
            for (Vertex vertex : result) {
            	fileWriter.append(vertex.getStringLabel() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Can't save result.");
            System.exit(42);
        }
    }

}
