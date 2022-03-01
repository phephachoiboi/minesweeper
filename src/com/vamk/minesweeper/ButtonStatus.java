package com.vamk.minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonStatus extends JButton {

	private static final long serialVersionUID = 1L;
	public int column;
	public int row;
	public boolean isReveal = false; // check if button is clicked or not
	public char status = ' '; // set any checked cells is blank
	public boolean flagged = false; // insert flag by right click

	private GameBoard gameBoard;

	public ButtonStatus(String nameButton, int col, int row) {
		super(nameButton);
		this.column = col;
		this.row = row;

		isReveal = false;
		status = ' ';
		flagged = false;
	}

	public void SetGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

	}

	public void rightPress() {
		flagged = !flagged;
		if (flagged == true) {
			GameBoard.flagNumber--;
			this.setIcon(new ImageIcon("images/flag.png"));
		} else {
			GameBoard.flagNumber++;
			this.setIcon(null);
		}

	}

	public boolean isFlagged() {
		return this.flagged;
	}

	public void setStatus(char status) {
		this.status = status;
		this.setText("");
		// this.setText(status + "");
	}

	public char getStatus() {
		return this.status;
	}

	/** On left clicked to this button. */
	public void leftPress() {
		if (flagged || isReveal) {
			return;
		}
		this.isReveal = true;
		this.setEnabled(false);

		if (this.status == 'x') {
			this.setIcon(new ImageIcon("images/newBomb.png"));
			this.setDisabledIcon(new ImageIcon("images/newBomb.png"));
			if (gameBoard != null) {
				gameBoard.showNotMine();
				gameBoard.disableAllButton();
			}
		}

		if (this.status != ' ' && this.status != 'x') {
			this.setText(this.status + "");
		}

		if (this.status == ' ') {
			if (column - 1 >= 0 && row - 1 >= 0 && GameBoard.arrayButtonStatus[column - 1][row - 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column - 1][row - 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column - 1][row - 1].flagged) {
				GameBoard.arrayButtonStatus[column - 1][row - 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (row - 1 >= 0 && GameBoard.arrayButtonStatus[column][row - 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column][row - 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column][row - 1].flagged) {
				GameBoard.arrayButtonStatus[column][row - 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (column + 1 <= 15 && row - 1 >= 0 && GameBoard.arrayButtonStatus[column + 1][row - 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column + 1][row - 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column + 1][row - 1].flagged) {
				GameBoard.arrayButtonStatus[column + 1][row - 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (column + 1 <= 15 && GameBoard.arrayButtonStatus[column + 1][row].status != 'x'
					&& GameBoard.arrayButtonStatus[column + 1][row].isReveal == false
					&& !GameBoard.arrayButtonStatus[column + 1][row].flagged) {
				GameBoard.arrayButtonStatus[column + 1][row].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (column + 1 <= 15 && row + 1 <= 15 && GameBoard.arrayButtonStatus[column + 1][row + 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column + 1][row + 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column + 1][row + 1].flagged) {
				GameBoard.arrayButtonStatus[column + 1][row + 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (row + 1 <= 15 && GameBoard.arrayButtonStatus[column][row + 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column][row + 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column][row + 1].flagged) {
				GameBoard.arrayButtonStatus[column][row + 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (column - 1 >= 0 && row + 1 <= 15 && GameBoard.arrayButtonStatus[column - 1][row + 1].status != 'x'
					&& GameBoard.arrayButtonStatus[column - 1][row + 1].isReveal == false
					&& !GameBoard.arrayButtonStatus[column - 1][row + 1].flagged) {
				GameBoard.arrayButtonStatus[column - 1][row + 1].leftPress();
				GameBoard.openedCellCounter++;
			}

			if (column - 1 >= 0 && GameBoard.arrayButtonStatus[column - 1][row].status != 'x'
					&& GameBoard.arrayButtonStatus[column - 1][row].isReveal == false
					&& !GameBoard.arrayButtonStatus[column - 1][row].flagged) {
				GameBoard.arrayButtonStatus[column - 1][row].leftPress();
				GameBoard.openedCellCounter++;
			}

		}

		if ((256 - GameBoard.openedCellCounter) == 40 && this.status == 'x') {
			if (gameBoard != null) {
				gameBoard.disableAllButton();
			}
		}
	}

	public void reset() {
		this.status = ' ';
		this.isReveal = false;
		this.flagged = false;
		this.setIcon(null);
		this.setEnabled(true);
	}

}
