package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.HashMap;
import java.util.Map;

import edu.cmu.cs.cs214.hw4.core.tilelistbean.NeighborEdgeBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.VertexBean;

/**
 * A Helper class to convert EdgeBean and VertexBean in different feature into angles.
 * @author xiaoyuq
 *
 */
public class MapToAngle {
	private Map<NeighborEdgeBean, Integer> edgeToAngle = new HashMap<NeighborEdgeBean, Integer>();
	private Map<VertexBean, Integer> vertexToAngle = new HashMap<VertexBean, Integer>();
	
	/**
	 * Constructor of two maps.
	 */
	public MapToAngle() {
		edgeToAngle = new HashMap<NeighborEdgeBean, Integer>();
		edgeToAngle.put(NeighborEdgeBean.up, 90);
		edgeToAngle.put(NeighborEdgeBean.left, 180);
		edgeToAngle.put(NeighborEdgeBean.down, 270);
		edgeToAngle.put(NeighborEdgeBean.right, 0);
		
		vertexToAngle.put(VertexBean.upright, 45);
		vertexToAngle.put(VertexBean.upleft, 135);
		vertexToAngle.put(VertexBean.downleft, 225);
		vertexToAngle.put(VertexBean.downright, 315);
	}
	
	/**
	 * Get angle from a specific NeighborEdgeBean.
	 * @param edge a specific NeighborEdgeBean
	 * @return corresponding angle
	 */
	public int getAngleFromEdge(NeighborEdgeBean edge) {
		return edgeToAngle.get(edge);
	}
	
	/**
	 * Get angle from a specific VertexBean.
	 * @param vertex a specific VertexBean
	 * @return corresponding angle
	 */
	public int getAngleFromVertex(VertexBean vertex) {
		return vertexToAngle.get(vertex);
	}

}
