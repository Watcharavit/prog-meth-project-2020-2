package entity;

import entity.base.Collidable;
import logic.Direction;
import logic.Sprite;

public class Player extends Character implements Collidable {
	private Equipment equipment; // ?? จะเป็นequipment หรือ hasMitt / hasBombKicker
	private int bombNumber;
	private int bombRadius;

	public Player() {
		this.equipment = null;
		this.isAlive = true;
		this.bombNumber = 1;
		this.bombRadius = 1;

	}

	@Override
	public void collide(Character otherCharacter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean move(Direction dir) {
		double targetx = x;
		double targety = y;

		this.direction = dir; // Update move position

		// one player ยังไม่ได้เพิ่ม player2,3
		switch (dir) {
		case LEFT:
			targetx -= 1;
			break;
		case UP:
			targety -= 1;
			break;
		case RIGHT:
			targetx += 1;
			break;
		case DOWN:
			targety += 1;
			break;
		default:
			break;

		}
		// if character can walk return true, else return false

	}

	/*
	 * 2 player ใช้ได้ แต่ ถ้ามี3player น่าจะต้องเขียนในเกม public void
	 * equip(Equipment BombKicker, Player leastScorePlayer, Equipment mitt) { //
	 * method to equip bombkicker for highest score player and mitt for least score
	 * player setEquipment(BombKicker); leastScorePlayer.setEquipment(mitt); }
	 */

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public int getBombNumber() {
		return bombNumber;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
	}

	public int getBombRadius() {
		return bombRadius;
	}

	public void setBombRadius(int bombRadius) {
		this.bombRadius = bombRadius;
	}

}
