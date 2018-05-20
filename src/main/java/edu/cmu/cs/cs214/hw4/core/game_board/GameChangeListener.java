package edu.cmu.cs.cs214.hw4.core.game_board;

import java.io.IOException;

import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Tile;

/**
 * The listener of the game.
 * @author xiaoyuq
 *
 */
public interface GameChangeListener {
	
	/**
	 * draw a new tile from the tilestack.
	 * @param newTile the input new tile
	 * @throws IOException when no image is found throw exception
	 */
	void drawNewTile(Tile newTile) throws IOException;

	/**
	 * place a new tile on the board.
	 * @param tile the input tile
	 * @throws IOException when no image is found throw exception
	 */
	void placeNewTile(Tile tile) throws IOException;

	/**
	 * pop up a dialog show Invalid Tile Placement.
	 */
	void showInvalidTilePlacementDialog();

	/**
	 * pop up a dialog show No Enough Followers.
	 */
	void showNoEnoughFollowers();

	/**
	 * pop up a dialog show Already Place Follower.
	 */
	void showAlreadyPlaceFollower();

	/**
	 * add a new player to the game.
	 * @param player the current player
	 */
	void addNewPlayer(Player player);

	/**
	 * pop up a dialog show Game Over.
	 */
	void showGameOver();

	/**
	 * pop up a dialog show show No More Follower.
	 */
	void showNoMoreFollower();

}
