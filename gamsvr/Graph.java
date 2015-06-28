import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private List<Vertex> vertices;

	private List<Edge> edges;

	public Graph() {
	}

	public Graph(int numberOfVertices) {
		this.vertices = new ArrayList<Vertex>(numberOfVertices);
		for (int i = 0; i < numberOfVertices; i++) {
			Vertex vertex = new Vertex();
			vertex.setLabel(i);
			this.vertices.add(vertex);
		}
		this.edges = new LinkedList<Edge>();
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}
