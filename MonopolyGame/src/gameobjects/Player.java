/**
 * 
 */
package gameobjects;

import game.PlayGame;

/**
 * @author steve
 *
 */
public class Player extends GameItem implements IGame {

	private char specialChar;
	private int CFV;
	private int currentBoardPosition=0;;
	private boolean isAlive = true;
	
	public Player() {
	}

	/**
	 * @param cFV
	 * @param currentBoardPosition
	 * @param isAlive
	 */
	public Player(String name, char specialChar, int cFV, int currentBoardPosition) {
		super(name);
		this.specialChar = specialChar;
		this.CFV = cFV;
		this.currentBoardPosition = currentBoardPosition;
	}

	/**
	 * @return the cFV
	 */
	public int getCFV() {
		return CFV;
	}

	/**
	 * @param cFV the cFV to set
	 */
	public void setCFV(int cFV) {
		CFV = cFV;
	}

	/**
	 * @return the currentBoardPosition
	 */
	public int getCurrentBoardPosition() {
		return currentBoardPosition;
	}

	/**
	 * @param currentBoardPosition the currentBoardPosition to set
	 */
	public void setCurrentBoardPosition(int currentBoardPosition) {
		this.currentBoardPosition = currentBoardPosition;
	}
	
	/**
	 * @param currentBoardPosition the currentBoardPosition to set
	 */
	public void updateCurrentBoardPosition(int diceRoll) {
		this.currentBoardPosition = (diceRoll+currentBoardPosition)%14;
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public void printDetails() {
		System.out.printf("%-15s %-6d %-10d %-5c \n", getName(), CFV, currentBoardPosition, specialChar);
	}

	/**
	 * @param add cFV to existing CFV value
	 */
	public void addCFV(int cFV) {
		this.CFV += cFV;
		if (this.CFV>PlayGame.MAX_ALLOWABLE_CFV) {
			isAlive = false;
		}
	}

	/**
	 * @return the specialChar
	 */
	public char getSpecialChar() {
		return specialChar;
	}

	/**
	 * @param specialChar the specialChar to set
	 */
	public void setSpecialChar(char specialChar) {
		this.specialChar = specialChar;
	}

}
