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
	private static final double SPEED = 0.1;
	private Equipment equipment;
	private int bombsNumber;
	private int bombsPlacedNumber;
	private int bombRadius;
	private double kingTime;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;
	private int playerNumber;

	public Player(String name, double posX, double posY, Map<PlayerControl, KeyCode> keyMap, int playerNumber) {
		super(posX, posY, SIZE);
		this.equipment = new Puncher();
		this.bombsNumber = 1;
		this.bombRadius = 1;
		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = 0;
		this.bombsPlacedNumber = 0;
		this.playerNumber = playerNumber;
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	private static final Sprite spriteFirstPlayer = new Sprite(5);
	private static final Sprite spriteSecondPlayer = new Sprite(6);

	@Override
	public Sprite getSprite() {
		if(this.playerNumber==1) {
			return spriteFirstPlayer;
		}else {
			return spriteSecondPlayer;
		}
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
			GameSingleton.moveBeing(this, -SPEED * ticksPassed, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_RIGHT))) {
			super.setFacing(Direction.RIGHT);
			GameSingleton.moveBeing(this, SPEED * ticksPassed, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_DOWN))) {
			super.setFacing(Direction.DOWN);
			GameSingleton.moveBeing(this, 0, SPEED * ticksPassed);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_UP))) {
			super.setFacing(Direction.UP);
			GameSingleton.moveBeing(this, 0, -SPEED * ticksPassed);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.PLACE_BOMB))) {
			this.placeBomb();
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.USE_EQUIPMENT))) {
			if (this.equipment != null) {
				this.equipment.tryUseEquipment(this);
			}
		}
		
		if (this.equipment != null) {
			this.equipment.update(ticksPassed);
		}
	}

	private void placeBomb() {
		if (GameSingleton.getTileObject((int) super.x, (int) super.y) instanceof Floor) {
			if(this.bombsPlacedNumber<this.bombsNumber) {
				Bomb bomb = new Bomb(this, this.bombRadius);
				GameSingleton.setTileObject((int) super.x, (int) super.y, bomb);
				this.bombsPlacedNumber++;
			}
		}
	}
	public void returnBomb() {
		this.bombsPlacedNumber--;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public void incrementBombsNumber() {
		this.bombsNumber++;
	}
	public void incrementBombRadius() {
		this.bombRadius++;
	}

	public double getKingTime() {
		return kingTime;
	}

	public void setKingTime(double d) {
		this.kingTime = d;
	}
	

}
