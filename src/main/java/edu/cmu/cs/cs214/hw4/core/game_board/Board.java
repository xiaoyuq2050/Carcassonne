package edu.cmu.cs.cs214.hw4.core.game_board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.feature.CityFeature;
import edu.cmu.cs.cs214.hw4.core.feature.CloisterFeature;
import edu.cmu.cs.cs214.hw4.core.feature.FarmFeature;
import edu.cmu.cs.cs214.hw4.core.feature.RoadFeature;
import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tile.Tile;

/**
 * The board of the game, with feature lists and tile list.
 * 
 * @author xiaoyuq
 *
 */
public class Board {
	private List<Tile> tileList;
	private List<CityFeature> cityFeatureList;
	private List<RoadFeature> roadFeatureList;
	private List<FarmFeature> farmFeatureList;
	private List<CloisterFeature> cloisterFeatureList;

	/**
	 * Constructor of the board, with a designated tile on position(0, 0).
	 * 
	 * @param designatedTile
	 *            the designated tile
	 * @param gameChangeListener the
	 *            registered listener of the game
	 * @throws IOException when no specific tile image is found throw exception
	 */
	public Board(Tile designatedTile, GameChangeListener gameChangeListener) throws IOException {

		tileList = new ArrayList<Tile>();
		cityFeatureList = new ArrayList<CityFeature>();
		roadFeatureList = new ArrayList<RoadFeature>();
		farmFeatureList = new ArrayList<FarmFeature>();
		cloisterFeatureList = new ArrayList<CloisterFeature>();
		designateFirstTile(designatedTile, gameChangeListener);
	}

	private void designateFirstTile(Tile designatedTile, GameChangeListener gameChangeListener) throws IOException {
		designatedTile.setPosition(0, 0);
		placeTile(designatedTile, gameChangeListener);
	}

	/**
	 * Place a new tile on board, if it has no abut tile or abut tiles are invalid,
	 * return false; otherwise add all the features on the tile into feature lists
	 * on board, and add this tile into tile list on board.
	 * 
	 * @param tile
	 *            the new tile to be placed
	 * @param gameChangeListener
	 *            the registered listener of the game
	 * @return false if the placement is invalid, true if valid
	 * @throws IOException when no specific tile image is found throw exception
	 */
	public boolean placeTile(Tile tile, GameChangeListener gameChangeListener) throws IOException {
		if (tileList.size() > 0) {
			List<Tile> abutTileList = findAbutTiles(tile);
			if (abutTileList.size() == 0 || !validateAbutTile(tile, abutTileList)) {
				gameChangeListener.showInvalidTilePlacementDialog();
				return false;
			}
		}
		findAbutCloister(tile);
		for (RoadFeature roadFInTile : tile.getRoadFeature()) {
			addToRoadFeatures(roadFInTile);
		}
		for (FarmFeature farmFInTile : tile.getFarmFeature()) {
			addToFarmFeatures(farmFInTile);
		}
		for (CityFeature cityFInTile : tile.getCityFeature()) {
			addToCityFeatures(cityFInTile);
		}
		if (tile.getCloisterFeature().size() > 0) {
			addToCloisterFeatures(tile.getCloisterFeature().get(0));
		}
		tileList.add(tile);
		gameChangeListener.placeNewTile(tile);
		return true;
	}

	private void addToCityFeatures(CityFeature newCityF) {
		List<Integer> adjCityFIdx = new ArrayList<Integer>();
		for (int i = 0; i < cityFeatureList.size(); i++) {
			Set<Position> commonNeighborEdge = new HashSet<Position>();
			commonNeighborEdge.addAll(cityFeatureList.get(i).getNeighborEdge());
			commonNeighborEdge.retainAll(newCityF.getNeighborEdge());
			if (commonNeighborEdge.size() > 0) {
				adjCityFIdx.add(i);
			}
		}
		if (adjCityFIdx.size() == 0) {
			cityFeatureList.add(newCityF);
		} else {
			if (adjCityFIdx.size() == 1) {
				cityFeatureList.set(adjCityFIdx.get(0),
						newCityF.mergeTwoFeatures(cityFeatureList.get(adjCityFIdx.get(0))));
			} else {
				cityFeatureList.set(adjCityFIdx.get(0), newCityF.mergeTwoFeatures(cityFeatureList
						.get(adjCityFIdx.get(0)).mergeTwoFeatures(cityFeatureList.get(adjCityFIdx.get(1)))));
				cityFeatureList.remove((int) adjCityFIdx.get(1));
			}
		}
	}

	private void addToCloisterFeatures(CloisterFeature cloisterFeature) {
		for (Tile tile : tileList) {
			if (cloisterFeature.getNeighborPos().contains(tile.getPosition())) {
				cloisterFeature.addCoveringPos(tile.getPosition());
				cloisterFeature.removeNeighborPos(tile.getPosition());
			}
		}
		cloisterFeatureList.add(cloisterFeature);
	}

	private void addToRoadFeatures(RoadFeature newRoadF) {
		List<Integer> adjRoadFIdx = new ArrayList<Integer>();
		for (int i = 0; i < roadFeatureList.size(); i++) {
			Set<Position> commonNeighborEdge = new HashSet<Position>();
			commonNeighborEdge.addAll(roadFeatureList.get(i).getNeighborEdge());
			commonNeighborEdge.retainAll(newRoadF.getNeighborEdge());
			if (commonNeighborEdge.size() > 0) {
				adjRoadFIdx.add(i);
			}
		}
		if (adjRoadFIdx.size() == 0) {
			roadFeatureList.add(newRoadF);
		} else {
			if (adjRoadFIdx.size() == 1) {
				roadFeatureList.set(adjRoadFIdx.get(0),
						newRoadF.mergeTwoFeatures(roadFeatureList.get(adjRoadFIdx.get(0))));
			} else {
				roadFeatureList.set(adjRoadFIdx.get(0), newRoadF.mergeTwoFeatures(roadFeatureList
						.get(adjRoadFIdx.get(0)).mergeTwoFeatures(roadFeatureList.get(adjRoadFIdx.get(1)))));
				roadFeatureList.remove((int) adjRoadFIdx.get(1));
			}
		}
	}

	private void addToFarmFeatures(FarmFeature newFarmF) {
		List<Integer> adjFarmFIdx = new ArrayList<Integer>();
		for (int i = 0; i < farmFeatureList.size(); i++) {
			Set<Position> commonNeighborEdge = new HashSet<Position>();
			Set<Position> commonVertexPos = new HashSet<Position>();
			commonNeighborEdge.addAll(farmFeatureList.get(i).getNeighborEdge());
			commonNeighborEdge.retainAll(newFarmF.getNeighborEdge());
			commonVertexPos.addAll(farmFeatureList.get(i).getVertexPos());
			commonVertexPos.retainAll(newFarmF.getVertexPos());
			if (commonNeighborEdge.size() > 0 && commonVertexPos.size() > 0) {
				adjFarmFIdx.add(i);
			}
		}
		if (adjFarmFIdx.size() == 0) {
			farmFeatureList.add(newFarmF);
		} else {
			if (adjFarmFIdx.size() == 1) {
				farmFeatureList.set(adjFarmFIdx.get(0),
						newFarmF.mergeTwoFeatures(farmFeatureList.get(adjFarmFIdx.get(0))));
			}
			if (adjFarmFIdx.size() == 2) {
				farmFeatureList.set(adjFarmFIdx.get(0), farmFeatureList.get(adjFarmFIdx.get(1))
						.mergeTwoFeatures(newFarmF.mergeTwoFeatures(farmFeatureList.get(adjFarmFIdx.get(0)))));
				farmFeatureList.remove((int) adjFarmFIdx.get(1));
			}
			if (adjFarmFIdx.size() == 3) {
				farmFeatureList.set(adjFarmFIdx.get(0),
						farmFeatureList.get(adjFarmFIdx.get(2)).mergeTwoFeatures(farmFeatureList.get(adjFarmFIdx.get(1))
								.mergeTwoFeatures(newFarmF.mergeTwoFeatures(farmFeatureList.get(adjFarmFIdx.get(0))))));
				farmFeatureList.remove((int) adjFarmFIdx.get(1));
				farmFeatureList.remove((int) adjFarmFIdx.get(2));
			}
		}
	}

	private void findAbutCloister(Tile tile) {
		for (CloisterFeature eachCloisterFeature : cloisterFeatureList) {
			for (Position eachNeighborPos : eachCloisterFeature.getNeighborPos()) {
				if (tile.getPosition().equals(eachNeighborPos)) {
					eachCloisterFeature.addCoveringPos(tile.getPosition());
					eachCloisterFeature.removeNeighborPos(tile.getPosition());
					break;
				}
			}
		}
	}

	/**
	 * Search for abut tiles on board given a new tile.
	 * 
	 * @param tile
	 *            the new tile
	 * @return list of abut tiles
	 */
	public List<Tile> findAbutTiles(Tile tile) {
		List<Tile> abutTileList = new ArrayList<Tile>();
		for (Tile tileOnBoard : tileList) {
			if (tileOnBoard.abut(tile)) {
				abutTileList.add(tileOnBoard);
			}
		}
		return abutTileList;
	}

	/**
	 * Check if all the abut tiles are valid (have same segment on common edges).
	 * 
	 * @param tile
	 *            the new tile
	 * @param abutTileList
	 *            list of abut tiles of the new tile
	 * @return true if valid, false if invalid
	 */
	public boolean validateAbutTile(Tile tile, List<Tile> abutTileList) {
		int count = 0;
		for (Tile abutTile : abutTileList) {
			int[] adjSgmIdx = abutTile.abutTileSegments(tile);
			if (abutTile.getSegment().toArray()[adjSgmIdx[0]].equals(tile.getSegment().toArray()[adjSgmIdx[1]])) {
				count++;
			}
		}
		return count == abutTileList.size();
	}

	/**
	 * Update all city features, cloister features, and road features after every
	 * round.
	 */
	public void updateStatus() {
		for (CityFeature cityFeature : cityFeatureList) {
			cityFeature.updateStatus();
		}
		for (CloisterFeature cloisterFeature : cloisterFeatureList) {
			cloisterFeature.updateStatus();
		}
		for (RoadFeature roadFeature : roadFeatureList) {
			roadFeature.updateStatus();
		}
	}

	/**
	 * Tile list Getter.
	 * 
	 * @return all the tiles on board
	 */
	public List<Tile> getTileList() {
		return tileList;
	}

	/**
	 * Road feature List Getter.
	 * 
	 * @return all the road features on board
	 */
	public List<RoadFeature> getRoadFeatureList() {
		return roadFeatureList;
	}

	/**
	 * Farm feature List Getter.
	 * 
	 * @return all the farm features on board
	 */
	public List<FarmFeature> getFarmFeatureList() {
		return farmFeatureList;
	}

	/**
	 * City feature List Getter.
	 * 
	 * @return all the city features on board
	 */
	public List<CityFeature> getCityFeatureList() {
		return cityFeatureList;
	}

	/**
	 * Cloister feature List Getter.
	 * 
	 * @return all the cloister features on board
	 */
	public List<CloisterFeature> getCloisterFeatureList() {
		return cloisterFeatureList;
	}

	/**
	 * When game is over, update scores of incomplete features and farm features.
	 */
	public void updateFinalScore() {
		for (CityFeature cityFeature : cityFeatureList) {
			cityFeature.updateScore();
		}
		for (CloisterFeature cloisterFeature : cloisterFeatureList) {
			cloisterFeature.updateScore();
		}
		for (RoadFeature roadFeature : roadFeatureList) {
			roadFeature.updateScore();
		}
		for (FarmFeature farmFeature : farmFeatureList) {
			if (farmFeature.getFollowers().size() > 0) {
				int count = 0;
				for (CityFeature cityFeature : cityFeatureList) {
					if (cityFeature.isComplete()) {
						Set<Position> commonCoverPos = new HashSet<Position>();
						Set<Position> commonVertexPos = new HashSet<Position>();
						commonCoverPos.addAll(farmFeature.getCoverPos());
						commonCoverPos.retainAll(cityFeature.getCoverPos());
						commonVertexPos.addAll(farmFeature.getVertexPos());
						commonVertexPos.retainAll(cityFeature.getVertexPos());
						if (commonCoverPos.size() > 0 && commonVertexPos.size() > 0) {
							count += 1;
						}
					}
				}
				for (Player player : farmFeature.findDominantPlayer()) {
					player.addScore(3 * count);
				}
			}
		}
	}
}
