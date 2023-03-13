package game;

import java.util.Scanner;

import gameobjects.Area;
import gameobjects.Player;
import gameobjects.Tile;

public class PlayGame {

	static Scanner sc = new Scanner(System.in);
	public static Tile[] board = Utils.loadBoardTiles();
	public static Player[] players = Utils.loadPlayers();

	public static final int MAX_ALLOWABLE_CFV = 3000;
	public static final int STARTING_CFV = 2000;
	private static final int CFV_PASSING_GO = -200;
	public static final int PRIVATE_ISLAND_BOARD_INDEX = 4;
	public static final int PRIVATE_ISLAND_CFV_PENALTY = 500;
	private static final int SEND_TO_PRIVATE_ISLAND_BOARD_INDEX = 11;

	public static void main(String[] args) {

		int currentPlayerIndex = 0;

		Utils.printLine();

		Utils.printBoard();
		System.out.println();

		int turnNum = 1;

		while (Utils.gameStillAlive()) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Player currentPlayer = players[currentPlayerIndex];

			if (currentPlayer.isAlive()) {

				System.out.println("Turn Number: " + turnNum++);

				Utils.printPlayers();
				// logic of current go
				currentGo(currentPlayer);

				System.out.println();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Utils.printBoard();
				System.out.println();
			}

			currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
		}

		sc.close();

		Player winner = Utils.returnWinner();

		System.out.println("Game over");
		System.out.println("The winner is : " + winner.getName());
	}

	/**
	 * 
	 * @param currentPlayer
	 */
	public static void currentGo(Player currentPlayer) {
		System.out.println();

		System.out.println("Player: " + currentPlayer.getName());
		System.out.println("Press enter to roll dice");
		
		// TODO add condition that player can leave game here - by entering N
		
		PlayGame.sc.nextLine();

		int diceRoll = Utils.diceRoll();
		int playerStartBoardTileIndex = currentPlayer.getCurrentBoardPosition();

		if (diceRoll == -1) {
			// if 3 doubles are rolled
			Utils.sendPlayerToPrivateIsland(currentPlayer);

		} else {
			currentPlayer.updateCurrentBoardPosition(diceRoll);

			int playerNewBoardTileIndex = currentPlayer.getCurrentBoardPosition();
			Tile PlayerNewBoardTile = PlayGame.board[playerNewBoardTileIndex];

			System.out.println();
			System.out.println("Landed on " + PlayerNewBoardTile.getName());

			// special tile logic
			if (playerNewBoardTileIndex == SEND_TO_PRIVATE_ISLAND_BOARD_INDEX) {
				// if land on send to private island, send to private island
				System.out.println("Player Sent to Private Island");
				Utils.sendPlayerToPrivateIsland(currentPlayer);
				
			} else if (playerNewBoardTileIndex <= playerStartBoardTileIndex || diceRoll > 14) {
				// if pass go
				System.out.println("Lost " + -CFV_PASSING_GO + " for reaching go");
				System.out.println();
				currentPlayer.addCFV(CFV_PASSING_GO);
			}

			if (PlayerNewBoardTile instanceof Area) {
				Area boardArea = (Area) PlayerNewBoardTile;
				landedOnAreaLogic(boardArea, currentPlayer);
			}

		}

	}

	/**
	 * 
	 * @param boardArea
	 * @param currentPlayer
	 */
	public static void landedOnAreaLogic(Area boardArea, Player currentPlayer) {
		if (boardArea.getCurrentOwner() == null) {
			// if no one owns board position

			int boardAreaCost = boardArea.getCostToPurchase();
			System.out.println(
					"Area available, price to buy is : " + boardAreaCost + " would you like to purchase? (Y/N)");

			// TODO separate Y/N questions into separate method with validation

			if (PlayGame.sc.nextLine().equalsIgnoreCase("Y")) {
				currentPlayer.addCFV(boardAreaCost);
				boardArea.setCurrentOwner(currentPlayer);
				System.out.println("Area purchased for: " + boardAreaCost);
			} else {
				System.out.println();
				System.out.println("Area put up for auction");
				Utils.auction(boardArea);
			}
			
		} else if (Utils.doesCurrentPlayerOwnZone(currentPlayer, boardArea.getZone())) {
			// if current player owns zone

			int boardAreaDevCost = boardArea.getCostToDevelop();
			System.out.println("You own this zone, price to develop on " + boardArea.getName() + "is : "
					+ boardAreaDevCost + " would you like to develop? (Y/N)");
			if (PlayGame.sc.nextLine().equalsIgnoreCase("Y")) {
				currentPlayer.addCFV(boardAreaDevCost);
				boardArea.incrementDevelopmentStatus();
				System.out.println(
						"Area develped to level " + boardArea.getDevelopmentStatus() + " for: " + boardAreaDevCost);
			}
			
		} else if (currentPlayer == boardArea.getCurrentOwner()) {
			// if current player owns area only

			System.out.println("You own this area, nothing happens");
			
		} else {
			// if another player owns board position

			System.out.println("Area owned by " + boardArea.getCurrentOwner().getName());
			int rentDue = boardArea.currentRent();

			System.out.println("Rent of " + rentDue + " paid from " + currentPlayer.getName() + " to "
					+ boardArea.getCurrentOwner().getName());

			boardArea.getCurrentOwner().addCFV(-rentDue);
			currentPlayer.addCFV(rentDue);

			// if paying rent bankrupts player
			if (!currentPlayer.isAlive()) {
				System.out
						.println(currentPlayer.getName() + " has reached max CFV level and has been removed from game");
				Utils.transferAssetsFromDeadPlayer(currentPlayer, boardArea.getCurrentOwner());
			}
		}
	}

}
