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
	public static final double SIZE = 0.6;
	private Equipment equipment; // this is good already; hasMitt/hasBombKicker makes for worse abstraction
	private int bombNumber;
	private int bombRadius;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;

	public Player(String name, double posX, double posY, Map<PlayerControl, KeyCode> keyMap) {
		super(posX, posY, SIZE);
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

	private static final Sprite sprite = new Sprite(5);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	/*
	 * 2 player Ã Â¹Æ’Ã Â¸Å Ã Â¹â€°Ã Â¹â€žÃ Â¸â€�Ã Â¹â€° Ã Â¹ï¿½Ã Â¸â€¢Ã Â¹Ë† Ã Â¸â€“Ã Â¹â€°Ã Â¸Â²Ã Â¸Â¡Ã Â¸Âµ3player Ã Â¸â„¢Ã Â¹Ë†Ã Â¸Â²Ã Â¸Ë†Ã Â¸Â°Ã Â¸â€¢Ã Â¹â€°Ã Â¸Â­Ã Â¸â€¡Ã Â¹â‚¬Ã Â¸â€šÃ Â¸ÂµÃ Â¸Â¢Ã Â¸â„¢Ã Â¹Æ’Ã Â¸â„¢Ã Â¹â‚¬Ã Â¸ï¿½Ã Â¸Â¡ public void
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
	public void update(double frameTimeRatio) {
		Set<KeyCode> activeKeys = GameSingleton.getActiveKeys();
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_LEFT))) {
			super.setFacing(Direction.LEFT);
			GameSingleton.moveBeing(this, -0.1 * frameTimeRatio, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_RIGHT))) {
			super.setFacing(Direction.RIGHT);
			GameSingleton.moveBeing(this, 0.1 * frameTimeRatio, 0);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_DOWN))) {
			super.setFacing(Direction.DOWN);
			GameSingleton.moveBeing(this, 0, 0.1 * frameTimeRatio);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.MOVE_UP))) {
			super.setFacing(Direction.UP);
			GameSingleton.moveBeing(this, 0, -0.1 * frameTimeRatio);
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.PLACE_BOMB))) {
			this.placeBomb();
		}
		if (activeKeys.contains(keyMap.get(PlayerControl.USE_EQUIPMENT))) {
			// TODO
		}
	}
	
	private void placeBomb() {
		if (GameSingleton.getTileObject((int)super.x, (int)super.y) instanceof Floor) {
			Bomb bomb = new Bomb(this);
			GameSingleton.setTileObject((int)super.x, (int)super.y, bomb);
		}
	}
}
