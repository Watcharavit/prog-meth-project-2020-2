package entity;

import entity.base.Entity;
import entity.base.Pushable;
import entity.base.StillObject;
import entity.base.Updatable;
import gui.Sprite;

public class Bomb extends StillObject implements Pushable, Updatable {

	@Override
	public void push(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double frameTimeRatio) {
		// TODO Auto-generated method stub

	}

	private static final Sprite sprite = new Sprite(5);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
