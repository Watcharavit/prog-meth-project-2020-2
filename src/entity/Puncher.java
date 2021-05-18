package entity;

import entity.base.Equipment;
import game.GameSingleton;
import graphics.Sprite;
import logic.Direction;

public class Puncher extends Equipment {

	@Override
	public void useEquipment(Player user) {
		super.setCooldown(30.0);
		Direction facing = user.getFacing();
		double x = user.getX() + (double) facing.getDeltaX() / 2;
		double y = user.getY() + (double) facing.getDeltaY() / 2;
		Punch punch = new Punch(x, y, facing, user);
		GameSingleton.addBeing(punch);
	}

}