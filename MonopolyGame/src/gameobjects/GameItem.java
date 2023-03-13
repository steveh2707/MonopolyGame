package gameobjects;

/**
 * 
 * @author steve
 *
 */
public abstract class GameItem {

	private String name;

	/**
	 * Default constructor
	 */
	public GameItem() {
	}

	/**
	 * Constructor with args
	 * 
	 * @param name
	 */
	public GameItem(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
