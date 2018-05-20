package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.CityFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.NeighborEdgeBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.VertexBean;

/**
 * CityFeature: representation of city segment or aggregation of city segments.
 * 
 * @author xiaoyuq
 *
 */
public class CityFeature implements Feature {
	private boolean complete;
	private boolean scored;
	private Map<Player, Integer> followers;
	private Set<Position> coveringPos;
	private List<Integer> neighborAngle;
	private Set<Position> neighborEdge;
	private Set<Position> allNeighborEdge;
	private List<Integer> vertexAngle;
	private Set<Position> vertexPos;
	private int banner;
	private static final Enum<TypeBean> TYPE = TypeBean.city;

	/**
	 * Construct city feature (city segment on a tile) with parsed city feature
	 * bean.
	 * 
	 * @param cityFB
	 *            city feature bean.
	 */
	public CityFeature(CityFeatureBean cityFB) {
		complete = false;
		scored = false;
		followers = new HashMap<Player, Integer>();
		neighborAngle = new ArrayList<Integer>();
		coveringPos = new HashSet<Position>();
		neighborEdge = new HashSet<Position>();
		allNeighborEdge = new HashSet<Position>();
		vertexAngle = new ArrayList<Integer>();
		vertexPos = new HashSet<Position>();
		MapToAngle newMap = new MapToAngle();

		for (NeighborEdgeBean neighboredgebean : cityFB.getNeighborEdges()) {
			neighborAngle.add(newMap.getAngleFromEdge(neighboredgebean));
		}

		for (VertexBean vertexBean : cityFB.getVertexPos()) {
			vertexAngle.add(newMap.getAngleFromVertex(vertexBean));
		}

		if (cityFB.hasBanner()) {
			banner = 1;
		} else {
			banner = 0;
		}
	}

	@Override
	public void updateStatus() {
		if (!scored) {
			if (neighborEdge.size() == 0) {
				System.out.println("city complete");
				complete = true;
				updateScore();
				scored = true;
				for (Map.Entry<Player, Integer> entry: followers.entrySet()) {
					entry.getKey().retrieveFollower(entry.getValue());
				}
			}
		}
	}

	/**
	 * If city feature hasn't been scored, update score the followers and score
	 * information. This method will be called either by updateStatus() (when the
	 * feature is complete) or by updateFinalScore() (when the game is over).
	 */
	public void updateScore() {
		if (!scored) {
			int multiply;
			if (complete) {
				multiply = 2;
			} else {
				multiply = 1;
			}
			if (followers.size() > 0) {
				for (Player player : findDominantPlayer()) {
					player.addScore((coveringPos.size() + banner) * multiply);
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
			neighborEdge.add(new Position((int) Math.round((x + Math.cos(Math.toRadians(neighborAngle.get(i))))),
					(int) Math.round((y + Math.sin(Math.toRadians(neighborAngle.get(i)))))));
			allNeighborEdge.add(new Position((int) Math.round((x + Math.cos(Math.toRadians(neighborAngle.get(i))))),
					(int) Math.round((y + Math.sin(Math.toRadians(neighborAngle.get(i)))))));
		}
		for (int j = 0; j < vertexAngle.size(); j++) {
			vertexPos
					.add(new Position((int) Math.round(x + Math.sqrt(2) * Math.cos(Math.toRadians(vertexAngle.get(j)))),
							(int) Math.round(y + Math.sqrt(2) * Math.sin(Math.toRadians(vertexAngle.get(j))))));
		}
	}

	/**
	 * When two features are adjacent, merge them into one feature.
	 * 
	 * @param oldCityF
	 *            the old city feature on the board
	 * @return the merged feature
	 */
	public CityFeature mergeTwoFeatures(CityFeature oldCityF) {
		CityFeature mergedFeature = oldCityF;
		mergedFeature.addCoverPos(this.coveringPos);
		mergedFeature.addNeighborEdge(this.neighborEdge);
		mergedFeature.addAllNeighborEdge(this.allNeighborEdge);
		mergedFeature.addVertexPos(this.vertexPos);
		mergedFeature.addBanner(this.banner);
		mergedFeature.addFollower(this.followers);
		return mergedFeature;
	}
	
	private void addFollower(Map<Player, Integer> newFollowers) {
		for (Map.Entry<Player, Integer> entry: newFollowers.entrySet()) {
			if (this.followers.containsKey(entry.getKey()) && this.followers.get(entry.getKey()) != null) {
				this.followers.put(entry.getKey(), this.followers.get(entry.getKey()) + entry.getValue());
			} else {
				this.followers.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void addNeighborEdge(Set<Position> newNeighborEdge) {
		List<Position> diffNeighborEdge = new ArrayList<Position>(newNeighborEdge);
		List<Position> commonNeighborEdge = new ArrayList<Position>(newNeighborEdge);
		commonNeighborEdge.retainAll(neighborEdge);
		diffNeighborEdge.removeAll(commonNeighborEdge);
		neighborEdge.removeAll(commonNeighborEdge);
		neighborEdge.addAll(diffNeighborEdge);
	}

	private void addAllNeighborEdge(Set<Position> allNewNeighborEdge) {
		allNeighborEdge.addAll(allNewNeighborEdge);
	}

	private void addVertexPos(Set<Position> newVertexPos) {
		vertexPos.addAll(newVertexPos);
	}

	private void addBanner(int numOfBanner) {
		this.banner += numOfBanner;
	}

	private void addCoverPos(Set<Position> newCoverPos) {
		coveringPos.addAll(newCoverPos);
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
	 * Get all the neighbor edge that city feature has.
	 * @return set of all neighbor edges
	 */
	public Set<Position> getAllNeighborEdge() {
		return allNeighborEdge;
	}

	@Override
	public List<Integer> getNeighborAngle() {
		return neighborAngle;
	}

	/**
	 * VertexAngle Getter.
	 * 
	 * @return vertexAngle of the feature
	 */
	public List<Integer> getVertexAngle() {
		return vertexAngle;
	}

	/**
	 * VertexPos Getter.
	 * 
	 * @return vertexPos of the feature
	 */
	public Set<Position> getVertexPos() {
		return vertexPos;
	}

	/**
	 * Banner getter.
	 * 
	 * @return number of banner of the city feature
	 */
	public int getBanner() {
		return this.banner;
	}

	@Override
	public Map<Player, Integer> getFollowers() {
		return followers;
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
	public boolean isComplete() {
		return complete;
	}

	@Override
	public Enum<TypeBean> getType() {
		return TYPE;
	}

}
