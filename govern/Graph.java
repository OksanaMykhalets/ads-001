import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

	private Map<String, Vertex> vertices;

	private List<Edge> edges;

	public Graph() {
		this.vertices = new HashMap<String, Vertex>();
		this.edges = new LinkedList<Edge>();
	}

	public Map<String, Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(Map<String, Vertex> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}
