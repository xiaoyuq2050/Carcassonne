// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class RoadFeatureBean {
	private NeighborEdgeBean[] neighborEdges;
	
	public NeighborEdgeBean[] getNeighborEdges() {
		return neighborEdges.clone();
	}
	
	public void setNeighborEdge(NeighborEdgeBean[] neighborEdges) {
		this.neighborEdges = neighborEdges.clone();
	}
}
