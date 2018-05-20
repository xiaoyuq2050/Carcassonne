// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class FarmFeatureBean {
    private NeighborEdgeBean[] neighborEdges;
    private VertexBean[] vertexPos;
    
	public NeighborEdgeBean[] getNeighborPos() {
		return neighborEdges.clone();
	}
	
	public void setNeighborEdge(NeighborEdgeBean[] neighborEdges) {
		this.neighborEdges = neighborEdges.clone();
	}
	
	public VertexBean[] getVertexPos() {
		return vertexPos.clone();
	}
	
	public void setVertexPos(VertexBean[] vertexPos) {
		this.vertexPos = vertexPos.clone();
	}
}
