package edu.cmu.cs.cs214.hw4.core.game_board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.yaml.snakeyaml.Yaml;

import edu.cmu.cs.cs214.hw4.core.feature.CloisterFeature;
import edu.cmu.cs.cs214.hw4.core.feature.Feature;
import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Tile;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.TileBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.TileListBean;;

/**
 * Game class which interacts directly with external user, with player list,
 * unused tile list, and board.
 * 
 * @author xiaoyuq
 *
 */
public class Game {
	private boolean begin = false;
	private List<Player> playerList;
	private List<Tile> tileList;
	private List<Feature> newFeatureList;
	private Board board;
	private GameChangeListener gameChangeListener;
	private Random r;

	private static TileListBean parse(String fileName) {
		Yaml yaml = new Yaml();
		try (InputStream is = new FileInputStream(fileName)) {
			return yaml.loadAs(is, TileListBean.class);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File " + fileName + " not found!");
		} catch (IOException e) {
			throw new IllegalArgumentException("Error when reading " + fileName + "!");
		}
	}

	/**
	 * Constructor of game object, initialize a board object with one tile and list
	 * of 71 tiles, and an empty player list.
	 */
	public Game() {
		playerList = new ArrayList<Player>();
		TileListBean tileListbean = parse("src/main/resources/tile_config.yaml");
		tileList = new ArrayList<Tile>();
		for (TileBean eachtileB : tileListbean.getTilelist()) {
			tileList.add(new Tile(eachtileB));
		}
	}

	/**
	 * Add player into the game.
	 * 
	 * @param player
	 *            new player
	 */
	public void addPlayer(Player player) {
		playerList.add(player);
		gameChangeListener.addNewPlayer(player);
	}

	/**
	 * Register game change listener to game.
	 * 
	 * @param gameChangeListener
	 *            the listener
	 */
	public void addGameChangeListener(GameChangeListener gameChangeListener) {
		this.gameChangeListener = gameChangeListener;
	}

	/**
	 * Start the game and initialize.
	 * 
	 * @throws IOException
	 *             When specific image is found throw the exception
	 */
	public void startNewGame() throws IOException {
		begin = true;
		board = new Board(tileList.remove(0), gameChangeListener);
		r = new Random();
	}

	/**
	 * Check whether the game has begun.
	 * 
	 * @return true if the game has begun
	 */
	public boolean isBegin() {
		return begin;
	}

	/**
	 * Draw a random tile from the unused tile list.
	 * 
	 * @throws IOException
	 *             when no specific image is found throw exception
	 */
	public void drawTile() throws IOException {
		int index = r.nextInt(tileList.size());
		Tile newTile = tileList.remove(index);
		notifyNewTile(newTile);
	}

	private void notifyNewTile(Tile newTile) throws IOException {
		gameChangeListener.drawNewTile(newTile);
	}

	/**
	 * Place a new tile on the board.
	 * 
	 * @param tile
	 *            the new tile to be placed
	 * @param gameChangeListener the registered listener of the game
	 * @return true if the placement is valid, false if invalid
	 * @throws IOException
	 *             when no specific image is found throw exception
	 */
	public boolean placeTile(Tile tile, GameChangeListener gameChangeListener) throws IOException {
		return board.placeTile(tile, gameChangeListener);
	}

	/**
	 * After placing a new tile, get all the feature associated with this tile to
	 * place follower.
	 * 
	 * @param player
	 *            the player placing the tile
	 * @param tile
	 *            the newly-placed tile
	 * @return list of features associated with the tile
	 */
	public List<Feature> getNewFeatures(Player player, Tile tile) {
		newFeatureList = new ArrayList<Feature>();
		newFeatureList.addAll(board.getCityFeatureList());
		newFeatureList.addAll(board.getFarmFeatureList());
		newFeatureList.addAll(board.getRoadFeatureList());
		List<Feature> toRemove = new ArrayList<Feature>();
		for (Feature eachFeature : newFeatureList) {
			if (!eachFeature.getCoverPos().contains(tile.getPosition())) {
				toRemove.add(eachFeature);
			}
		}
		newFeatureList.removeAll(toRemove);
		for (CloisterFeature cloisterFeature : board.getCloisterFeatureList()) {
			if (cloisterFeature.getCenterPos().equals(tile.getPosition())) {
				newFeatureList.add(cloisterFeature);
			}
		}
		return newFeatureList;
	}

	/**
	 * Place a follower on a designated feature (any element in the result of
	 * getNewFeatuers()).
	 * 
	 * @param player
	 *            the player placing the follower
	 * @param feature
	 *            the designated feature
	 * @return true if the placement is valid, false if invalid (player has no
	 *         unused followers or the feature is occupied)
	 */
	public boolean placeFollower(Player player, Feature feature) {
		return feature.placeFollower(player);
	}

	/**
	 * Update the status of board after every round.
	 */
	public void updateStatus() {
		board.updateStatus();
		if (tileList.size() == 0) {
			board.updateFinalScore();
			gameChangeListener.showGameOver();
		}
	}

	/**
	 * Player list Getter.
	 * 
	 * @return list of players
	 */
	public List<Player> getPlayers() {
		return playerList;
	}

	/**
	 * Board Getter.
	 * 
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Tile list Getter.
	 * 
	 * @return list of unused tiles
	 */
	public List<Tile> getTileList() {
		return tileList;
	}
}
