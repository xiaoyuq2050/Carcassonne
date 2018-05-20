//package edu.cmu.cs.cs214.hw4.core.boardTest;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.cmu.cs.cs214.hw4.core.game_board.Game;
//import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
//import edu.cmu.cs.cs214.hw4.core.tile.Tile;
//
//public class BoardTest {
//
//	private Game newgame;
//	private Player player1;
//	private Player player2;
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
//		player2 = new Player("2");
//		newgame.addPlayer(player1);
//		newgame.addPlayer(player2);
//		tile0 = newgame.drawTile();
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
//	public void placeTileTest() {
//		assertTrue(newgame.placeTile(player1, tile0));
//		assertTrue(newgame.placeTile(player1, tile1));
//		assertTrue(newgame.placeTile(player1, tile2));
//		assertTrue(newgame.placeTile(player1, tile3));
//		assertTrue(newgame.placeTile(player1, tile4));
//		assertTrue(newgame.placeTile(player1, tile5));
//		assertTrue(newgame.placeTile(player1, tile6));
//		assertTrue(newgame.placeTile(player1, tile7));
//	}
//	
//	@Test
//	public void placeFollowerTest() {
//		newgame.placeTile(player1, tile0);
//		assertTrue(newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile0).get(0)));
//		newgame.updateStatus();
//		assertEquals(player1.getNumUsedFollower(), 6);
//		newgame.placeTile(player2, tile1);
//		assertFalse(newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile1).get(0)));
//		newgame.updateStatus();
//		assertEquals(player2.getNumUsedFollower(), 7);
//		newgame.placeTile(player1, tile2);
//		assertTrue(newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile2).get(0)));
//		newgame.updateStatus();
//		assertEquals(player1.getNumUsedFollower(), 5);
//		newgame.placeTile(player2, tile3);
//		assertTrue(newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile3).get(1)));
//		newgame.updateStatus();
//		assertEquals(player2.getNumUsedFollower(), 6);
//		newgame.placeTile(player1, tile4);
//		assertTrue(newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile4).get(1)));
//	    newgame.updateStatus();
//	    assertEquals(player1.getNumUsedFollower(), 4);
//	    newgame.placeTile(player2, tile6);
//	    assertTrue(newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile6).get(2)));
//	    newgame.updateStatus();
//	    assertEquals(player2.getNumUsedFollower(), 5);
//	    newgame.placeTile(player1, tile5);
//	    assertTrue(newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile5).get(1)));
//	    newgame.updateStatus();
//	    assertEquals(player1.getNumUsedFollower(), 5);
//	    newgame.placeTile(player2, tile7);
//	    assertTrue(newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile7).get(1)));
//	    newgame.updateStatus();
//	    assertEquals(player2.getNumUsedFollower(), 5);
//	}
//
//	@Test
//	public void checkScore() {
//		newgame.placeTile(player1, tile0);
//		newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile0).get(0));
//		newgame.updateStatus();
//		assertEquals(player1.getScore(), 0);
//		newgame.placeTile(player2, tile1);
//		newgame.updateStatus();
//		assertEquals(player2.getScore(), 0);
//		newgame.placeTile(player1, tile2);
//		newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile2).get(0));
//		newgame.updateStatus();
//		assertEquals(player1.getScore(), 0);
//		newgame.placeTile(player2, tile3);
//		newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile3).get(1));
//		newgame.updateStatus();
//		assertEquals(player2.getScore(), 0);
//		newgame.placeTile(player1, tile4);
//		newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile4).get(1));
//	    newgame.updateStatus();
//	    assertEquals(player1.getScore(), 0);
//	    newgame.placeTile(player2, tile6);
//	    newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile6).get(2));
//	    newgame.updateStatus();
//	    assertEquals(player2.getScore(), 0);
//	    newgame.placeTile(player1, tile5);
//	    newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile5).get(1));
//	    newgame.updateStatus();
//	    assertEquals(player1.getScore(), 16);
//	    newgame.placeTile(player2, tile7);
//	    newgame.placeFollower(player2, newgame.getNewFeatures(player2, tile7).get(1));
//	    newgame.updateStatus();
//	    assertEquals(player2.getScore(), 3);
//	    newgame.getBoard().updateFinalScore();
//	    assertEquals(player1.getScore(), 22);
//	    assertEquals(player2.getScore(), 11);
//	}
//	
//	
//}
