package entity;

import java.util.Map;
import java.util.Set;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Equipment;
import entity.base.Updatable;
import game.GameSingleton;
import graphics.Sprite;
import javafx.scene.input.KeyCode;
import logic.Direction;
import logic.PlayerControl;

public class Player extends Being implements Collidable, Updatable {
	public static final double SIZE = 0.6;
	private Equipment equipment; // this is good already; hasMitt/hasBombKicker makes for worse abstraction
	private int bombNumber;
	private int bombPlaceNumber;
	private int bombRadius;
	private int kingTime;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;

	public Player(String name, double posX, double posY, Map<PlayerControl, KeyCode> keyMap) {
		super(posX, posY, SIZE);
		this.equipment = null;
		this.bombNumber = 1;
		this.bombRadius = 1;
		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = 0;
		this.bombPlaceNumber = 0;
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	private static final Sprite sprite = new Sprite(5);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	/*
	 * equip(Equipment BombKicker, Player leastScorePlayer, Equipment mitt) { //
	 * method to equip bombkicker for highest score player and mitt for least score
	 * player setEquipment(BombKicker); leastScorePlayer.setEquipment(mitt); }
	 */

	@Override
	public void update(double ticksPassed) {
		Set<KeyCode> activeKeys = GameSingleton.getActiveKeys();
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_LEFT))) {
			super.setFacing(Direction.LEFT);
			GameSingleton.moveBeing(this, -0.1 * ticksPassed, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_RIGHT))) {
			super.setFacing(Direction.RIGHT);
			GameSingleton.moveBeing(this, 0.1 * ticksPassed, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_DOWN))) {
			super.setFacing(Direction.DOWN);
			GameSingleton.moveBeing(this, 0, 0.1 * ticksPassed);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_UP))) {
			super.setFacing(Direction.UP);
			GameSingleton.moveBeing(this, 0, -0.1 * ticksPassed);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.PLACE_BOMB))) {
			this.placeBomb();
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.USE_EQUIPMENT))) {
			// TODO
		}
	}

	private void placeBomb() {
		if (GameSingleton.getTileObject((int) super.x, (int) super.y) instanceof Floor) {
			if(this.bombPlaceNumber<this.bombNumber) {
				Bomb bomb = new Bomb(this);
				GameSingleton.setTileObject((int) super.x, (int) super.y, bomb);
				this.bombPlaceNumber++;
			}
		}
	}

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

	public int getKingTime() {
		return kingTime;
	}

	public void setKingTime(int kingTime) {
		this.kingTime = kingTime;
	}

	public int getBombPlaceNumber() {
		return bombPlaceNumber;
	}

	public void setBombPlaceNumber(int bombPlaceNumber) {
		this.bombPlaceNumber = bombPlaceNumber;
	}
	

}
