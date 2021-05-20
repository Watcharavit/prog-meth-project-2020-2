package entity.bomb;

import entity.Being;
import entity.StillObject;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Bombable;
import interfaces.Passable;
import interfaces.Updatable;
import logic.Direction;

public class BombFlame extends StillObject implements Updatable, Passable, Bombable {
	private final Player placer;
	private double remainingTicks;
	private final Bombable bombedObject;
	private final Sprite sprite;

	public BombFlame(Player placer, double lifetime, Bombable bombedObject, Sprite sprite) {
		this.placer = placer;
		this.remainingTicks = lifetime;
		this.bombedObject = bombedObject;
		this.sprite = sprite;
	}

	@Override
	public void pass(Being character) {
		if (character instanceof LivingBeing) {
			// player respawn
			((LivingBeing) character).die();
		}

	}

	protected static final Sprite centerSprite = new Sprite(13);
	protected static final Sprite topSprite = new Sprite(18);
	protected static final Sprite bottomSprite = new Sprite(12);
	protected static final Sprite leftSprite = new Sprite(14);
	protected static final Sprite rightSprite = new Sprite(15);
	protected static final Sprite horizontalSprite = new Sprite(16);
	protected static final Sprite verticalSprite = new Sprite(17);
	
	protected static Sprite getSpriteFor(Direction direction, boolean end) {
		if (end) {
			switch (direction) {
			case UP:
				return topSprite;
			case DOWN:
				return bottomSprite;
			case LEFT:
				return leftSprite;
			case RIGHT:
				return rightSprite;
			}
		}
		else {
			switch (direction) {
			case UP:
				return verticalSprite;
			case DOWN:
				return verticalSprite;
			case LEFT:
				return horizontalSprite;
			case RIGHT:
				return horizontalSprite;
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
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		if (this.remainingTicks <= 0) {
			GameSingleton.setTileObject(x, y, bombedObject.getAfterBombed());
		}

	}

	@Override
	public StillObject getAfterBombed() {
		return bombedObject.getAfterBombed();
	}

}
