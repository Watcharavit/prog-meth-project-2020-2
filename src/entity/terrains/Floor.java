
package entity.terrains;

import entity.Being;
import entity.StillObject;
import interfaces.Bombable;
import interfaces.Passable;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * 
 *
 */
public class Floor extends StillObject implements Passable, Bombable {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.FLOOR;
	}

	@Override
	public void pass(Being character) {

	}

	/**
	 * {@link interfaces.Bombable}
	 * @return Floor
	 */
	@Override
	public StillObject getAfterBombed() {
		return this;
	}

}