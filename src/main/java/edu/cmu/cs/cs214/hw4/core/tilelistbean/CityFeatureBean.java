// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class CityFeatureBean {
    private NeighborEdgeBean[] neighborEdges;
    private VertexBean[] vertexPos;
    private boolean hasBanner;

	public NeighborEdgeBean[] getNeighborEdges() {
		return neighborEdges.clone();
	}
	
	public void setNeighborEdge(NeighborEdgeBean[] neighborEdges) {
		this.neighborEdges = neighborEdges.clone();
	}
	
	public boolean hasBanner() {
		return hasBanner;
	}
	
	public void setBanner(boolean hasBanner) {
		this.hasBanner = hasBanner;
	}
	
	public VertexBean[] getVertexPos() {
		return vertexPos.clone();
	}
	
	public void setVertexPos(VertexBean[] vertexPos) {
		this.vertexPos = vertexPos.clone();
	}
}
