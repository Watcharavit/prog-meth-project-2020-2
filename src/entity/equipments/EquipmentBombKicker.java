package entity.equipments;

import entity.StillObject;
import entity.bomb.BombObject;
import entity.livings.Player;
import entity.projectiles.ProjectileRollingBomb;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import logic.Direction;

public class EquipmentBombKicker extends Equipment {

	public EquipmentBombKicker(Player user) {
		super(user);
	}

	@Override
	protected void useEquipment() {
		Direction facing = user.getFacing();
		int sourceTileX = (int) user.getX();
		int sourceTileY = (int) user.getY();
		int targetTileX = sourceTileX + facing.getDeltaX();
		int targetTileY = sourceTileY + facing.getDeltaY();
		StillObject actedObject = GameSingleton.getTileObject(targetTileX, targetTileY);
		if (actedObject instanceof BombObject) {
			super.setCooldown(30.0);
			BombObject bomb = (BombObject) actedObject;
			if (bomb.prepareForExplosion()) {
				GameSingleton.setTileObject(targetTileX, targetTileY, new Floor());
				GameSingleton.addBeing(new ProjectileRollingBomb(targetTileX + 0.5, targetTileY + 0.5, facing, user, bomb.radius));
			}
		}
	}

}
