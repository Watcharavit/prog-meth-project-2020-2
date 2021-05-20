package entity.bomb;

import entity.Being;
import entity.StillObject;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import graphics.SpritesLibrary;
import interfaces.Bombable;
import interfaces.Collidable;
import interfaces.Passable;
import interfaces.Updatable;
import logic.Direction;

public class BombFlame extends Being implements Updatable, Collidable {
	private static final double SIZE = 1.0;
	private final Player placer;
	private double remainingTicks;
	private final Bombable bombedObject;
	private final Sprite sprite;

	public BombFlame(int x, int y, Player placer, double lifetime, Bombable bombedObject, Sprite sprite) {
		super(x + 0.5, y + 0.5, SIZE, Direction.random());
		this.placer = placer;
		this.remainingTicks = lifetime;
		this.bombedObject = bombedObject;
		this.sprite = sprite;
	}

	@Override
	public void collide(Being character) {
		if (character instanceof LivingBeing) {
			((LivingBeing) character).die();
		}
	}

	protected static Sprite getSpriteFor(Direction direction, boolean end) {
		if (end) {
			switch (direction) {
			case UP:
				return SpritesLibrary.BLAST_TOP;
			case DOWN:
				return SpritesLibrary.BLAST_BOTTOM;
			case LEFT:
				return SpritesLibrary.BLAST_LEFT;
			case RIGHT:
				return SpritesLibrary.BLAST_RIGHT;
			}
		} else {
			switch (direction) {
			case UP:
				return SpritesLibrary.BLAST_VERTICAL;
			case DOWN:
				return SpritesLibrary.BLAST_VERTICAL;
			case LEFT:
				return SpritesLibrary.BLAST_HORIZONTAL;
			case RIGHT:
				return SpritesLibrary.BLAST_HORIZONTAL;
			}
		}

		// Unreachable
		return null;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		int x = (int) super.getX();
		int y = (int) super.getY();
		if (this.remainingTicks <= 0) {
			GameSingleton.removeBeing(this);
			GameSingleton.setTileObject(x, y, bombedObject.getAfterBombed());
		}
	}
}
