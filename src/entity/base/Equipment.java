package entity.base;

import entity.Player;

public abstract class Equipment implements Updatable {
	private double cooldown = 0;
	public abstract void useEquipment(Player user);
	public void tryUseEquipment(Player user) {
		if (cooldown <= 0) useEquipment(user);
	}
	@Override
	public void update(double ticksPassed) {
		if (cooldown > 0) {
			cooldown -= ticksPassed;
		}
	}
	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}
}
