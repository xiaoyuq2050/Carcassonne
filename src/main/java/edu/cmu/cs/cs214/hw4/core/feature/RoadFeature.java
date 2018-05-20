package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.NeighborEdgeBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.RoadFeatureBean;

/**
 * RoadFeature: representation of road segment or aggregation of road segments.
 * 
 * @author xiaoyuq
 *
 */
public class RoadFeature implements Feature {
	private boolean complete;
	private boolean scored;
	private Map<Player, Integer> followers;
	private Set<Position> coveringPos;
	private List<Integer> neighborAngle;
	private Set<Position> neighborEdge;
	private Set<Position> allNeighborEdge;
	private static final Enum<TypeBean> TYPE = TypeBean.road;

	/**
	 * Construct road feature (city segment on a tile) with parsed road feature
	 * bean.
	 * 
	 * @param roadFB
	 *            road feature bean.
	 */
	public RoadFeature(RoadFeatureBean roadFB) {
		complete = false;
		scored = false;
		followers = new HashMap<Player, Integer>();
		coveringPos = new HashSet<Position>();
		neighborAngle = new ArrayList<Integer>();
		neighborEdge = new HashSet<Position>();
		allNeighborEdge = new HashSet<Position>();
		MapToAngle newMap = new MapToAngle();

		for (NeighborEdgeBean neighboredgebean : roadFB.getNeighborEdges()) {
			neighborAngle.add(newMap.getAngleFromEdge(neighboredgebean));
		}
	}

	@Override
	public void rotateAngle(int rotateAngle) {
		for (int i = 0; i < neighborAngle.size(); i++) {
			neighborAngle.set(i, neighborAngle.get(i) + rotateAngle);
		}
	}

	@Override
	public void setPosition(int x, int y) {
		coveringPos.add(new Position(x, y));
		for (int i = 0; i < neighborAngle.size(); i++) {
			neighborEdge.add(new Position((int) Math.round(x + Math.cos(Math.toRadians(neighborAngle.get(i)))),
					(int) Math.round(y + Math.sin(Math.toRadians(neighborAngle.get(i))))));
			allNeighborEdge.add(new Position((int) Math.round(x + Math.cos(Math.toRadians(neighborAngle.get(i)))),
					(int) Math.round(y + Math.sin(Math.toRadians(neighborAngle.get(i))))));
		}
	}

	/**
	 * When two features are adjacent, merge them into one feature.
	 * 
	 * @param oldRoadF
	 *            the old road feature on the board
	 * @return the merged feature
	 */
	public RoadFeature mergeTwoFeatures(RoadFeature oldRoadF) {
		RoadFeature mergedFeature = oldRoadF;
		mergedFeature.addCoverPos(this.coveringPos);
		mergedFeature.addNeighborEdge(this.neighborEdge);
		mergedFeature.sumNeighborEdge(this.allNeighborEdge);
		mergedFeature.addFollower(this.followers);
		return mergedFeature;
	}

	private void addCoverPos(Set<Position> newCoverPos) {
		coveringPos.addAll(newCoverPos);
	}

	private void sumNeighborEdge(Set<Position> newAllNeighborEdge) {
		allNeighborEdge.addAll(newAllNeighborEdge);
	}
	
	private void addNeighborEdge(Set<Position> newNeighborEdge) {
		List<Position> diffNeighborEdge = new ArrayList<Position>(newNeighborEdge);
		List<Position> commonNeighborEdge = new ArrayList<Position>(newNeighborEdge);
		commonNeighborEdge.retainAll(neighborEdge);
		diffNeighborEdge.removeAll(commonNeighborEdge);
		neighborEdge.removeAll(commonNeighborEdge);
		neighborEdge.addAll(diffNeighborEdge);
	}

	private void addFollower(Map<Player, Integer> newFollowers) {
		for (Map.Entry<Player, Integer> entry: newFollowers.entrySet()) {
			if (this.followers.containsKey(entry.getKey()) && this.followers.get(entry.getKey()) != null) {
				this.followers.put(entry.getKey(), this.followers.get(entry.getKey()) + entry.getValue());
			}
			this.followers.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Set<Position> getCoverPos() {
		return coveringPos;
	}

	@Override
	public List<Integer> getNeighborAngle() {
		return neighborAngle;
	}

	@Override
	public Set<Position> getNeighborEdge() {
		return neighborEdge;
	}

	/**
	 * Get all the neighbor edge that road feature has.
	 * @return set of all neighbor edges
	 */
	public Set<Position> getAllNeighborEdge() {
		return allNeighborEdge;
	}
	
	@Override
	public boolean isComplete() {
		return complete;
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
		if (!scored) {
			if (neighborEdge.size() == 0) {
				complete = true;
				System.out.println("road complete");
				updateScore();
				scored = true;
				for (Map.Entry<Player, Integer> entry: followers.entrySet()) {
					entry.getKey().retrieveFollower(entry.getValue());
				}
			}
		}
	}

	/**
	 * If road feature hasn't been scored, update score the followers and score
	 * information. This method will be called either by updateStatus() (when the
	 * feature is complete) or by updateFinalScore() (when the game is over).
	 */
	public void updateScore() {
		if (!scored) {
			if (followers.size() > 0) {
				for (Player player : findDominantPlayer()) {
					player.addScore(coveringPos.size());
				}
			}
		}
	}

	private List<Player> findDominantPlayer() {
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
