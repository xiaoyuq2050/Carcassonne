package edu.cmu.cs.cs214.hw4.core.feature;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;

/**
 * Interface of cityfeature, cloisterfeature, farmfeature, and roadfeature.
 * @author xiaoyuq
 *
 */
public interface Feature {
	
	/**
	 * If the feature is complete and hasn't been scored, update the followers and score information.
	 */
	void updateStatus();
	
	/**
	 * When the feature is rotated, update the angle of neighborAngle and vertexAngle (anti clockwise).
	 * This method will be called when the tile is rotated.
	 * @param rotateAngle the degree of the rotate angle (anti clockwise)
	 */
	void rotateAngle(int rotateAngle);
	
	/**
	 * When the position of feature is set, update the positions of neighborEdge and vertexPos.
	 * This method will be called when the tile position is set.
	 * @param x the horizontal coordinate of tile
	 * @param y the vertical coordinate of tile
	 */
	void setPosition(int x, int y);
	
	/**
	 * Covering position Getter.
	 * @return covering position of the feature
	 */
	Set<Position> getCoverPos();
	
	/**
	 * NeighborAngle Getter.
	 * @return neighborAngle of the feature
	 */
	List<Integer> getNeighborAngle();
	
	/**
	 * NeighborEdge Getter.
	 * @return neighborEdge of the feature
	 */
	Set<Position> getNeighborEdge();
	
	/**
	 * Follower Getter.
	 * @return map of occupied players and numbers on the feature
	 */
	Map<Player, Integer> getFollowers();
	
	/**
	 * Place a follower from a player on the feature.
	 * @param player the player on the turn
	 * @return true if player has unused followers and the feature is not occupied
	 */
	boolean placeFollower(Player player);

	/**
	 * Return whether the feature is complete.
	 * @return true if complete, false if incomplete
	 */
	boolean isComplete();

	/**
	 * Enum to of TypeBean to represent the type of the feature.
	 * @return type of feature
	 */
	Enum<TypeBean> getType();
}
