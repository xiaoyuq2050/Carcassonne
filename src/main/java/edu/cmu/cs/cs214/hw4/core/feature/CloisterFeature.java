package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.CloisterFeatureBean;

/**
 * CloisterFeature: representation of cloister feature.
 * @author xiaoyuq
 *
 */
public class CloisterFeature implements Feature{
	private boolean complete;
	private boolean scored;
	private Map<Player, Integer> followers;
	private Position centerPos;
	private Set<Position> coveringPos;
	private Set<Position> neighborPos;
	private static final Enum<TypeBean> TYPE = TypeBean.cloister;
	
	/**
	 * Construct cloister feature with parsed cloister feature bean. 
	 * @param cloisterFB cloister feature bean
	 */
	public CloisterFeature(CloisterFeatureBean cloisterFB) {
		complete = false;
		scored = false;
		followers = new HashMap<Player, Integer>();
		coveringPos = new HashSet<Position>();
		neighborPos = new HashSet<Position>();
	}

	/**
	 * When the position of feature is set, update the positions of neighborPos.
	 * This method will be called when the tile position is set.
	 * @param x the horizontal coordinate of tile
	 * @param y the vertical coordinate of tile
	 */
	public void setPosition(int x, int y) {
		centerPos = new Position(x, y);
		coveringPos.add(new Position(x, y));
		for (int i = 0; i < 8; i++) {
			neighborPos.add(new Position(x - 2, y + 2));
			neighborPos.add(new Position(x, y + 2));
			neighborPos.add(new Position(x + 2, y + 2));
			neighborPos.add(new Position(x - 2, y));
			neighborPos.add(new Position(x + 2, y));
			neighborPos.add(new Position(x - 2, y - 2));
			neighborPos.add(new Position(x, y - 2));
			neighborPos.add(new Position(x + 2, y - 2));
		}
	}
	
	/**
	 * NeighborPos Getter.
	 * @return neighborPos of the feature
	 */
	public Set<Position> getNeighborPos() {
		return neighborPos;
	}
	
	/**
	 * When adjacent tile is placed, add the position of the tile into coveringPos.
	 * @param pos position of adjacent tile
	 */
	public void addCoveringPos(Position pos) {
		coveringPos.add(pos);
	}
	
	/**
	 * When adjacent tile is placed, remove the position of the tile from neighborPos.
	 * @param pos position of adjacent tile.
	 */
	public void removeNeighborPos(Position pos) {
		neighborPos.remove(pos);
	}
	
	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void rotateAngle(int rotateAngle) {
		return;
	}

	@Override
	public Set<Position> getCoverPos() {
		return coveringPos;
	}

	@Override
	public Set<Position> getNeighborEdge() {
		return getNeighborPos();
	}

	@Override
	public boolean placeFollower(Player player) {
		if (player.haveUnusedFollower()) {
			player.placeFollower();
			followers.put(player, 1);
		    return true;
		}
		return false;
	}

	@Override
	public void updateStatus() {
		if (!scored) {
			if (neighborPos.size() == 0) {
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
	 * If cloister feature hasn't been scored, update score the followers and score information.
	 * This method will be called either by updateStatus() (when the feature is complete) 
	 * or by updateFinalScore() (when the game is over).
	 */
	public void updateScore() {
		if (!scored) {
			if (followers.size() > 0) {
		        for (Player player: followers.keySet()) {
				    player.addScore(coveringPos.size());
			    }
		    }
		}
	}
	
	/**
	 * Center position of the feature.
	 * @return center position of cloister.
	 */
	public Position getCenterPos() {
		return centerPos;
	}

	@Override
	public Map<Player, Integer> getFollowers() {
		return followers;
	}
	
	@Override
	public List<Integer> getNeighborAngle() {
		return null;
	}
	
	@Override
	public Enum<TypeBean> getType() {
		return TYPE;
	}
}
