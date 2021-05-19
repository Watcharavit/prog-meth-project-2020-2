package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.PhysicalEntity;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.Tile;
import graphics.Sprite;
import logic.Direction;

public class WallThornMonster extends LivingBeing implements Updatable, Collidable {
	private static final Sprite sprite = new Sprite(1);
	public static final double SIZE = 1;

	public WallThornMonster(Tile spawnTile) {
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
		
	}


}
