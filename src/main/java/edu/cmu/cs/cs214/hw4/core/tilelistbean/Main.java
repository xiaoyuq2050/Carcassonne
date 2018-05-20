//package edu.cmu.cs.cs214.hw4.core.tilelistbean;
//
//
//import java.util.Map;
//
//import edu.cmu.cs.cs214.hw4.core.game_board.Game;
//import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
//import edu.cmu.cs.cs214.hw4.core.tile.Tile;
//
//public class Main {
//
//	public static void main(String[] args) {
////		List<Tile> allTiles;
////		System.out.println("start");
////		TileListBean tileList = parse("src/main/resources/tile_config.yaml");
////		System.out.println(tileList.getName());
////		System.out.println(Arrays.toString(tileList.getTilelist()[0].getSegment()));
////		System.out.println(Arrays.toString(tileList.getTilelist()[0].getCityFeature()));
////		System.out.println("start to convert tilebean to tile...");
////		allTiles = new ArrayList<Tile>();
////		for (TileBean eachtileB : tileList.getTilelist()) {
////			allTiles.add(new Tile(eachtileB));
////		}
////		System.out.println("first segment...");
////		System.out.println(allTiles.get(0).getSegment());
////		System.out.println(allTiles.get(0).getFarmFeature().get(0).getVertexAngle());
////		System.out.println(allTiles.get(2).getCityFeature().get(0).getNeighborAngle());
////		System.out.println(allTiles.get(3).getFarmFeature().get(0).getVertexAngle());
////		System.out.println(allTiles.get(3).getFarmFeature().get(1).getVertexAngle());
////		System.out.println(" ");
//		Game newgame = new Game();
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(0).getCoverPos());
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(0).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getCoverPos());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getVertexPos());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(1).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(1).getVertexPos());
//		newgame = new Game();
//		Player player1 = new Player("1");
//		Tile tile0 = newgame.drawTile(player1, 8);
//		tile0.setPosition(0, 2);
//		Tile tile1 = newgame.drawTile(player1, 7);
//		tile1.setPosition(2, 2);
//		Tile tile2 = newgame.drawTile(player1, 6);
//		tile2.setPosition(0, 4);
//		Tile tile3 = newgame.drawTile(player1, 5);
//		tile3.rotate(90);
//		tile3.setPosition(2, 4);
//		Tile tile4 = newgame.drawTile(player1, 4);
//		tile4.rotate(90);
//		tile4.setPosition(4, 2);
//		Tile tile5 = newgame.drawTile(player1, 3);
//		tile5.setPosition(2, 0);
//		Tile tile6 = newgame.drawTile(player1, 0);
//		tile6.rotate(180);
//		tile6.setPosition(-2, 2);
//		Tile tile7 = newgame.drawTile(player1, 1);
//		tile7.rotate(90);
//		tile7.setPosition(-2, 4);
//		
//		newgame.placeTile(player1, tile0);
//		newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile0).get(0));
//		newgame.updateStatus();
//		Map<Player, Integer> followermap = newgame.getBoard().getCityFeatureList().get(0).getFollowers();
//		for (Player player: followermap.keySet()) {
//			System.out.println("key and values...");
//			System.out.println(player.getName());
//			System.out.println(followermap.get(player));
//		}
//		
//		newgame.placeTile(player1, tile1);
//		newgame.updateStatus();
//		
//		newgame.placeTile(player1, tile2);
//		newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile2).get(0));
//		followermap = newgame.getBoard().getCityFeatureList().get(0).getFollowers();
//		for (Player player: followermap.keySet()) {
//			System.out.println("key and values...");
//			System.out.println(player.getName());
//			System.out.println(followermap.get(player));
//		}
//		followermap = newgame.getBoard().getCityFeatureList().get(1).getFollowers();
//		for (Player player: followermap.keySet()) {
//			System.out.println("key and values...");
//			System.out.println(player.getName());
//			System.out.println(followermap.get(player));
//		}
//		newgame.updateStatus();
//		
//		newgame.placeTile(player1, tile3);
//		newgame.updateStatus();
//		followermap = newgame.getBoard().getCityFeatureList().get(0).getFollowers();
//		for (Player player: followermap.keySet()) {
//			System.out.println("key and values...");
//			System.out.println(player.getName());
//			System.out.println(followermap.get(player));
//		}
//		
//		System.out.println(newgame.placeTile(player1, tile4));
//		newgame.updateStatus();
//		followermap = newgame.getBoard().getCityFeatureList().get(0).getFollowers();
//		for (Player player: followermap.keySet()) {
//			System.out.println("key and values...");
//			System.out.println(player.getName());
//			System.out.println(followermap.get(player));
//		}
//		
//		System.out.println(newgame.placeTile(player1, tile5));
//		newgame.updateStatus();
//		
//
//	    
////		Tile tile1 = newgame.drawTile(player1, 8);
////		Tile tile2 = newgame.drawTile(player1, 7);
////		Tile tile3 = newgame.drawTile(player1, 6);
////		Tile tile4 = newgame.drawTile(player1, 5);
////		Tile tile5 = newgame.drawTile(player1, 4);
////		Tile tile6 = newgame.drawTile(player1, 3);
//////		tile1.rotate(180);
////		tile1.setPosition(0, 2);
////		tile2.setPosition(2, 2);
////		tile3.setPosition(0, 4);
////		tile4.rotate(90);
////		tile4.setPosition(2, 4);
////		tile5.rotate(90);
////		tile5.setPosition(4, 2);
////		tile6.setPosition(2, 0);
////
////		System.out.println(newgame.placeTile(player1, tile1));
////		System.out.println(newgame.placeTile(player1, tile2));
////		System.out.println(newgame.placeTile(player1, tile3));
////		System.out.println(newgame.placeTile(player1, tile4));
////		System.out.println(newgame.placeFollower(player1, newgame.getNewFeatures(player1, tile4).get(0)));
////
////		System.out.println(newgame.placeTile(player1, tile5));
////		System.out.println(newgame.placeTile(player1, tile6));
////		System.out.println("score...");
////		newgame.updateStatus();
////		System.out.println(newgame.getPlayers().get(0).getScore());
////		
////		
////		System.out.println("road feature size...");
////		System.out.println(newgame.getBoard().getRoadFeatureList().size());
////		
////		System.out.println("first road feature...");
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(0).getCoverPos());
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(0).getNeighborEdge());
////		System.out.println("second road feature...");
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(1).getCoverPos());
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(1).getNeighborEdge());
////		System.out.println("third road feature...");
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(2).getCoverPos());
////		System.out.println(newgame.getBoard().getRoadFeatureList().get(2).getNeighborEdge());
////		
////		System.out.println("farm feature size...");
////		System.out.println(newgame.getBoard().getFarmFeatureList().size());
////		System.out.println("first farm feature...");
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getCoverPos());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(0).getVertexPos());
////		System.out.println("second farm feature...");
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(1).getCoverPos());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(1).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(1).getVertexPos());
////		System.out.println("third farm feature...");
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(2).getCoverPos());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(2).getNeighborEdge());
////		System.out.println(newgame.getBoard().getFarmFeatureList().get(2).getVertexPos());
////		System.out.println("city feature size...");
////		System.out.println(newgame.getBoard().getCityFeatureList().size());
////		System.out.println("first city feature...");
////		System.out.println(newgame.getBoard().getCityFeatureList().get(0).getCoverPos());
////		System.out.println(newgame.getBoard().getCityFeatureList().get(0).getNeighborEdge());
////		System.out.println(newgame.getBoard().getCityFeatureList().get(0).getVertexPos());
////		System.out.println(newgame.getBoard().getCityFeatureList().get(0).getBanner());
//////		System.out.println("second city feature...");
//////		System.out.println(newgame.getBoard().getCityFeatureList().get(1).getCoverPos());
//////		System.out.println(newgame.getBoard().getCityFeatureList().get(1).getNeighborEdge());
//////		System.out.println(newgame.getBoard().getCityFeatureList().get(1).getVertexPos());
//////		System.out.println(newgame.getBoard().getCityFeatureList().get(1).getBanner());
//		
//	}
//}
