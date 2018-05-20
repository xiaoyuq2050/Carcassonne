package edu.cmu.cs.cs214.hw4.core.tile;

/**
 * Position class of objects (tile, feature) on board.
 * @author xiaoyuq
 *
 */
public class Position {
	
	private int x;
	private int y;
	
	/**
	 * Construct position with x, y coordinates.
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set position of a tile.
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Horizontal coordinate Getter.
	 * @return horizontal coordinate
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Vertical coordinate Getter.
	 * @return vertical coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	@Override
	public boolean equals(Object anotherPos) {
		if (!(anotherPos instanceof Position)) {
			return false;
		}
		Position comparePos = (Position) anotherPos;
		if (this.x == comparePos.x && this.y == comparePos.y) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 17 * x + 31 * y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
