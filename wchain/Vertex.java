import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private String label;

	private List<Edge> outboundEdges;

	public Vertex() {
		this.outboundEdges = new LinkedList<Edge>();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String stringLabel) {
		this.label = stringLabel;
	}

	public List<Edge> getOutboundEdges() {
		return outboundEdges;
	}

	public void setOutboundEdges(List<Edge> outboundEdges) {
		this.outboundEdges = outboundEdges;
	}

}
