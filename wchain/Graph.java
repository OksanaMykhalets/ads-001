import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private ArrayList<ArrayList<Vertex>> vertices;

	private List<Edge> edges;

	public Graph() {
		this.vertices = new ArrayList<ArrayList<Vertex>>();
		for (int i = 0; i <= 50; i++) {
			this.vertices.add(new ArrayList<Vertex>());
		}
		this.edges = new LinkedList<Edge>();
	}

	public ArrayList<ArrayList<Vertex>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<ArrayList<Vertex>> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}
