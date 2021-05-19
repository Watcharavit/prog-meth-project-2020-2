package entity.equipments;

import entity.livings.Player;
import entity.projectiles.ProjectilePunch;
import game.GameSingleton;
import graphics.Sprite;
import logic.Direction;

public class EquipmentPuncher extends Equipment {

	public EquipmentPuncher(Player user) {
		super(user);
	}

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
