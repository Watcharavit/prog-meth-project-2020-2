package entity.base;

import entity.Player;
import game.GameSingleton;

public abstract class Equipment extends PhantomEntity implements Updatable {
	private double cooldown = 0;
	public final Player user;
	public Equipment(Player user) {
		this.user = user;
	}
	public abstract void useEquipment();
	public void tryUseEquipment() {
		if (cooldown <= 0) useEquipment();
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
