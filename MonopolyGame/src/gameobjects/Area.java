package gameobjects;

/**
 * 
 * @author steve
 *
 */
public class Area extends Tile {

	private int costToPurchase;
	private int costToDevelop;
	private int developmentStatus = 1;
	private int costToLandOnBase;
	private Zone zone;
	private Player currentOwner;

	/**
	 * @param name
	 * @param costToDevelop
	 * @param developmentStatus
	 * @param rentBase
	 * @param zone
	 * @param currentOwner
	 */
	public Area(String name, int costToPurchase, int costToLandOnBase, int costToDevelop, Zone zone) {
		super(name);
		this.costToPurchase = costToPurchase;
		this.costToDevelop = costToDevelop;
		this.costToLandOnBase = costToLandOnBase;
		this.zone = zone;
	}

	/**
	 * @return the costToDevelop
	 */
	public int getCostToDevelop() {
		return costToDevelop;
	}

	/**
	 * @param costToDevelop the costToDevelop to set
	 */
	public void setCostToDevelop(int costToDevelop) {
		this.costToDevelop = costToDevelop;
	}

	/**
	 * @return the developmentStatus
	 */
	public int getDevelopmentStatus() {
		return developmentStatus;
	}

	/**
	 * @param developmentStatus the developmentStatus to set
	 */
	public void setDevelopmentStatus(int developmentStatus) {
		this.developmentStatus = developmentStatus;
	}
	
	/**
	 * @param developmentStatus the developmentStatus to set
	 */
	public void incrementDevelopmentStatus() {
		this.developmentStatus++;
	}

	/**
	 * @return the baseRate
	 */
	public int getRentBase() {
		return costToLandOnBase;
	}

	/**
	 * @param baseRate the baseRate to set
	 */
	public void setRentBase(int rentBase) {
		this.costToLandOnBase = rentBase;
	}

	/**
	 * @return the zone
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * @return the currentOwner
	 */
	public Player getCurrentOwner() {
		return currentOwner;
	}

	/**
	 * @param currentOwner the currentOwner to set
	 */
	public void setCurrentOwner(Player currentOwner) {
		this.currentOwner = currentOwner;
	}

	@Override
	public void printDetails() {
		System.out.printf("%-25s %-8d %-8d %-8d %-8d %-28s %-10s", getName(), costToPurchase, costToLandOnBase, costToDevelop, developmentStatus,
				zone, currentOwner != null ? currentOwner.getName() : "N/A");
	}

	public int currentRent() {
		return costToLandOnBase * developmentStatus;
	}

	/**
	 * @return the costToPurchase
	 */
	public int getCostToPurchase() {
		return costToPurchase;
	}

	/**
	 * @param costToPurchase the costToPurchase to set
	 */
	public void setCostToPurchase(int costToPurchase) {
		this.costToPurchase = costToPurchase;
	}

}
