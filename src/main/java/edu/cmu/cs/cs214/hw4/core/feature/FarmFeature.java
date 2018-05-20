package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.FarmFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.NeighborEdgeBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.VertexBean;

/**
 * FarmFeature: representation of farm segment or aggregation of farm segments.
 * 
 * @author xiaoyuq
 *
 */
public class FarmFeature implements Feature {
	private Map<Player, Integer> followers;
	private Set<Position> coveringPos;
	private List<Integer> neighborAngle;
	private Set<Position> neighborEdge;
	private List<Integer> vertexAngle;
	private Set<Position> vertexPos;
	private static final Enum<TypeBean> TYPE = TypeBean.farm;

	/**
	 * Construct farm feature (farm segment on a tile) with parsed farm feature
	 * bean.
	 * 
	 * @param farmFB
	 *            farm feature bean.
	 */
	public FarmFeature(FarmFeatureBean farmFB) {
		followers = new HashMap<Player, Integer>();
		coveringPos = new HashSet<Position>();
		neighborAngle = new ArrayList<Integer>();
		neighborEdge = new HashSet<Position>();
		vertexAngle = new ArrayList<Integer>();
		vertexPos = new HashSet<Position>();
		MapToAngle newMap = new MapToAngle();

		for (NeighborEdgeBean neighboredgebean : farmFB.getNeighborPos()) {
			neighborAngle.add(newMap.getAngleFromEdge(neighboredgebean));
		}

		for (VertexBean vertexBean : farmFB.getVertexPos()) {
			vertexAngle.add(newMap.getAngleFromVertex(vertexBean));
		}
	}

	@Override
	public void rotateAngle(int rotateAngle) {
		for (int i = 0; i < neighborAngle.size(); i++) {
			neighborAngle.set(i, neighborAngle.get(i) + rotateAngle);
		}
		for (int i = 0; i < vertexAngle.size(); i++) {
			vertexAngle.set(i, vertexAngle.get(i) + rotateAngle);
		}
	}

	@Override
	public void setPosition(int x, int y) {
		coveringPos.add(new Position(x, y));
		for (int i = 0; i < neighborAngle.size(); i++) {
			neighborEdge.add(new Position((int) Math.round(x + Math.cos(Math.toRadians(neighborAngle.get(i)))),
					(int) Math.round(y + Math.sin(Math.toRadians(neighborAngle.get(i))))));
		}
		for (int j = 0; j < vertexAngle.size(); j++) {
			vertexPos
					.add(new Position((int) Math.round(x + Math.sqrt(2) * Math.cos(Math.toRadians(vertexAngle.get(j)))),
							(int) Math.round(y + Math.sqrt(2) * Math.sin(Math.toRadians(vertexAngle.get(j))))));
		}
	}

	@Override
	public List<Integer> getNeighborAngle() {
		return neighborAngle;
	}

	/**
	 * VertexAngle Getter.
	 * 
	 * @return vertexAngle of the feature.
	 */
	public List<Integer> getVertexAngle() {
		return vertexAngle;
	}

	private void addCoverPos(Set<Position> newCoverPos) {
		coveringPos.addAll(newCoverPos);
	}

	private void addNeighborEdge(Set<Position> newNeighborEdge) {
		neighborEdge.addAll(newNeighborEdge);
	}

	private void addVertexPos(Set<Position> newVertexPos) {
		vertexPos.addAll(newVertexPos);
	}

	@Override
	public Set<Position> getCoverPos() {
		return coveringPos;
	}

	@Override
	public Set<Position> getNeighborEdge() {
		return neighborEdge;
	}

	/**
	 * VertexPos Getter.
	 * 
	 * @return vertexPos of the feature.
	 */
	public Set<Position> getVertexPos() {
		return vertexPos;
	}

	/**
	 * When two features are adjacent, merge them into one feature.
	 * 
	 * @param oldFarmF
	 *            the old farm feature on the board
	 * @return the merged feature
	 */
	public FarmFeature mergeTwoFeatures(FarmFeature oldFarmF) {
		FarmFeature mergedFeature = oldFarmF;
		mergedFeature.addCoverPos(this.coveringPos);
		mergedFeature.addNeighborEdge(this.neighborEdge);
		mergedFeature.addVertexPos(this.vertexPos);
		mergedFeature.addFollower(this.followers);
		return mergedFeature;
	}

	private void addFollower(Map<Player, Integer> newFollowers) {
		for (Map.Entry<Player, Integer> entry: newFollowers.entrySet()) {
			if (this.followers.containsKey(entry.getKey())) {
				this.followers.put(entry.getKey(), this.followers.get(entry.getKey()) + entry.getValue());
			}
			this.followers.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public boolean placeFollower(Player player) {
		if (followers.size() > 0 || !player.haveUnusedFollower()) {
			return false;
		}
		player.placeFollower();
		followers.put(player, 1);
		return true;
	}

	@Override
	public void updateStatus() {
		return;
	}

	/**
	 * When calculating the final scores, find the dominant player on the farm
	 * feature.
	 * 
	 * @return list of dominant players
	 */
	public List<Player> findDominantPlayer() {
		List<Player> dominantPlayer = new ArrayList<Player>();
		int followerMax = 0;
		for (Integer num : followers.values()) {
			if (num > followerMax) {
				followerMax = num;
			}
		}
		for (Map.Entry<Player, Integer> entry: followers.entrySet()) {
			if (entry.getValue() == followerMax) {
				dominantPlayer.add(entry.getKey());
			}
		}
		return dominantPlayer;
	}

	@Override
	public Map<Player, Integer> getFollowers() {
		return followers;
	}

	@Override
	public Enum<TypeBean> getType() {
		return TYPE;
	}

}
