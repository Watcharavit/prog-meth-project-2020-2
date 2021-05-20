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

public class BombFlame extends StillObject implements Updatable, Passable, Bombable {
	private final Player placer;
	private double remainingTicks;
	private final Bombable bombedObject;

	public BombFlame(Player placer, double lifetime, Bombable bombedObject) {
		this.placer = placer;
		this.remainingTicks = lifetime;
		this.bombedObject = bombedObject;
	}

	@Override
	public void pass(Being character) {
		if (character instanceof LivingBeing) {
			// player respawn
			((LivingBeing) character).die();
		}

	}

	private static final Sprite sprite = new Sprite(1);

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
