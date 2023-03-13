/**
 * 
 */
package gameobjects;

/**
 * @author steve
 *
 */
public class SpecialTile extends Tile {

	

	/**
	 * 
	 * @param name
	 */
	public SpecialTile(String name) {
		super(name);
	}


	@Override
	public void printDetails() {
		System.out.printf("%-25s %-8s %-8s %-8s %-8s %-28s %-10s", getName(), "-", "-", "-", "-", "-", "-");
	}

}
