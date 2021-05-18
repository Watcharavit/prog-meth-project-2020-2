package entity;

import entity.base.Equipment;
import entity.base.StillObject;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import logic.Direction;

public class BombKicker extends Equipment {

	@Override
	public void useEquipment(Player user) {
		Direction facing = user.getFacing();
		int sourceTileX = (int) user.getX();
		int sourceTileY = (int) user.getY();
		int targetTileX = sourceTileX + facing.getDeltaX();
		int targetTileY = sourceTileY + facing.getDeltaY();
		StillObject actedObject = GameSingleton.getTileObject(targetTileX, targetTileY);
		if (actedObject instanceof Bomb) {
			super.setCooldown(30.0);
			Bomb bomb = (Bomb) actedObject;
			GameSingleton.setTileObject(targetTileX, targetTileY, new Floor());
			bomb.placer.returnBomb();
			GameSingleton.addBeing(new RollingBomb(targetTileX + 0.5, targetTileY + 0.5, facing, user, bomb.radius));
		}
	}

}
