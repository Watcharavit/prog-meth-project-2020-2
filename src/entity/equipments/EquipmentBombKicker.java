package entity.equipments;

import entity.StillObject;
import entity.bomb.BombObject;
import entity.livings.Player;
import entity.projectiles.ProjectileRollingBomb;
import game.GameSingleton;
import logic.Direction;

/**
 * An equipment that let its user kick bombs.
 *
 */
public class EquipmentBombKicker extends Equipment {

	/**
	 * Constructor: calls super(...).
	 * 
	 * @param user Player who uses this equipment.
	 */
	public EquipmentBombKicker(Player user) {
		super(user);
	}

	/**
	 * Execute the kicking process. Make sure the user is facing a bomb. Lock and
	 * remove that bomb using {@link entity.bomb.BombObject#prepareForExplosion()}.
	 * Restart the equipments cooldown with {@link Equipment#setCooldown(double)}.
	 * Spawn a {@link entity.projectiles.ProjectileRollingBomb}.
	 */
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
				GameSingleton.addBeing(
						new ProjectileRollingBomb(targetTileX + 0.5, targetTileY + 0.5, facing, user, bomb.radius));
			}
		}
	}

}
