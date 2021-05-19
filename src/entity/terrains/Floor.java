  
package entity.terrains;

import entity.Being;
import entity.StillObject;
import graphics.Sprite;
import interfaces.Bombable;
import interfaces.Passable;

public class Floor extends StillObject implements Passable,Bombable {
	private static final Sprite sprite = new Sprite(7);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void pass(Being character) {
		
	}

}