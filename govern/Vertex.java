import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private String stringLabel;

	private Integer integerLabel;

	private List<Edge> outboundEdges;

	public Vertex() {
		this.outboundEdges = new LinkedList<Edge>();
	}

	public String getStringLabel() {
		return stringLabel;
	}

	public void setStringLabel(String stringLabel) {
		this.stringLabel = stringLabel;
	}

	public Integer getIntegerLabel() {
		return integerLabel;
	}

	public void setIntegerLabel(Integer integerLabel) {
		this.integerLabel = integerLabel;
	}

	public List<Edge> getOutboundEdges() {
		return outboundEdges;
	}

	public void setOutboundEdges(List<Edge> outboundEdges) {
		this.outboundEdges = outboundEdges;
	}

}
