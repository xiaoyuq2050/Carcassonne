// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class CloisterFeatureBean {
	private NeighborPosBean[] neighborPos;

	public NeighborPosBean[] getNeighborPos() {
		return neighborPos.clone();
	}
	
	public void setNeighborPos(NeighborPosBean[] neighborPos) {
		this.neighborPos = neighborPos.clone();
	}
}
