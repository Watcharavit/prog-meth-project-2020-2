package entity.equipments;

import entity.livings.Player;
import entity.projectiles.ProjectilePunch;
import game.GameSingleton;
import logic.Direction;

/**
 * An equipment that let its user punch other beings.
 * 
 * @author user
 *
 */
public class EquipmentPuncher extends Equipment {

	/**
	 * Constructor: call super(...).
	 * 
	 * @param user Player who uses this equipment.
	 */
	public EquipmentPuncher(Player user) {
		super(user);
	}

	/**
	 * Execute the punch process. Set the cooldown with
	 * {@link Equipment#setCooldown(double)}. Spawn a
	 * {@link entity.projectiles.ProjectilePunch}.
	 */
	@Override
	protected void useEquipment() {
		super.setCooldown(30.0);
		Direction facing = user.getFacing();
		double x = user.getX() + (double) facing.getDeltaX() / 2;
		double y = user.getY() + (double) facing.getDeltaY() / 2;
		ProjectilePunch punch = new ProjectilePunch(x, y, facing, user);
		GameSingleton.addBeing(punch);
	}

}
