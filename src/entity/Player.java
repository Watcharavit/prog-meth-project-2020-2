package entity;

import java.util.Map;
import java.util.Set;

import application.GameSingleton;
import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Equipment;
import entity.base.Updatable;
import gui.Sprite;
import javafx.scene.input.KeyCode;
import logic.Direction;
import logic.PlayerControl;

public class Player extends Being implements Collidable,Updatable {
	private Equipment equipment; // this is good already; hasMitt/hasBombKicker makes for worse abstraction
	private int bombNumber;
	private int bombRadius;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;

	public Player(String name, double posX, double posY, Map<PlayerControl, KeyCode> keyMap) {
		super(posX, posY);
		this.equipment = null;
		this.bombNumber = 1;
		this.bombRadius = 1;
		this.name = name;
		this.keyMap = keyMap;
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	private static final Sprite sprite = new Sprite(4);
	@Override
	public Sprite getSprite() {
		return sprite;
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

	@Override
	public void update(long frameTimeNano) {
		Set<KeyCode> activeKeys = GameSingleton.getActiveKeys();
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_LEFT))) {
			super.setFacing(Direction.LEFT);
			super.move(-0.1, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_RIGHT))) {
			super.setFacing(Direction.RIGHT);
			super.move(0.1, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_DOWN))) {
			super.setFacing(Direction.DOWN);
			super.move(0, 0.1);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_UP))) {
			super.setFacing(Direction.UP);
			super.move(0, -0.1);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.PLACE_BOMB))) {
			// TODO
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.USE_EQUIPMENT))) {
			// TODO
		}
		
	}

}
