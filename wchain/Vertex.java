import java.util.ArrayList;

public class Vertex {
	
	public String label;
	public ArrayList<Vertex> derivedVertices;
	public boolean isDerived;

	public Vertex() {
		this.derivedVertices = new ArrayList<Vertex>();
	}

}
