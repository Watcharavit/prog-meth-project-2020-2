package entity;

import java.util.Map;
import java.util.Set;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.PhysicalEntity;
import entity.base.Equipment;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import javafx.scene.input.KeyCode;
import logic.Direction;
import logic.PlayerControl;

public class Player extends LivingBeing implements Collidable, Updatable {
	public static final double SIZE = 0.6;
	private static final double SPEED = 0.1;
	private Equipment equipment;
	private int bombsNumber;
	private int bombsPlacedNumber;
	private int bombRadius;
	private double kingTime;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;
	private final Sprite normalSprite, dyingSprite;
	// private boolean isDying; // for red tone player's sprite

	public Player(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile, Sprite normalSprite, Sprite dyingSprite) {
		super(spawnTile, SIZE);
		this.equipment = null;
		this.bombsNumber = 1;
		this.bombRadius = 1;
		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = 0;
		this.bombsPlacedNumber = 0;
		this.normalSprite = normalSprite;
		this.dyingSprite = dyingSprite;
		this.setEquipment(new Puncher(this));
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getSprite() {
		if (super.isDead()) return dyingSprite;
		else return normalSprite;
	}

	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead()) return;
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
			System.out.println(this);
			if (this.equipment != null) {
				this.equipment.tryUseEquipment();
			}
		}
	}

	private void placeBomb() {
		if (GameSingleton.getTileObject((int) super.x, (int) super.y) instanceof Floor) {
			if (this.bombsPlacedNumber < this.bombsNumber) {
				Bomb bomb = new Bomb(this, this.bombRadius);
				GameSingleton.setTileObject((int) super.x, (int) super.y, bomb);
				this.bombsPlacedNumber++;
			}
		}
	}

	@Override
	public void die() {
		super.die();
		bombRadius = Math.max(1, bombRadius - 3);
		bombsNumber = Math.max(1,  bombsNumber - 3);
	}

	public void returnBomb() {
		this.bombsPlacedNumber--;
	}

	public void setEquipment(Equipment equipment) {
		if (this.equipment != null) GameSingleton.removePhantomEntity(this.equipment);
		GameSingleton.addPhantomEntity(equipment);
		this.equipment = equipment;
	}

	public void incrementBombsNumber() {
		this.bombsNumber++;
	}

	public void incrementBombRadius() {
		this.bombRadius++;
	}

	public void incrementKingTime(double ticks) {
		this.kingTime += ticks;
	}

	@Override
	public double getSpawnCooldown() {
		return 50.0;
	}

}
