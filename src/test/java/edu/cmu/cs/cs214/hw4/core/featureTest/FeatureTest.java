//package edu.cmu.cs.cs214.hw4.core.featureTest;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.cmu.cs.cs214.hw4.core.game_board.Game;
//import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
//import edu.cmu.cs.cs214.hw4.core.tile.Position;
//import edu.cmu.cs.cs214.hw4.core.tile.Tile;
//
//public class FeatureTest {
//
//	private Game newgame;
//	private Player player1;
//	private Tile tile0;
//	private Tile tile1;
//	private Tile tile2;
//	private Tile tile3;
//	private Tile tile4;
//	private Tile tile5;
//	private Tile tile6;
//	private Tile tile7;
//	
//	@Before
//	public void setUp() {
//		newgame = new Game();
//		player1 = new Player("1");
//		newgame.addPlayer(player1);
//		tile0 = newgame.drawTile(player1, 8);
//		tile0.setPosition(0, 2);
//		tile1 = newgame.drawTile(player1, 7);
//		tile1.setPosition(2, 2);
//		tile2 = newgame.drawTile(player1, 6);
//		tile2.setPosition(0, 4);
//		tile3 = newgame.drawTile(player1, 5);
//		tile3.rotate(90);
//		tile3.setPosition(2, 4);
//		tile4 = newgame.drawTile(player1, 4);
//		tile4.rotate(90);
//		tile4.setPosition(4, 2);
//		tile5 = newgame.drawTile(player1, 3);
//		tile5.setPosition(2, 0);
//		tile6 = newgame.drawTile(player1, 0);
//		tile6.rotate(180);
//		tile6.setPosition(-2, 2);
//		tile7 = newgame.drawTile(player1, 1);
//		tile7.rotate(90);
//		tile7.setPosition(-2, 4);
//	}
//	
//	@Test
//	public void mergeTwoRoadTest() {
//		newgame.placeTile(player1, tile0);
//		assertEquals(newgame.getBoard().getRoadFeatureList().size(), 1);
//		assertTrue(newgame.getBoard().getRoadFeatureList().get(0).getCoverPos().contains(new Position(0, 0)));
//		assertTrue(newgame.getBoard().getRoadFeatureList().get(0).getCoverPos().contains(new Position(0, 2)));
//		assertTrue(newgame.getBoard().getRoadFeatureList().get(0).getNeighborEdge().contains(new Position(0, -1)));
//		assertTrue(newgame.getBoard().getRoadFeatureList().get(0).getNeighborEdge().contains(new Position(0, 3)));
//	}
//	
//	@Test
//	public void mergeTwoFarmTest() {
//		newgame.placeTile(player1, tile0);
//		assertEquals(newgame.getBoard().getFarmFeatureList().size(), 2);
//		assertTrue(newgame.getBoard().getFarmFeatureList().get(0).getCoverPos().contains(new Position(0, 0)));
//		assertTrue(newgame.getBoard().getFarmFeatureList().get(0).getCoverPos().contains(new Position(0, 2)));
//		assertEquals(newgame.getBoard().getFarmFeatureList().get(0).getNeighborEdge().size(), 5);
//		assertTrue(newgame.getBoard().getFarmFeatureList().get(0).getNeighborEdge().contains(new Position(0, -1)));
//		assertTrue(newgame.getBoard().getFarmFeatureList().get(0).getNeighborEdge().contains(new Position(0, 3)));
//		assertEquals(newgame.getBoard().getFarmFeatureList().get(0).getVertexPos().size(), 3);
//		assertEquals(newgame.getBoard().getFarmFeatureList().get(1).getNeighborEdge().size(), 4);
//		assertEquals(newgame.getBoard().getFarmFeatureList().get(1).getVertexPos().size(), 3);
//	}
//	
//	@Test
//	public void mergeTwoCityTest() {
//		newgame.placeTile(player1, tile0);
//		newgame.placeTile(player1, tile1);
//		assertEquals(newgame.getBoard().getCityFeatureList().size(), 1);
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(0, 2)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(2, 2)));
//		assertEquals(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().size(), 3);
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().contains(new Position(2, 1)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().contains(new Position(3, 2)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().contains(new Position(2, 3)));
//	}
//	
//	@Test
//	public void mergeThreeCityTest() {
//		newgame.placeTile(player1, tile0);
//		newgame.placeTile(player1, tile1);
//		newgame.placeTile(player1, tile2);
//		newgame.placeTile(player1, tile3);
//		assertEquals(newgame.getBoard().getCityFeatureList().size(), 1);
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(0, 2)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(2, 2)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(0, 4)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getCoverPos().contains(new Position(2, 4)));
//		assertEquals(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().size(), 2);
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().contains(new Position(2, 1)));
//		assertTrue(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge().contains(new Position(3, 2)));
//	}
//
//	@Test
//	public void addCloisterTest() {
//		newgame.placeTile(player1, tile0);
//		newgame.placeTile(player1, tile6);
//		assertEquals(newgame.getBoard().getCloisterFeatureList().size(), 1);
//		assertEquals(newgame.getBoard().getCloisterFeatureList().get(0).getCenterPos(), new Position(-2, 2));
//		assertEquals(newgame.getBoard().getCloisterFeatureList().get(0).getCoverPos().size(), 3);
//		assertEquals(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().size(), 6);
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(-2, 4)));
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(0, 4)));
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(-4, 4)));
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(-4, 2)));
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(-4, 0)));
//		assertTrue(newgame.getBoard().getCloisterFeatureList().get(0).getNeighborPos().contains(new Position(-2, 0)));
//	}
//	
//}
