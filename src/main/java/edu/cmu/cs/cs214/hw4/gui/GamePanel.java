package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import edu.cmu.cs.cs214.hw4.core.feature.CityFeature;
import edu.cmu.cs.cs214.hw4.core.feature.CloisterFeature;
import edu.cmu.cs.cs214.hw4.core.feature.FarmFeature;
import edu.cmu.cs.cs214.hw4.core.feature.Feature;
import edu.cmu.cs.cs214.hw4.core.feature.RoadFeature;
import edu.cmu.cs.cs214.hw4.core.feature.TypeBean;
import edu.cmu.cs.cs214.hw4.core.game_board.Game;
import edu.cmu.cs.cs214.hw4.core.game_board.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.player_follower.Player;
import edu.cmu.cs.cs214.hw4.core.tile.Position;
import edu.cmu.cs.cs214.hw4.core.tile.Tile;

/**
 * Gui of the game which implements GameChangeListener.
 * 
 * @author xiaoyuq
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements GameChangeListener {
	private boolean playerAdded = false;
	private boolean firstTile = true;
	private boolean placeFollowerState = false;
	private boolean placeTileState = true;
	private final Game game;
	private Tile currentTile;
	private int currentPIdx;
	private final JButton currentTileButton;
	private BufferedImage myPicture;
	private JLabel currentPlayerLabel;
	private final JPanel gameStatePanel;
	private final JScrollPane boardPanel;
	private final JPanel playerInfoPanel;
	private final JLabel followerInfoLabel;
	private final JButton addPlayer;
	private final JButton startGame;
	private final JButton nextPlayer;

	private final List<JLabel> playerLabels;
	private final Map<JButton, Position> buttonPosMap;
	private Map<JButton, Boolean> buttonPlacedMap;
	private static final int FOLLOWER_R = 10;
	private static final int TILE_L = 100;
	private static final int BOARDTILE_LENGTH = 2;
	private static final Dimension BUTTONDIMENSION = new Dimension(100, 100);
	private static final Color[] P_COLOR = { Color.BLACK, Color.cyan, Color.green, Color.orange, Color.white };

	/**
	 * The Constructor of the panel, including player info panel on the east, game
	 * state panel on the north, and board in the center.
	 * 
	 * @param g
	 *            the game
	 */
	public GamePanel(Game g) {
		game = g;
		game.addGameChangeListener(this);

		currentPIdx = 0;

		currentTile = null;
		currentPlayerLabel = new JLabel("No Player yet");
		currentTileButton = new JButton();
		currentTileButton.setPreferredSize(BUTTONDIMENSION);
		currentTileButton.addActionListener((e) -> {
			if (!game.isBegin()) {
				JOptionPane.showMessageDialog(null, "Start game first!");
			} else {
				if (!placeTileState || currentTile == null) {
				    JOptionPane.showMessageDialog(null, "Click next player to draw a new tile!");
			    } else {
				    currentTile.rotate(90);
				    myPicture = createRotatedCopy(myPicture);
				    currentTileButton.setIcon(new ImageIcon(myPicture));
			    }
			}
		});
		
		followerInfoLabel = new JLabel("(Here shows follower placement information)", SwingConstants.CENTER);
		followerInfoLabel.setPreferredSize(new Dimension(700, 100));
		
		nextPlayer = new JButton("next player");
		nextPlayer.setPreferredSize(new Dimension(100, 50));
		nextPlayer.addActionListener((e) -> {
			if (!game.isBegin()) {
				JOptionPane.showMessageDialog(null, "Start game first!");
				return;
			}
			followerInfoLabel.setText("(Here shows follower placement information)");
			game.updateStatus();
			currentPIdx = (currentPIdx + 1) % game.getPlayers().size();
			currentPlayerLabel.setText("Current Player: " + game.getPlayers().get(currentPIdx).getName());
			try {
				placeTileState = true;
				game.drawTile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		gameStatePanel = new JPanel();
		gameStatePanel.setPreferredSize(new Dimension(700, 150));
		gameStatePanel.add(currentPlayerLabel);

		gameStatePanel.add(currentTileButton);
		gameStatePanel.add(nextPlayer);
		
		buttonPosMap = new HashMap<JButton, Position>();
		buttonPlacedMap = new HashMap<JButton, Boolean>();
		JButton b1;
		b1 = new JButton("starting tile");
		b1.setPreferredSize(BUTTONDIMENSION);
		b1.addActionListener((e) -> {
			if (game.isBegin()) {
				JOptionPane.showMessageDialog(null, "You cannot place anything on the starting tile!");
			} else {
				JOptionPane.showMessageDialog(null, "Start game first!");
			}
		});
		buttonPosMap.put(b1, new Position(0, 0));
		buttonPlacedMap.put(b1, true);

		boardPanel = new JScrollPane();
		updateBoardPanel();
		
		playerInfoPanel = new JPanel();
		playerInfoPanel.setPreferredSize(new Dimension(100, 700));
		playerInfoPanel.setLayout(new GridLayout(7, 1));
		addPlayer = new JButton("add player");
		addPlayer.setPreferredSize(new Dimension(50, 30));
		addPlayer.addActionListener((e) -> {
			if (playerAdded) {
				JOptionPane.showMessageDialog(null, "You cannot add new player during the game!");
				return;
			}
			String playerName = JOptionPane.showInputDialog("Type in player's name:");
			if (playerName != null) {
				game.addPlayer(new Player(playerName));
			}
		});
		playerInfoPanel.add(addPlayer);
		
		startGame = new JButton("start game");
		startGame.setPreferredSize(new Dimension(50, 30));
		startGame.addActionListener((e) -> {
			if (game.isBegin()) {
				JOptionPane.showMessageDialog(null, "You cannot start new game until current game is over!");
				return;
			}
			if (game.getPlayers().size() < 2) {
				JOptionPane.showMessageDialog(null, "Please add more players!");
			} else {
				try {
					playerAdded = true;
					currentPIdx = 0;
					currentPlayerLabel.setText("Current Player:" + game.getPlayers().get(currentPIdx).getName());
					game.startNewGame();
					placeTileState = true;
					game.drawTile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		playerInfoPanel.add(startGame);

		playerLabels = new ArrayList<JLabel>();
		for (int i = 0; i < 5; i++) {
			JLabel playerLabel = new JLabel();
			playerLabel.setPreferredSize(new Dimension(20, 20));
			playerLabels.add(new JLabel());
			playerInfoPanel.add(playerLabels.get(i));
		}
		
		setLayout(new BorderLayout());
		add(gameStatePanel, BorderLayout.NORTH);
		add(boardPanel, BorderLayout.CENTER);
		add(playerInfoPanel, BorderLayout.EAST);
		add(followerInfoLabel, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(800, 800));
	}

	@Override
	public void addNewPlayer(Player player) {
		playerLabels.get(currentPIdx).setText("<html>Name: " + player.getName() + "<br>Score: " + player.getScore()
				+ "<br>Followers: " + player.getNumUsedFollower() + "</html>");
		currentPIdx++;
		if (currentPIdx == 5) {
			playerInfoPanel.remove(addPlayer);
			JOptionPane.showMessageDialog(null, "Start the game now!");
		}
	}

	@Override
	public void drawNewTile(Tile newTile) throws IOException {
		currentTile = newTile;
		String imageID = "src/main/resources/" + newTile.getImageID() + ".png";
		myPicture = resize(ImageIO.read(new File(imageID)));
		currentTileButton.setIcon((new ImageIcon(myPicture)));
	}

	@Override
	public void showInvalidTilePlacementDialog() {
		JOptionPane.showMessageDialog(null, "Invalid Tile Placement!");
	}

	@Override
	public void showNoEnoughFollowers() {
		JOptionPane.showMessageDialog(null, "There is no followers left!");
	}

	@Override
	public void showAlreadyPlaceFollower() {
		JOptionPane.showMessageDialog(null, "Alreadly placed a follower!");
	}

	@Override
	public void showNoMoreFollower() {
		JOptionPane.showMessageDialog(null, "You don't have followers left!");
	}
	
	@Override
	public void showGameOver() {
		StringBuffer totalScoreMsg = new StringBuffer("");
		for (Player player : game.getPlayers()) {
			totalScoreMsg.append("<br>Player: " + player.getName() + "<br>Score: " + player.getScore());
		}
		JOptionPane.showMessageDialog(null, "<html>Total score:" + totalScoreMsg + "</html>");
	}

	@Override
	public void placeNewTile(Tile tile) throws IOException {
		if (firstTile) {
			String imageID = "src/main/resources/" + tile.getImageID() + ".png";
			BufferedImage myPicture = resize(ImageIO.read(new File(imageID)));
			for (Map.Entry<JButton, Position> entry: buttonPosMap.entrySet()) {
				if (tile.getPosition().equals(entry.getValue())) {
					entry.getKey().setIcon(new ImageIcon(myPicture));
				}
			}
			firstTile = false;
		} else {
			for (Map.Entry<JButton, Position> entry: buttonPosMap.entrySet()) {
				if (tile.getPosition().equals(entry.getValue())) {
					entry.getKey().setIcon(currentTileButton.getIcon());
				}
			}
			placeFollowerState = true;
		}
		currentTileButton.setIcon(new ImageIcon());
		updateNeighborButton(tile);
	}

	private void updateNeighborButton(Tile tile) {
		List<Position> neighborPosList = new ArrayList<Position>();
		Position up = new Position(tile.getPosition().getX(), tile.getPosition().getY() + 2);
		Position down = new Position(tile.getPosition().getX(), tile.getPosition().getY() - 2);
		Position left = new Position(tile.getPosition().getX() - 2, tile.getPosition().getY());
		Position right = new Position(tile.getPosition().getX() + 2, tile.getPosition().getY());
		neighborPosList.add(up);
		neighborPosList.add(down);
		neighborPosList.add(left);
		neighborPosList.add(right);
		int count = 0;
		for (Position pos : neighborPosList) {
			if (buttonPosMap.containsValue(pos)) {
				count++;
			} else {
				JButton newButton = new JButton("newButton");
				newButton.setPreferredSize(BUTTONDIMENSION);
				buttonPlacedMap.put(newButton, false);
				newButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (placeTileState) {
							if (buttonPlacedMap.get(newButton)) {
								JOptionPane.showMessageDialog(null, "You can only place tile on empty buttons!");
								return;
							}
							buttonPlacedMap.put(newButton, buttonListenToPlaceTile(newButton, pos));
							return;
						}
						if (!placeTileState) {
							if (game.getPlayers().get(currentPIdx).getNumUsedFollower() == 0) {
								showNoMoreFollower();
								return;
							}
							if (!placeFollowerState) {
								showAlreadyPlaceFollower();
								return;
							}
							if (!buttonPosMap.get(newButton).equals(currentTile.getPosition())) {
								JOptionPane.showMessageDialog(null, "You can only place follower on newly-placed tile!");
								return;
							}
							int x = e.getX() + 10;
							int y = e.getY() + 8;
							boolean placed = false;
							List<Feature> newFeatureList = game.getNewFeatures(game.getPlayers().get(currentPIdx),
									currentTile);
							if (x >= TILE_L / 2 - 2 * FOLLOWER_R && x <= TILE_L / 2 + 2 * FOLLOWER_R
									&& y >= TILE_L / 2 - 2 * FOLLOWER_R && y <= TILE_L / 2 + 2 * FOLLOWER_R) {
								placed = placeOnCloister(newButton, newFeatureList, x, y, currentPIdx, game);
								if (placed) {
									followerInfoLabel.setText("You placed a follower on cloister, click on 'next player'");
								}
							}
							if (!placed && ((x >= TILE_L / 2 - FOLLOWER_R && x <= TILE_L / 2 + FOLLOWER_R)
									|| (y >= TILE_L / 2 - FOLLOWER_R && y <= TILE_L / 2 + FOLLOWER_R))) {
								placed = placeOnRoad(newButton, buttonPosMap.get(newButton), newFeatureList, x, y,
										currentPIdx, game, currentTile);
							}
							if (!placed) {
								placed = placeOnCity(newButton, buttonPosMap.get(newButton), newFeatureList, x, y,
										currentPIdx, game);
							}
							if (!placed) {
								placed = placeOnFarm(newButton, buttonPosMap.get(newButton), newFeatureList, x, y, currentPIdx,
										game);
							}
							game.updateStatus();
							for (int i = 0; i < game.getPlayers().size(); i++) {
								playerLabels.get(i)
								.setText("<html>Name: " + game.getPlayers().get(i).getName()
										+ "<br>Score: " + game.getPlayers().get(i).getScore()
										+ "<br>Followers: "
										+ game.getPlayers().get(i).getNumUsedFollower() + "</html>");
							}
						}
					}
				});
				buttonPosMap.put(newButton, pos);
			}
		}
		if (count < 4) {
			updateBoardPanel();
		}
	}

	private boolean placeOnFarm(JButton newButton, Position buttonPos, List<Feature> newFeatureList, int x,
			int y, int currentPIdx, Game game) {
		List<FarmFeature> newFarms = new ArrayList<FarmFeature>();
		for (Feature feature : newFeatureList) {
			if (feature.getType().equals(TypeBean.farm)) {
				newFarms.add((FarmFeature) feature);
			}
		}
		if (newFarms.size() == 1) {
			if (game.placeFollower(game.getPlayers().get(currentPIdx), newFarms.get(0))) {
				newButton.setIcon(
						new ImageIcon(withCircle((BufferedImage) iconToImage(newButton.getIcon()),
								P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
				placeFollowerState = false;
				followerInfoLabel.setText("You placed a follower on farm, click on 'next player'");
				return true;
			} else {
				placeFollowerState = true;
				followerInfoLabel.setText("<html>This farm is already occupied.<br>Try another feature or click on 'next player'</html>");
				return false;
			}
		}
		if (newFarms.size() > 1) {
			for (FarmFeature farmF : newFarms) {
				Position closestVertexPos = findClosestVertexPos(farmF, xCoorToPos(x, buttonPos),
						yCoorToPos(y, buttonPos));
				if (dist(closestVertexPos.getX(), closestVertexPos.getY(), xCoorToPos(x, buttonPos),
						yCoorToPos(y, buttonPos)) <= Math.sqrt(2)) {
					if (game.placeFollower(game.getPlayers().get(currentPIdx), farmF)) {
						newButton.setIcon(new ImageIcon(
								withCircle((BufferedImage) iconToImage(newButton.getIcon()),
										P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
						placeFollowerState = false;
						followerInfoLabel.setText("You placed a follower on farm, click on 'next player'");
						return true;
					} else {
						placeFollowerState = true;
						followerInfoLabel.setText("<html>This farm is occupied.<br>Try another feature or click on next player</html>");
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean placeOnCity(JButton newButton, Position buttonPos, List<Feature> newFeatureList,
			int x, int y, int currentPIdx, Game game) {
		List<CityFeature> newCities = new ArrayList<CityFeature>();
		for (Feature feature : newFeatureList) {
			if (feature.getType().equals(TypeBean.city)) {
				newCities.add((CityFeature) feature);
			}
		}
		if (newCities.size() == 0) {
			return false;
		}
		if (newCities.size() == 1) {
			Position edge = findClosestNeighborEdge(newCities.get(0), xCoorToPos(x, buttonPos), yCoorToPos(y, buttonPos));
			if (dist(edge.getX(), edge.getY(), xCoorToPos(x, buttonPos),
					yCoorToPos(y, buttonPos)) <= Math.pow(BOARDTILE_LENGTH / 2, 2)) {
				if (game.placeFollower(game.getPlayers().get(currentPIdx), newCities.get(0))) {
					newButton.setIcon(new ImageIcon(
							withCircle((BufferedImage) iconToImage(newButton.getIcon()),
									P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
					placeFollowerState = false;
					followerInfoLabel.setText("You placed a follower on city, click on 'next player'");
				} else {
					placeFollowerState = true;
					followerInfoLabel.setText("<html>This city is occupied.<br>Try another feature or click on next player</html>");
				}
				return true;
			}
		}
		if (newCities.size() > 1) {
			for (CityFeature cityF : newCities) {
				Position closestNeighborEdge = findClosestNeighborEdge(cityF,
						buttonPosMap.get(newButton));
				if (dist(closestNeighborEdge.getX(), closestNeighborEdge.getY(),
						xCoorToPos(x, buttonPos),
						yCoorToPos(y, buttonPos)) <= Math.pow(BOARDTILE_LENGTH / 2, 2)) {
					if (game.placeFollower(game.getPlayers().get(currentPIdx), cityF)) {
						newButton.setIcon(new ImageIcon(
								withCircle((BufferedImage) iconToImage(newButton.getIcon()),
										P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
						placeFollowerState = false;
						followerInfoLabel.setText("You placed a follower on city, click on 'next player'");
					} else {
						placeFollowerState = true;
						followerInfoLabel.setText("<html>This city is occupied.<br>Try another feature or click on next player</html>");
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean placeOnRoad(JButton newButton, Position buttonPos, List<Feature> newFeatureList,
			int x, int y, int currentPIdx, Game game, Tile currentTile) {
		List<RoadFeature> newRoads = new ArrayList<RoadFeature>();
		for (Feature feature : newFeatureList) {
			if (feature.getType().equals(TypeBean.road)) {
				newRoads.add((RoadFeature) feature);
			}
		}
		if (newRoads.size() == 0) {
			return false;
		}
		if (newRoads.size() == 1) {
			Position edge = findClosestNeighborEdge(newRoads.get(0), xCoorToPos(x, buttonPos), yCoorToPos(y, buttonPos));
			if (dist(edge.getX(), edge.getY(), xCoorToPos(x, buttonPos),
					yCoorToPos(y, buttonPos)) <= Math.pow(BOARDTILE_LENGTH / 2, 2)) {
				if (game.placeFollower(game.getPlayers().get(currentPIdx), newRoads.get(0))) {
					newButton.setIcon(new ImageIcon(
							withCircle((BufferedImage) iconToImage(newButton.getIcon()),
									P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
					placeFollowerState = false;
					followerInfoLabel.setText("You placed a follower on road, click on 'next player'");
				} else {
					placeFollowerState = true;
					followerInfoLabel.setText("<html>This road is occupied.<br>Try another feature or click on next player</html>");
				}
				return true;
			}
		}
		if (newRoads.size() > 1) {
			for (RoadFeature roadF : newRoads) {
				Position closestNeighborEdge = findClosestNeighborEdge(roadF,
						buttonPosMap.get(newButton));
				if (dist(closestNeighborEdge.getX(), closestNeighborEdge.getY(),
						xCoorToPos(x, buttonPos),
						yCoorToPos(y, buttonPos)) <= Math.pow(BOARDTILE_LENGTH / 2, 2)) {
					if (game.placeFollower(game.getPlayers().get(currentPIdx), roadF)) {
						newButton.setIcon(new ImageIcon(
								withCircle((BufferedImage) iconToImage(newButton.getIcon()),
										P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
						placeFollowerState = false;
						followerInfoLabel.setText("You placed a follower on road, click on 'next player'");
					} else {
						placeFollowerState = true;
						followerInfoLabel.setText("<html>This road is occupied.<br>Try another feature or click on next player</html>");
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean placeOnCloister(JButton newButton, List<Feature> newFeatureList, int x, int y,
			int currentPIdx, Game game) {
		List<CloisterFeature> newCloisters = new ArrayList<CloisterFeature>();
		for (Feature feature : newFeatureList) {
			if (feature.getType().equals(TypeBean.cloister)) {
				newCloisters.add((CloisterFeature) feature);
			}
		}
		if (newCloisters.size() == 0) {
			return false;
		}
		game.placeFollower(game.getPlayers().get(currentPIdx), newCloisters.get(0));
		newButton.setIcon(new ImageIcon(withCircle((BufferedImage) iconToImage(newButton.getIcon()),
				P_COLOR[currentPIdx], x, y, FOLLOWER_R)));
		placeFollowerState = false;
		return true;
	}
	
	private boolean buttonListenToPlaceTile(JButton newButton, Position pos) {
		currentTile.setPosition(pos.getX(), pos.getY());
		try {
			if (game.placeTile(currentTile, this)) {
				placeTileState = false;
				placeFollowerState = true;
				if (game.getPlayers().get(currentPIdx).getNumUsedFollower() == 0) {
					followerInfoLabel.setText("You don't have followers left, click on 'next player'");
				} else {
					followerInfoLabel.setText("Place a valid follower or click on 'next player'");
				}
				return true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	private void updateBoardPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		int min = 0;
		int max = 0;
		for (Position pos : buttonPosMap.values()) {
			if (pos.getX() < min) {
				min = pos.getX();
			}
			if (pos.getY() > max) {
				max = pos.getY();
			}
		}
		for (Map.Entry<JButton, Position> entry: buttonPosMap.entrySet()) {
			gbc.gridx = entry.getValue().getX() - min;
			gbc.gridy = max - entry.getValue().getY();
			panel.add(entry.getKey(), gbc);
		}
		boardPanel.setViewportView(panel);
	}

	private static BufferedImage createRotatedCopy(BufferedImage img) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(-90), (double) img.getWidth() / 2, (double) img.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		return img;
	}

	private static BufferedImage resize(BufferedImage img) {
		Image tmp = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	private static BufferedImage withCircle(BufferedImage src, Color color, int x, int y, int radius) {
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

		Graphics2D g = (Graphics2D) dest.getGraphics();
		g.drawImage(src, 0, 0, null);
		g.setColor(color);
		g.fillOval(x - radius, y - radius, radius, radius);
		g.dispose();

		return dest;
	}

	static Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			BufferedImage image = gc.createCompatibleImage(w, h);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
	}
	
	private static Position findClosestNeighborEdge(RoadFeature roadF, double x, double y) {
		double minDist = Double.MAX_VALUE;
		Position res = null;
		for (Position neighborEdge: roadF.getAllNeighborEdge()) {
			if (Math.pow(neighborEdge.getX() - x, 2) + Math.pow(neighborEdge.getY() - y, 2) < minDist) {
				minDist = Math.pow(neighborEdge.getX() - x, 2) + Math.pow(neighborEdge.getY() - y, 2);
				res = neighborEdge;
			}
		}
		return res;
	}
	
	private static Position findClosestNeighborEdge(RoadFeature roadF, Position pos) {
		for (Position neighborEdge : roadF.getAllNeighborEdge()) {
			if (Math.abs(neighborEdge.getX() - pos.getX()) + Math.abs(neighborEdge.getY() - pos.getY()) == 1) {
				return neighborEdge;
			}
		}
		return null;
	}

	private static Position findClosestNeighborEdge(CityFeature cityF, double x, double y) {
		double minDist = Double.MAX_VALUE;
		Position res = null;
		for (Position neighborEdge: cityF.getAllNeighborEdge()) {
			if (Math.pow(neighborEdge.getX() - x, 2) + Math.pow(neighborEdge.getY() - y, 2) < minDist) {
				minDist = Math.pow(neighborEdge.getX() - x, 2) + Math.pow(neighborEdge.getY() - y, 2);
				res = neighborEdge;
			}
		}
		return res;
	}
	
	private static Position findClosestNeighborEdge(CityFeature cityF, Position pos) {
		for (Position neighborEdge : cityF.getAllNeighborEdge()) {
			if (Math.abs(neighborEdge.getX() - pos.getX()) + Math.abs(neighborEdge.getY() - pos.getY()) == 1) {
				return neighborEdge;
			}
		}
		return null;
	}

	private static Position findClosestVertexPos(FarmFeature farmF, double x, double y) {
		double minDist = Double.MAX_VALUE;
		Position closestVertexPos = null;
		for (Position pos : farmF.getVertexPos()) {
			if (dist(pos.getX(), pos.getY(), x, y) < minDist) {
				minDist = dist(pos.getX(), pos.getY(), x, y);
				closestVertexPos = pos;
			}
		}
		return closestVertexPos;
	}

	private static double dist(int x1, int y1, double x2, double y2) {
		return Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
	}

	private static double xCoorToPos(int x, Position tilePosition) {
		return ((float) (x - TILE_L / 2) / (float) (TILE_L / 2)) + tilePosition.getX();
	}

	private static double yCoorToPos(int y, Position tilePosition) {
		return -((float) (y - TILE_L / 2) / (float) (TILE_L / 2)) + tilePosition.getY();
	}

}
