// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class TileListBean {
	private String name;
	private TileBean[] tilelist;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TileBean[] getTilelist() {
		return tilelist.clone();
	}
	
	public void setTilelist(TileBean[] tilelist) {
		this.tilelist = tilelist.clone();
	}
}
