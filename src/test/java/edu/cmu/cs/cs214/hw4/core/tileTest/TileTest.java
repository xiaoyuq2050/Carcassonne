package edu.cmu.cs.cs214.hw4.core.tileTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.game_board.Game;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tile.Tile;
import edu.cmu.cs.cs214.hw4.core.tilelistbean.SegmentBean;


public class TileTest {
    private List<Tile> tileList;
    
	@Before
	public void setUp() throws IOException {
		Game newgame = new Game();
		tileList = newgame.getTileList();
	}

	@Test
	public void staticSegmentTest() {
		assertEquals(tileList.get(0).getSegment().getFirst(), SegmentBean.road);
		assertEquals(tileList.get(0).getSegment().getLast(), SegmentBean.city);
		assertEquals(tileList.get(3).getSegment().getFirst(), SegmentBean.farm);
		assertEquals(tileList.get(3).getSegment().getLast(), SegmentBean.farm);
//		assertEquals(tileList.get(6).getSegment().getFirst(), SegmentBean.city);
//		assertEquals(tileList.get(6).getSegment().getLast(), SegmentBean.farm);
//		assertEquals(tileList.get(7).getSegment().getFirst(), SegmentBean.farm);
//		assertEquals(tileList.get(7).getSegment().getLast(), SegmentBean.city);
	}
	
	@Test
	public void rotateSegmentTest() {
		tileList.get(0).rotate(90);
		tileList.get(2).rotate(180);
		tileList.get(3).rotate(270);
//		assertEquals(tileList.get(0).getSegment().getFirst(), SegmentBean.farm);
//		assertEquals(tileList.get(0).getSegment().getLast(), SegmentBean.road);
//		assertEquals(tileList.get(2).getSegment().getFirst(), SegmentBean.road);
//		assertEquals(tileList.get(2).getSegment().getLast(), SegmentBean.road);
//		assertEquals(tileList.get(3).getSegment().getFirst(), SegmentBean.farm);
//		assertEquals(tileList.get(3).getSegment().getLast(), SegmentBean.city);
	}
	
	@Test
	public void staticFeature_farm_cloister_Test() {
		Tile t0 = tileList.get(0);
//		assertEquals(t0.getCityFeature().size(), 0);
//		assertEquals(t0.getRoadFeature().size(), 1);
//		assertEquals(t0.getRoadFeature().get(0).getNeighborAngle().size(), 1);
//		assertEquals((int)t0.getRoadFeature().get(0).getNeighborAngle().get(0), 270);
//		assertEquals(t0.getCloisterFeature().size(), 1);
//		assertEquals(t0.getFarmFeature().size(), 1);
//		assertEquals(t0.getFarmFeature().get(0).getNeighborAngle().size(), 4);
//		for (Integer angle: new int[] {0, 90, 180, 270}) {
//			assertTrue(t0.getFarmFeature().get(0).getNeighborAngle().contains(angle));
//		}
	}
		
	@Test
	public void feature_road_farm_Test() {
		Tile t1 = tileList.get(2);
		assertEquals(t1.getCityFeature().size(), 0);
		assertEquals(t1.getRoadFeature().size(), 1);
//		assertEquals(t1.getRoadFeature().get(0).getNeighborAngle().size(), 2);
//		assertEquals((int)t1.getRoadFeature().get(0).getNeighborAngle().get(0), 180);
//		assertEquals((int)t1.getRoadFeature().get(0).getNeighborAngle().get(1), 270);
//		assertEquals(t1.getCloisterFeature().size(), 0);
//		assertEquals(t1.getFarmFeature().size(), 2);
//		assertEquals((int)t1.getFarmFeature().get(0).getNeighborAngle().get(0), 180);
//		assertEquals((int)t1.getFarmFeature().get(0).getNeighborAngle().get(1), 270);
//		assertEquals((int)t1.getFarmFeature().get(0).getVertexAngle().get(0), 225);
//		assertEquals(t1.getFarmFeature().get(1).getNeighborAngle().size(), 4);
//		for (Integer angle: new int[] {0, 90, 180, 270}) {
//			assertTrue(t1.getFarmFeature().get(1).getNeighborAngle().contains(angle));
//		}
//		for (Integer angle: new int[] {45, 135, 315}) {
//			assertTrue(t1.getFarmFeature().get(1).getVertexAngle().contains(angle));
//		}
	}
	
	@Test
	public void staticFeature_city_farm_Test() {
		Tile t2 = tileList.get(5);
//		assertEquals(t2.getCityFeature().size(), 1);
//		assertEquals(t2.getCityFeature().get(0).getNeighborAngle().size(), 2);
//		assertEquals((int) t2.getCityFeature().get(0).getNeighborAngle().get(0), 90);
//		assertEquals((int) t2.getCityFeature().get(0).getNeighborAngle().get(1), 180);
//		assertEquals((int) t2.getCityFeature().get(0).getVertexAngle().get(0), 45);
//		assertEquals((int) t2.getCityFeature().get(0).getVertexAngle().get(1), 225);
//		assertEquals(t2.getCityFeature().get(0).getBanner(), 1);
//		assertEquals(t2.getRoadFeature().size(), 0);
//		assertEquals(t2.getCloisterFeature().size(), 0);
//		assertEquals(t2.getFarmFeature().size(), 1);
//		assertEquals((int) t2.getFarmFeature().get(0).getNeighborAngle().get(0), 270);
//		assertEquals((int) t2.getFarmFeature().get(0).getNeighborAngle().get(1), 0);
//		for (Integer angle: new int[] {225, 45, 315}) {
//			assertTrue(t2.getFarmFeature().get(0).getVertexAngle().contains(angle));
//		}

	}
	
	@Test
	public void setPosition_cloister_farm_Test() {
		Tile t0 = tileList.get(0);
		t0.rotate(180);
		t0.setPosition(0, -2);
		assertTrue(t0.getRoadFeature().get(0).getCoverPos().contains(new Position(0, -2)));
		assertTrue(t0.getRoadFeature().get(0).getNeighborEdge().contains(new Position(0, -1)));
		assertTrue(t0.getFarmFeature().get(0).getNeighborEdge().contains(new Position(0, -1)));
		assertTrue(t0.getFarmFeature().get(0).getNeighborEdge().contains(new Position(1, -2)));
		assertTrue(t0.getFarmFeature().get(0).getNeighborEdge().contains(new Position(0, -3)));
//		assertTrue(t0.getFarmFeature().get(0).getNeighborEdge().contains(new Position(-1, -2)));
//		assertEquals(t0.getCloisterFeature().get(0).getCenterPos(), new Position(0, -2));
//		assertTrue(t0.getCloisterFeature().get(0).getCoverPos().contains(new Position(0, -2)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(0, 0)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(2, 0)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(2, -2)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(2, -4)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(0, -4)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(-2, -4)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(-2, -2)));
//		assertTrue(t0.getCloisterFeature().get(0).getNeighborPos().contains(new Position(-2, 0)));
	}
	
	@Test
	public void setPosition_farm_road_city_Test() {
		Tile t1 = tileList.get(8);
		t1.rotate(90);
		t1.setPosition(4, 2);
		assertTrue(t1.getCityFeature().get(0).getCoverPos().contains(new Position(4, 2)));
		assertTrue(t1.getCityFeature().get(0).getNeighborEdge().contains(new Position(4, 3)));
		assertTrue(t1.getCityFeature().get(0).getVertexPos().contains(new Position(3, 3)));
		assertTrue(t1.getCityFeature().get(0).getVertexPos().contains(new Position(5, 3)));
		assertTrue(t1.getRoadFeature().get(0).getCoverPos().contains(new Position(4, 2)));
		assertTrue(t1.getRoadFeature().get(0).getNeighborEdge().contains(new Position(3, 2)));
		assertTrue(t1.getRoadFeature().get(0).getNeighborEdge().contains(new Position(5, 2)));
		assertTrue(t1.getFarmFeature().get(0).getCoverPos().contains(new Position(4, 2)));
		assertTrue(t1.getFarmFeature().get(0).getNeighborEdge().contains(new Position(3, 2)));
		assertTrue(t1.getFarmFeature().get(0).getNeighborEdge().contains(new Position(4, 1)));
		assertTrue(t1.getFarmFeature().get(0).getNeighborEdge().contains(new Position(5, 2)));
	}
	
	@Test
	public void abutTileTest() {
		Tile t0 = tileList.get(0);
		t0.setPosition(0, 0);
		Tile t1 = tileList.get(1);
		t1.setPosition(2, 0);
		assertTrue(t0.abut(t1));
		t1.setPosition(0, 2);
		assertTrue(t0.abut(t1));
		t1.setPosition(-2, 0);
		assertTrue(t0.abut(t1));
		t1.setPosition(0, -2);
		assertTrue(t0.abut(t1));
	}
	

}
