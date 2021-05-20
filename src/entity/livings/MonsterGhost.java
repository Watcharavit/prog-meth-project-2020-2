package entity.livings;

import entity.Being;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;

public class MonsterGhost extends LivingBeing implements Collidable, Updatable {
	private static final Sprite sprite = new Sprite(8);
	public static final double SIZE = 1;

	public MonsterGhost(Tile spawnTile) {
		super(spawnTile, SIZE);
		// TODO Auto-generated constructor stub
	}

	private void move() {
		// move ghost to the enable direction
	}

	@Override
	public void update(double ticksPassed) {

	}

	@Override
	public void collide(Being otherCharacter) {
		// Ghost win player
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		} else if (otherCharacter instanceof MonsterGhost) {
			((MonsterGhost) otherCharacter).move();
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
	protected double getSpawnCooldown() {
		return 60.0;
	}

	@Override
	protected void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
