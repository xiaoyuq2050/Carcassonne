package edu.cmu.cs.cs214.hw4.core.tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.feature.CityFeature;
import edu.cmu.cs.cs214.hw4.core.feature.CloisterFeature;
import edu.cmu.cs.cs214.hw4.core.feature.FarmFeature;
import edu.cmu.cs.cs214.hw4.core.feature.RoadFeature;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.CityFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.CloisterFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.FarmFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.RoadFeatureBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.SegmentBean;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.TileBean;

/**
 * Tile object in game, with ordered deque of segments, list of city feature, list of road feature, list of farm feature and list of cloister feature.
 * @author xiaoyuq
 *
 */
public class Tile {
	private Position pos;
	private String imageID;
	private Deque<SegmentBean> segments;
	private List<CityFeature> cityFeature;
	private List<RoadFeature> roadFeature;
	private List<FarmFeature> farmFeature;
	private List<CloisterFeature> cloisterFeature;
	
	/**
	 * Construct the tile from tile bean, copy the segments and initialize all the features on the tile.
	 * @param tileBean the tile bean
	 */
	public Tile(TileBean tileBean) {
		pos = new Position(0, 0);
		this.imageID = tileBean.getImageID();
		this.segments = new ArrayDeque<SegmentBean>(Arrays.asList(tileBean.getSegment()));
		this.cityFeature = new ArrayList<CityFeature>();
		this.roadFeature = new ArrayList<RoadFeature>();
		this.farmFeature = new ArrayList<FarmFeature>();
		this.cloisterFeature = new ArrayList<CloisterFeature>();
		if (tileBean.getCityFeature() != null) {
			for (CityFeatureBean eachCityFB : tileBean.getCityFeature()) {
				cityFeature.add(new CityFeature(eachCityFB));
			}
		}
		if (tileBean.getRoadFeature() != null) {
			for (RoadFeatureBean eachRoadFB : tileBean.getRoadFeature()) {
				roadFeature.add(new RoadFeature(eachRoadFB));
			}
		}
		if (tileBean.getFarmFeature() != null) {
			for (FarmFeatureBean eachFarmFB : tileBean.getFarmFeature()) {
				farmFeature.add(new FarmFeature(eachFarmFB));
			}
		}
		if (tileBean.getCloisterFeature() != null) {
			for (CloisterFeatureBean eachCloisterFB : tileBean.getCloisterFeature()) {
				cloisterFeature.add(new CloisterFeature(eachCloisterFB));
			}
		}
	}
	
	/**
	 * Tile Position Getter
	 * @return position of the tile
	 */
	public Position getPosition() {
		return pos;
	}
	
	/**
	 * When rotating the tile, update the order the segments, and rotate features on the file.
	 * @param rotateAngle the degree of the angle (anti clockwise)
	 */
	public void rotate(int rotateAngle) {
		for (int i = 0; i < rotateAngle / 90; i++) {
			segments.addFirst(segments.pollLast());
		}
		for (CityFeature eachCityFeature: cityFeature) {
			eachCityFeature.rotateAngle(rotateAngle);
		}
		for (RoadFeature eachRoadFeature: roadFeature) {
			eachRoadFeature.rotateAngle(rotateAngle);
		}
		for (FarmFeature eachFarmFeature: farmFeature) {
			eachFarmFeature.rotateAngle(rotateAngle);
		}
	}
	
	/**
	 * Set the position of the tile, and also set all the features on the tile.
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public void setPosition(int x, int y) {
		pos.setPosition(x, y);
		for (CityFeature eachCityFeature: cityFeature) {
			eachCityFeature.setPosition(x, y);
		}
		for (RoadFeature eachRoadFeature: roadFeature) {
			eachRoadFeature.setPosition(x, y);
		}
		for (FarmFeature eachFarmFeature: farmFeature) {
			eachFarmFeature.setPosition(x, y);
		}
		for (CloisterFeature eachCloisterFeature: cloisterFeature) {
			eachCloisterFeature.setPosition(x, y);
		}
	}
	
	/**
	 * Get the image id of the tile.
	 * @return string of image id(from A to X)
	 */
	public String getImageID() {
		return imageID;
	}
	
	/**
	 * Segment Deque Getter.
	 * @return segment deque of the tile
	 */
	public Deque<SegmentBean> getSegment() {
		return segments;
	}
	
	/**
	 * City Feature Getter.
	 * @return city feature on the tile
	 */
	public List<CityFeature> getCityFeature() {
		return cityFeature;
	}
	
	/**
	 * Road Feature Getter.
	 * @return road feature on the tile
	 */
	public List<RoadFeature> getRoadFeature() {
		return roadFeature;
	}
	
	/**
	 * Farm Feature Getter.
	 * @return farm feature on the tile
	 */
	public List<FarmFeature> getFarmFeature() {
		return farmFeature;
	}
	
	/**
	 * Cloister Feature Getter.
	 * @return cloister feature on the tile
	 */
	public List<CloisterFeature> getCloisterFeature() {
		return cloisterFeature;
	}

	/**
	 * Check if two tiles are abut (up, down, left or right).
	 * @param anotherTile another tile for check
	 * @return true if they are abut, otherwise return false
	 */
	public boolean abut(Tile anotherTile) {
		double dist = Math.abs(this.pos.getX() - anotherTile.getPosition().getX()) + Math.abs(this.pos.getY() - anotherTile.getPosition().getY());
		return dist == 2;
	}
	
	/**
	 * Find the indexes of edges to compare between two abut tiles.
	 * @param anotherTile another tile for check
	 * @return array of indexes of common segments between two abut tiles
	 */
	public int[] abutTileSegments(Tile anotherTile) {
		if (this.pos.getX() == anotherTile.getPosition().getX()) {
			if (this.pos.getY() < anotherTile.getPosition().getY()) {
				return new int[] {0, 2};
			} else {
				return new int[] {2, 0};
			}
		} else {
			if (this.pos.getX() > anotherTile.getPosition().getX()) {
				return new int[] {1, 3};
			} else {
				return new int[] {3, 1};
			}
		}
	}

}
