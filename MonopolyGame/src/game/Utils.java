package game;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
//import java.util.Scanner;

import gameobjects.Area;
import gameobjects.Player;
import gameobjects.SpecialTile;
import gameobjects.Tile;
import gameobjects.Zone;

public class Utils {

	private static int AUCTION_START_PRICE = 50;

	/**
	 * 
	 * @return
	 */
	public static int diceRoll() {
		Random random = new Random();
		int total = 0;
		int dice1, dice2;
		int numDoubles = 0;

		do {
			dice1 = (random.nextInt(4)) + 1;
			dice2 = (random.nextInt(4)) + 1;
			total += dice1 + dice2;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Rolled " + dice1 + " and " + dice2);

			if (dice1 == dice2) {
				numDoubles++;

				if (numDoubles < 3) {
					System.out.println("Got a double! Rolling again...\n");
				} else {
					System.out.println("Rolled three doubles, sending you directly to the private island");
					return -1;
				}

			}
		} while (dice1 == dice2);

		System.out.println("You rolled " + total);
		return total;

	}

	/**
	 * 
	 * @return
	 */
	public static Tile[] loadBoardTiles() {

		Tile[] boardTiles = new Tile[14];

		boardTiles[0] = new SpecialTile("Go");
		boardTiles[1] = new Area("Wind Farm", 120, 30, 50, Zone.RENEWABLE_ENERGY);
		boardTiles[2] = new Area("Hydroelectric Dam", 150, 60, 80, Zone.RENEWABLE_ENERGY);
		boardTiles[3] = new Area("Solar Farm", 180, 90, 110, Zone.RENEWABLE_ENERGY);

		boardTiles[4] = new SpecialTile("Private Island");
		boardTiles[5] = new Area("Recycling Plant", 220, 120, 140, Zone.REDUCE_REUSE_RECYCLE);
		boardTiles[6] = new Area("Second Hand Clothes Shop", 280, 150, 170, Zone.REDUCE_REUSE_RECYCLE);

		boardTiles[7] = new SpecialTile("Free Space");
		boardTiles[8] = new Area("Public Transport Hub", 320, 180, 200, Zone.CARBON_FOOTPRINT);
		boardTiles[9] = new Area("Farmer's Market", 350, 210, 230, Zone.CARBON_FOOTPRINT);
		boardTiles[10] = new Area("Forest", 380, 240, 260, Zone.CARBON_FOOTPRINT);

		boardTiles[11] = new SpecialTile("Fly to Private Island");
		boardTiles[12] = new Area("Ocean Clean-up Facility", 420, 270, 290, Zone.REAPAIRING_PREVIOUS_DAMAGE);
		boardTiles[13] = new Area("CO2 Recapture", 480, 300, 320, Zone.REAPAIRING_PREVIOUS_DAMAGE);

		return boardTiles;
	}

	/**
	 * 
	 * @return
	 */
	public static Player[] loadPlayers() {
		
		

		int numPlayers = 0;
		do {
			System.out.println("Please enter number of players (2-4)");

			try {
				numPlayers = PlayGame.sc.nextInt();
			} catch (Exception e) {
				System.out.println("Incorrect input");
			}

			PlayGame.sc.nextLine();

			switch (numPlayers) {
			case 2:
			case 3:
			case 4:
				System.out.println(numPlayers + " players selected");
				break;
			default:
				System.out.println("Only 2-4 players allowed");
				break;
			}

		} while (numPlayers < 2 || numPlayers > 4);

		Player[] players = new Player[numPlayers];

		for (int loop = 1; loop <= numPlayers; loop++) {
			System.out.println();
			System.out.println("Please enter player " + loop + " name");

			// TODO add data validation for player name
			String name;
			do {
				name = PlayGame.sc.nextLine();
				
				if (name.length()<1) {
					System.out.println("Please enter a longer name");
				} else if (name.length()>10) {
					System.out.println("Max character length is 10. Please enter a shorter name");
				}
				
			} while (name.length()<1 || name.length()>10);
			
			char specialChar = selectSpecialChar();
			players[loop - 1] = new Player(name, specialChar, PlayGame.STARTING_CFV, 0);
		}

		System.out.println();
		return players;
	}
	
	public static ArrayList<String> loadSpecialChars() {
		ArrayList<String> specialChars = new ArrayList<>();
		Collections.addAll(specialChars, "*", "£", "?", "$");
		return specialChars;
	}

	public static char selectSpecialChar() {		
		System.out.println("Please select character to represent you on the board:");
		for (int loop = 0; loop < PlayGame.specialChars.size(); loop++) {
			System.out.println(loop + 1 + ": " + PlayGame.specialChars.get(loop));
		}

		int selection = 0;
		
		do {
			try {
				selection = PlayGame.sc.nextInt();
			} catch (Exception e) {
				selection = 0;
			}
			PlayGame.sc.nextLine();

			if (selection < 1 || selection > PlayGame.specialChars.size()) {
				System.out.println("Please input a number between 1 and " + PlayGame.specialChars.size());
			}
		} while (selection < 1 || selection > PlayGame.specialChars.size());

		char selectedChar = PlayGame.specialChars.get(selection-1).charAt(0);
		PlayGame.specialChars.remove(selection-1);
		
		System.out.println(selectedChar + " selected");

		return selectedChar;
	}

	/**
	 * 
	 * @param board
	 */
	public static void printBoard() {
		System.out.printf("%-40s %-10s \n", "", "Costs");
		System.out.printf("%-5s %-25s %-8s %-8s %-8s %-8s %-28s %-10s%-10s \n", "No.", "Tile Name", "Buy", "Rent",
				"Dev", "Status", "Zone", "Owner", "Player");

		for (int loop = 0; loop < PlayGame.board.length; loop++) {
			System.out.printf("%-5d ", loop);
			PlayGame.board[loop].printDetails();

			for (Player player : PlayGame.players) {
				if (player.getCurrentBoardPosition() == loop && player.isAlive()) {
					System.out.print(player.getSpecialChar() + " ");
				}
			}
			System.out.println();
		}

		printLine();
	}

	/**
	 * 
	 * @param players
	 */
	public static void printPlayers() {
		printLine();
		System.out.printf("%-15s %-6s %-10s %-5s \n", "Player Name", "CFV", "Position", "Char");
		for (Player player : PlayGame.players) {
			if (player.isAlive()) {
				player.printDetails();
			}
		}
	}

	/**
	 * 
	 * @param players
	 * @return
	 */
	public static boolean gameStillAlive() {
		int alivePlayers = 0;
		for (Player player : PlayGame.players) {
			if (player.getCFV() <= 0) {
				return false;
			}
			if (player.isAlive()) {
				alivePlayers++;
			}
		}
		return (alivePlayers > 1);
	}

	/**
	 * 
	 * @param players
	 * @return
	 */
	public static Player returnWinner() {

		Player winner = new Player();
		for (Player player : PlayGame.players) {
			if (player.getCFV() <= 0) {
				winner = player;
			}
			if (player.isAlive()) {
				winner = player;
			}
		}
		return winner;
	}

	/**
	 * 
	 * @param player
	 * @param zone
	 * @return
	 */
	public static boolean doesCurrentPlayerOwnZone(Player player, Zone zone) {

		ArrayList<Area> zoneAreas = new ArrayList<>();

		for (Tile tile : PlayGame.board) {
			if (tile instanceof Area) {
				Area area = (Area) tile;
				if (area.getZone() == zone) {
					zoneAreas.add(area);
				}
			}
		}

		for (Area area : zoneAreas) {
			if (area.getCurrentOwner() != player) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param boardArea
	 */
	public static void auction(Area boardArea) {
		ArrayList<Player> playersInAuction = new ArrayList<>();
		Collections.addAll(playersInAuction, PlayGame.players);

		System.out.println("Starting auction...");

		int currentPrice = AUCTION_START_PRICE;
		int priceIncrements = 25;

		while (playersInAuction.size() > 1) {

			for (int loop = 0; loop < playersInAuction.size(); loop++) {

				Player currentBidder = playersInAuction.get(loop);

				System.out.println(currentBidder.getName() + " would you like to bid " + currentPrice + " for "
						+ boardArea.getName() + " (Y/N)");

				if (PlayGame.sc.nextLine().equalsIgnoreCase("Y")) {
					currentPrice += priceIncrements;
				} else {
					playersInAuction.remove(loop);

					if (playersInAuction.size() == 1 && currentPrice != AUCTION_START_PRICE) {
						break;
					}
					loop--;
				}
			}

		}

		if (playersInAuction.size() > 0) {
			Player winner = playersInAuction.get(0);
			currentPrice -= priceIncrements;

			System.out.println(winner.getName() + " bought " + boardArea.getName() + " for " + currentPrice);
			boardArea.setCurrentOwner(winner);
			winner.addCFV(currentPrice);

		} else {
			System.out.println("Tile unsold");
		}

	}

	public static void transferAssetsFromDeadPlayer(Player currentPlayer, Player ownerOfArea) {

		for (Tile tile : PlayGame.board) {
			if (tile instanceof Area) {
				Area area = (Area) tile;
				if (area.getCurrentOwner() == currentPlayer) {
					System.out.printf("Area %s transferred from %s to %s", area.getName(), currentPlayer.getName(),
							ownerOfArea.getName());
					area.setCurrentOwner(ownerOfArea);
				}
			}
		}

	}

	public static void sendPlayerToPrivateIsland(Player currentPlayer) {
		System.out.println("Penalty CFV of " + PlayGame.PRIVATE_ISLAND_CFV_PENALTY + " applied");
		currentPlayer.setCurrentBoardPosition(PlayGame.PRIVATE_ISLAND_BOARD_INDEX);
		currentPlayer.addCFV(PlayGame.PRIVATE_ISLAND_CFV_PENALTY);

		// TODO need to add what happens if this bankrupts player - below code needs tested

		if (!currentPlayer.isAlive()) {

			for (Tile tile : PlayGame.board) {
				if (tile instanceof Area) {
					Area area = (Area) tile;
					if (area.getCurrentOwner() == currentPlayer) {
						area.setCurrentOwner(null);
					}
				}
			}
		}
	}

	public static void printLine() {
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");
	}

}
