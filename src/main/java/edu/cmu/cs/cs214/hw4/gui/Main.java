package edu.cmu.cs.cs214.hw4.gui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw4.core.game_board.Game;

/**
 * main method to run carcassonne game.
 * 
 * @author xiaoyuq
 *
 */
public class Main {
	private static final String GAME_NAME = "Carcassonne";

	/**
	 * void main method.
	 * 
	 * @param args
	 *            no args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				createAndShowGameBoard();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private static void createAndShowGameBoard() throws IOException {
		// Create and set-up the window.
		JFrame frame = new JFrame(GAME_NAME);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Game game = new Game();

		// Create and set up the content pane.
		GamePanel gamePanel = new GamePanel(game);
		gamePanel.setOpaque(true);
		frame.setContentPane(gamePanel);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
