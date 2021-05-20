
package entity.terrains;

import entity.Being;
import entity.StillObject;
import graphics.Sprite;
import graphics.SpritesLibrary;
import interfaces.Bombable;
import interfaces.Passable;

public class Floor extends StillObject implements Passable, Bombable {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.FLOOR;
	}

	@Override
	public void pass(Being character) {

	}

	@Override
	public StillObject getAfterBombed() {
		return this;
	}

}