package com.vamk.minesweeper;

public class Main {

	public static void main(String[] args) {
		GameBoard setGameBoard = new GameBoard();
		setGameBoard.prepareGUI();
		setGameBoard.timeCounter();
		GameComponent setGameComponent = new GameComponent();
		setGameComponent.mineGenerator();
		setGameComponent.numberGenerator();
	}
}
