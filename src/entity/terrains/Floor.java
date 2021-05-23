
package entity.terrains;

import entity.Being;
import entity.StillObject;
import interfaces.Bombable;
import interfaces.Passable;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * Floor.
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
	 * When bombed, floor is still floor.
	 * 
	 * @return this same instance.
	 */
	@Override
	public StillObject getAfterBombed() {
		return this;
	}

}