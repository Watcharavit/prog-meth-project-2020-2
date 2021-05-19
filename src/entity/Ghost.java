package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.Tile;
import graphics.Sprite;

public class Ghost extends LivingBeing implements Collidable, Updatable {
	private static final Sprite sprite = new Sprite(8);
	public static final double SIZE = 1;

	public Ghost(Tile spawnTile) {
		super(spawnTile, SIZE);
		// TODO Auto-generated constructor stub
	}
	
	private void move() {
		//move ghost to the enable direction
	}

	@Override
	public void update(double ticksPassed) {
	

	}

	@Override
	public void collide(Being otherCharacter) {
		// Ghost win player
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		}
		// Ghost lose WallThorn
		// implement in Wall thorn class
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public double getSpawnCooldown() {
		return 60.0;
	}

}
