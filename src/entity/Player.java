package entity;
import java.util.Map;
import java.util.Set;
import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Equipment;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import javafx.scene.input.KeyCode;
import logic.Direction;
import logic.PlayerControl;

public class Player extends Being implements Collidable, Updatable {
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
	private int playerNumber;
	private int spawnX;
	private final Sprite normalSprite, dyingSprite;
	// private boolean isDying; // for red tone player's sprite

	public Player(String name, double posX, double posY, Map<PlayerControl, KeyCode> keyMap, int playerNumber) {
		super(posX, posY, SIZE);
	public Player(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile, Sprite normalSprite, Sprite dyingSprite) {
		super(spawnTile, SIZE);
		this.equipment = new Puncher();
		this.bombsNumber = 1;
		this.bombRadius = 1;
		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = 0;
		this.bombsPlacedNumber = 0;
		this.playerNumber = playerNumber;
		// this.isDying = false;
		this.normalSprite = normalSprite;
		this.dyingSprite = dyingSprite;
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	private static final Sprite spriteFirstPlayer = new Sprite(5);
	private static final Sprite spriteFirstPlayerDying = new Sprite(0);
	private static final Sprite spriteSecondPlayer = new Sprite(6);
	private static final Sprite spriteSecondPlayerDying = new Sprite(1);

	@Override
	public Sprite getSprite() {
		if (this.playerNumber == 1) {
//			if (isDying) {
//				return spriteFirstPlayerDying; // haven't change sprite
//			}
			return spriteFirstPlayer;
		} else {
//			if (isDying) {
//				return spriteSecondPlayerDying;
//			}
			return spriteSecondPlayer;
		}
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
			if (this.bombsPlacedNumber < this.bombsNumber) {
				Bomb bomb = new Bomb(this, this.bombRadius);
				GameSingleton.setTileObject((int) super.x, (int) super.y, bomb);
				this.bombsPlacedNumber++;
			}
		}
	}

	public void respawn() { // move player to starting point

		if (this.spawnX == 2.5) {
			this.setX(2.5);
			this.setY(2.5);
		} else {
			this.setX(GameSingleton.WIDTH - 2.5);
			this.setY(GameSingleton.HEIGHT - 2.5);
		}
		GameSingleton.removeBeing(this);
		System.out.println("444");
		GameSingleton.addBeing(this);
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
	public void incrementKingTime(double ticks) {
		this.kingTime += ticks;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public void recievePenalty() {
		if (this.bombRadius > 3) {
			this.bombRadius -= 3;
		} else {
			this.bombRadius = 1;
		}
		if (this.bombsNumber > 3) {
			this.bombsNumber -= 3;
		} else {
			this.bombsNumber = 1;
		}
	@Override
	public double getSpawnCooldown() {
		return 60.0;
	}

}
  8  src/entity/W