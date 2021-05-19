package entity.equipments;

import entity.PhantomEntity;
import entity.livings.Player;
import interfaces.Updatable;

public abstract class Equipment extends PhantomEntity implements Updatable {
	private double cooldown = 0;
	public final Player user;

	protected Equipment(Player user) {
		this.user = user;
	}

	protected abstract void useEquipment();

	public void tryUseEquipment() {
		if (cooldown <= 0)
			useEquipment();
	}

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
