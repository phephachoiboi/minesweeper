package com.vamk.minesweeper;

import java.util.Random;

public class GameComponent extends GameBoard {
	private Random random = new Random();

	/** Adding mines into board randomly. */
	public void mineGenerator() {
		int i = 0;
		while (i < mineNumber) {
			int x = random.nextInt(tileColumns - 1);
			int y = random.nextInt(tileRows - 1);
			if (mines[x][y] != 'x') {
				mines[x][y] = 'x';
				i++;
				arrayButtonStatus[x][y].setStatus('x');
			}
		}
	}

	/**
	 * Generate number on each cells/tiles that display number of mines surrounding.
	 * 0 is not displayed.
	 */
	public void numberGenerator() {
		for (int i = 0; i < tileColumns; i++) {
			for (int j = 0; j < tileRows; j++) {
				if (mines[i][j] != 'x')
					mines[i][j] = (char) (countSurround(i, j) + 48);
				if (mines[i][j] == '0')
					mines[i][j] = ' ';
				arrayButtonStatus[i][j].setStatus(mines[i][j]);
			}
		}
	}

	/** Counting how many mines around cell/tile [i,j]. */
	public int countSurround(int i, int j) {
		int count = 0;
		if (i - 1 != -1 && j - 1 != -1 && mines[i - 1][j - 1] == 'x')
			count++;
		if (j - 1 != -1 && mines[i][j - 1] == 'x')
			count++;
		if (i + 1 != tileColumns && j - 1 != -1 && mines[i + 1][j - 1] == 'x')
			count++;
		if (i + 1 != tileColumns && mines[i + 1][j] == 'x')
			count++;
		if (i + 1 != tileColumns && j + 1 != tileRows && mines[i + 1][j + 1] == 'x')
			count++;
		if (j + 1 != tileRows && mines[i][j + 1] == 'x')
			count++;
		if (i - 1 != -1 && j + 1 != tileRows && mines[i - 1][j + 1] == 'x')
			count++;
		if (i - 1 != -1 && mines[i - 1][j] == 'x')
			count++;
		return count;
	}
}
