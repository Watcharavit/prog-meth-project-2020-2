package entity.livings;

import entity.Being;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;

public class MonsterGhost extends LivingBeing implements Collidable, Updatable {
	private static final Sprite Normalsprite = new Sprite(8);
	private final static Sprite DyingSprite = new Sprite(5);
	private final static Sprite EMPTYSPRITE = new Sprite(7);
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
		if (super.isDead())
			return EMPTYSPRITE;
		else
			return Normalsprite;
	}

	@Override
	protected double getSpawnCooldown() {
		return 70.0;
	}

	@Override
	protected void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
