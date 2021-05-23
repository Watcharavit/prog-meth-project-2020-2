package entity.equipments;

import entity.PhantomEntity;
import entity.livings.Player;
import interfaces.Updatable;

/**
 * This class is an equipment player can use.
 */
public abstract class Equipment extends PhantomEntity implements Updatable {

	/**
	 * Cooldown for the equipment.
	 */
	private double cooldown = 0;

	/**
	 * The player carrying this equipment.
	 */
	public final Player user;

	/**
	 * Constructor: save the given player to {@link #user}.
	 * 
	 * @param user Player who uses this equipment.
	 */
	protected Equipment(Player user) {
		this.user = user;
	}

	/**
	 * Ran when the equipment is used.
	 */
	protected abstract void useEquipment();

	/**
	 * Run {@link #useEquipment()} if there is no pending cooldown.
	 */
	public void tryUseEquipment() {
		if (cooldown <= 0)
			useEquipment();
	}

	/**
	 * Reduce cooldown as time passes.
	 * 
	 * @param ticksPassed Amount of time that passed since last frame.
	 */
	@Override
	public void update(double ticksPassed) {
		if (cooldown > 0) {
			cooldown -= ticksPassed;
		}
	}

	/**
	 * Setter for {@link #cooldown}. To be used by subclass when the equipment was
	 * activated.
	 * 
	 * @param cooldown The value to set.
	 */
	protected void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}
}
