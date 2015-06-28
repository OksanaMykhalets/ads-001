import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private int label;

	private List<Edge> outboundEdges;

	public Vertex() {
		this.outboundEdges = new LinkedList<Edge>();
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public List<Edge> getOutboundEdges() {
		return outboundEdges;
	}

	public void setOutboundEdges(List<Edge> outboundEdges) {
		this.outboundEdges = outboundEdges;
	}

}
