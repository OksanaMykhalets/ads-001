import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private ArrayList<HashMap<String, Vertex>> vertices;

	private List<Edge> edges;

	public Graph() {
		this.vertices = new ArrayList<HashMap<String, Vertex>>();
		for (int i=0; i<=50; i++) {
			this.vertices.add(new HashMap<String, Vertex>());
		}
		this.edges = new LinkedList<Edge>();
	}

	public ArrayList<HashMap<String, Vertex>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<HashMap<String, Vertex>> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}
