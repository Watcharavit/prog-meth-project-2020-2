package entity.livings;

import entity.Being;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;

public class MonsterWallThorn extends LivingBeing implements Updatable, Collidable {
	private static final Sprite sprite = new Sprite(2);
	public static final double SIZE = 1;

	public MonsterWallThorn(Tile spawnTile) {
		super(spawnTile, SIZE);

	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void collide(Being otherCharacter) {
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		} else if (otherCharacter instanceof MonsterGhost) {
			((MonsterGhost) otherCharacter).die();
		} else {
			// move wallMon to the opposite side
		}
	}

	private void move() {

	}

}
