package com.vamk.minesweeper;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class GameBoard {

	private JButton replayButton;

	public JFrame frame;
	public JPanel mainPanel;

	public static int tileColumns = 16;
	public static int tileRows = 16;
	public static int mineNumber = 40;

	public static char[][] mines = new char[tileColumns][tileRows];
	public static ButtonStatus[][] arrayButtonStatus = new ButtonStatus[tileColumns][tileRows];

	public static int flagCounter = 0;
	public static int openedCellCounter = 0;
	public static int flaggedMineCounter = 0;

	public static int flagNumber = 40;
	public JTextField flagDisplay = new JTextField();

	private int delay = 1000;
	private int seconds = 0;
	private int minutes = 0;
	Timer timer;
	public JTextField timeDisplay = new JTextField();
	private ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (minutes < 10) {
				if (seconds < 59)
					seconds++;
				else {
					minutes++;
					seconds = 0;
				}
				timeDisplay.setText(minutes + " : " + seconds);
				timeDisplay.setBounds(20, 20, 35, 35);
			} else if (minutes >= 10 && minutes < 60) {
				if (seconds < 59)
					seconds++;
				else {
					minutes++;
					seconds = 0;
				}
				timeDisplay.setText(minutes + " : " + seconds);
				timeDisplay.setBounds(20, 20, 40, 35);
			} else if (minutes == 60) {
				disableAllButton();
			}
		}
	};

	/** Time counter. */
	public void timeCounter() {
		timer = new Timer(delay, taskPerformer);
		timer.start();
	}

	public void resetTimerCounter() {
		seconds = 0;
		minutes = 0;
		openedCellCounter = 0;
	}

	public void resetArrayButton() {
		/* Reset Mines. */
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				mines[i][j] = ' ';
			}
		}
		/* Reset arrayButtonStatus. */
		for (int i = 0; i < arrayButtonStatus.length; i++) {
			for (int j = 0; j < arrayButtonStatus[i].length; j++) {

				arrayButtonStatus[i][j].reset();
			}
		}
	}

	/** Replay game. */
	public void replayGame() {
		System.out.println("REPLAY clicked");
		resetTimerCounter();
		resetArrayButton();

		GameComponent replay = new GameComponent();
		replay.mineGenerator();
		replay.numberGenerator();
	}

	/** Showing background and title. */
	public void prepareGUI() {
		frame = new JFrame("Minesweeper");
		mainPanel = new JPanel();
		// mainPanel.setLayout(new FlowLayout());
		mainPanel.setLayout(new GridBagLayout());

		replayButton = new JButton();
		replayButton.setBounds(920, 20, 50, 50);
		replayButton.setText("REPLAY");
		replayButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("Replay is called");
				if (SwingUtilities.isLeftMouseButton(evt))
					replayGame();
			}
		});

		mainPanel.add(replayButton);

		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(tileRows, tileColumns));

		for (int i = 0; i < tileColumns; i++) {
			for (int j = 0; j < tileRows; j++) {
				String nameButton = (i + 1) + "," + (j + 1);
				ButtonStatus button = new ButtonStatus(nameButton, i, j);
				button.SetGameBoard(this);
				arrayButtonStatus[i][j] = button;
				button.setText(nameButton);
				button.setPreferredSize(new Dimension(45, 45));
				button.setText((i + 1) + "," + (j + 1));
				button.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent event) {
						if (button.isEnabled() == false)
							return;
						if (SwingUtilities.isRightMouseButton(event)) {
							OnRightClicked(button);
						} else if (SwingUtilities.isLeftMouseButton(event)) {
							OnLeftClicked(button);
						}
						checkGameWin(button);
					}
				});
				boardPanel.add(button);
			}
		}

		mainPanel.add(timeDisplay);
		mainPanel.add(boardPanel);
		frame.add(mainPanel);
		frame.setSize(1200, 1000);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/** Set right click - flagged. */
	private void OnRightClicked(ButtonStatus btn) {
		System.out.print("Right and ");
		flagCounter++;
		System.out.println("Flag Number = " + flagNumber);
		btn.rightPress();
	}

	/** Set left click - number, bomb, blank. */
	public void OnLeftClicked(ButtonStatus btn) {
		System.out.println("Left");
		openedCellCounter++;
		btn.leftPress();
		System.out.println("Opened Cell Counter = " + openedCellCounter);
		System.out.println("Unopened Cell Counter = " + (256 - openedCellCounter));
	}

	/** Checking game LOSS. */
	public void checkGameLoss(ButtonStatus btn) {
		if (btn.status == 'x') {
			System.out.println(" CHECK GAME LOSS_YOU LOSE");
			showNotMine();
			disableAllButton();
		}
	}

	public void showNotMine() {
		for (int i = 0; i < arrayButtonStatus.length; i++) {
			for (int j = 0; j < arrayButtonStatus[i].length; j++) {
				if (arrayButtonStatus[i][j].flagged == true && arrayButtonStatus[i][j].status != 'x') {
					arrayButtonStatus[i][j].setIcon(new ImageIcon("images/noneBomb.png"));
					arrayButtonStatus[i][j].setDisabledIcon(new ImageIcon("images/noneBomb.png"));
				}
			}
		}
	}

	/** Checking game WIN. */
	public void checkGameWin(ButtonStatus btn) {
		System.out.println("checkGameWin");

		/* Unopened cells is number of bombs. */
		if ((256 - openedCellCounter) == 40 && btn.status != 'x' && btn.isReveal == true) {
			System.out.println("Duy win");
			disableAllButton();
			return;
		}

		int countMineFlagged = 0;
		for (int i = 0; i < arrayButtonStatus.length; i++) {
			for (int j = 0; j < arrayButtonStatus[i].length; j++) {
				if (arrayButtonStatus[i][j].status == 'x' && arrayButtonStatus[i][j].flagged == true) {
					countMineFlagged++;
					if (countMineFlagged == 40) {
						disableAllButton();
						return;
					}
				}
			}
		}
	}

	/** Disable all button at LOSS or WIN. */
	public void disableAllButton() {
		for (int i = 0; i < arrayButtonStatus.length; i++) {
			for (int j = 0; j < arrayButtonStatus[i].length; j++) {
				if (arrayButtonStatus[i][j].status == 'x') {
					arrayButtonStatus[i][j].setIcon(new ImageIcon("images/mBomb.jpg"));
				}
				arrayButtonStatus[i][j].setEnabled(false);
			}
		}
	}
}
