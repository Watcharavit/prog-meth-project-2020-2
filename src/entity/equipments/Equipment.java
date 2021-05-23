package entity.equipments;

import entity.PhantomEntity;
import entity.livings.Player;
import interfaces.Updatable;

/**
 * This class is an equipment player can use.
 */
public abstract class Equipment extends PhantomEntity implements Updatable {

	/**
	 * Cooldown for an equipment.
	 */
	private double cooldown = 0;

	/**
	 * Player who uses this equipment.
	 */
	public final Player user;

	/**
	 * 
	 * @param user Player who uses this equipment.
	 */
	protected Equipment(Player user) {
		this.user = user;
	}

	/**
	 * Override in sub class.
	 */
	protected abstract void useEquipment();

	/**
	 * Use equipment if it is possible.
	 */
	public void tryUseEquipment() {
		if (cooldown <= 0)
			useEquipment();
	}

	/**
	 * Reduce cooldown.
	 */
	@Override
	public void update(double ticksPassed) {
		if (cooldown > 0) {
			cooldown -= ticksPassed;
		}
	}

	protected void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}
}
