/**
 * 
 */
package gameobjects;

/**
 * @author moneill
 *
 */
public abstract class Tile extends GameItem implements IGame {

	/**
	 * @param name
	 */
	public Tile(String name) {
		super(name);
	}

	@Override
	public abstract void printDetails();

}
