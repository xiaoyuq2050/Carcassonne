package edu.cmu.cs.cs214.hw4.core.player_follower;

/**
 * Player class in the game, with name, score and number of unused followers.
 * @author xiaoyuq
 *
 */
public class Player {
	private String name;
	private int score;
	private int numUnusedFollower;
	
	/**
	 * Construct player object with name string, initialized score is 0, number of unused followers is 7.
	 * @param name name of the player
	 */
	public Player(String name) {
		this.name = name;
		score = 0;
		numUnusedFollower = 7;
	}
	
	/**
	 * When an occupied feature is complete and the player is dominant, add score.
	 * @param scoreAdded score to be added
	 */
	public void addScore(int scoreAdded) {
		score += scoreAdded;
	}
	
	/**
	 * When an occupied feature is complete, retrieve followers on that feature.
	 * @param numFollower number of follower on the feature
	 */
	public void retrieveFollower(int numFollower) {
		numUnusedFollower += numFollower;
	}
	
	/**
	 * When placement of a new follower is valid, reduce number of unused followers by 1.
	 */
	public void placeFollower() {
		numUnusedFollower -= 1;
	}
	
	/**
	 * Check if the player has unused followers.
	 * @return true if number of unused followers > 0, false if = 0
	 */
	public boolean haveUnusedFollower() {
		return numUnusedFollower > 0;
	}

	/**
	 * Player name Getter.
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Player score Getter.
	 * @return score of the player
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Number of unused followers Getter.
	 * @return number of unused followers
	 */
	public int getNumUsedFollower() {
		return numUnusedFollower;
	}
	
	@Override
	public boolean equals(Object anotherPlayer) {
		if (!(anotherPlayer instanceof Player)) {
			return false;
		}
		Player comparePlayer = (Player) anotherPlayer;
		if (this.name.equals(comparePlayer.name)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
