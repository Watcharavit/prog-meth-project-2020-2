package entity.livings;

import java.util.Formatter;
import java.util.Map;
import java.util.Set;

import entity.StillObject;
import entity.bomb.BombObject;
import entity.equipments.Equipment;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import interfaces.Updatable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.Direction;
import logic.PlayerControl;
import resources.SoundsLibrary;
import resources.Sprite;

/**
 * This class provides all information about each player.
 *
 */
public class Player extends LivingBeing implements Updatable {

	/**
	 * Size of Player.
	 */
	public static final double SIZE = 0.6;

	/**
	 * Speed of Player.
	 */
	private static final double SPEED = 0.1;

	/**
	 * Equipment that player is having.
	 */
	private Equipment equipment;

	/**
	 * Attribute of player's bomb.
	 */
	private int bombsNumber, bombsPlacedNumber, bombRadius;

	/**
	 * Player's score.
	 */
	private double kingTime;

	/**
	 * Player's name.
	 */
	private final String name;

	/**
	 * Player's control.
	 */
	private final Map<PlayerControl, KeyCode> keyMap;

	/**
	 * Player's graphic.
	 */
	private final Sprite normalSprite, dyingSprite;

	/**
	 * Player's board attribute.
	 */
	private final PlayerUi ui;

	/**
	 * create Player with specific given name, spawn tile, control, direction and
	 * set all bomb's attribute to 1.
	 * 
	 * @param name         Player's name.
	 * @param keyMap       Player's control.
	 * @param spawnTile    Position that Player spawns.
	 * @param normalSprite Player's graphic.
	 * @param dyingSprite  Player's graphic.
	 * @param uiPane       Player's board attribute.
	 */
	public Player(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile, Sprite normalSprite,
			Sprite dyingSprite, Pane uiPane) {
		super(spawnTile, SIZE, Direction.random());
		this.equipment = null;
		this.bombsNumber = 1;
		this.bombsPlacedNumber = 0;
		this.bombRadius = 1;

		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = 0;
		this.normalSprite = normalSprite;
		this.dyingSprite = dyingSprite;
		this.ui = new PlayerUi(uiPane, this);
	}

	/**
	 * Update every 1/60 second Check if this player is dead or not. If it is then
	 * respawn. Otherwise set the direction it is going and movement it is going to
	 * do. Do the actions it from player (real life).
	 */
	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead())
			return;
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
				this.equipment.tryUseEquipment();
			}
		}
	}

	/**
	 * Place the bomb if it is possible together with playing sound.
	 */
	private void placeBomb() {
		int x = (int) super.getX();
		int y = (int) super.getY();
		StillObject currentObject = GameSingleton.getTileObject(x, y);
		if (currentObject instanceof Floor) {
			if (bombsPlacedNumber < bombsNumber) {
				BombObject bomb = new BombObject(this, bombRadius, (Floor) currentObject);
				GameSingleton.setTileObject(x, y, bomb);
				bombsPlacedNumber += 1;
				ui.refreshBombsNumber();
				SoundsLibrary.BOMB_PLACED.play();
			}
		}
	}

	/**
	 * Set the available equipment for this Player.
	 * 
	 * @param equipment Equipment that player is having.
	 */
	public void setEquipment(Equipment equipment) {
		if (this.equipment != null)
			GameSingleton.removePhantomEntity(this.equipment);
		if (equipment != null)
			GameSingleton.addPhantomEntity(equipment);
		this.equipment = equipment;
	}

	/**
	 * Return spawn cooldown.
	 * 
	 * @return always return 60.0
	 */
	@Override
	protected double getSpawnCooldown() {
		return 60.0;
	}

	/**
	 * Penalty when Player is death.
	 */
	@Override
	public void onDeath() {
		bombRadius = (Math.max(1, bombRadius - 3));
		bombsNumber = (Math.max(1, bombsNumber - 3));
		ui.refreshBombsNumber();
		ui.refreshBombRadius();
		SoundsLibrary.PLAYER_DIE.play();
	}

	/**
	 * Play sound for Player respawn.
	 */
	@Override
	protected void onSpawn() {
		SoundsLibrary.PLAYER_RESPAWN.play();
	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onAlive() {

	}

	/**
	 * @return return Player image.
	 */
	@Override
	protected Sprite getAliveSprite() {
		return normalSprite;
	}

	/**
	 * @return return dying Player image.
	 */
	@Override
	protected Sprite getDyingSprite() {
		return dyingSprite;
	}

	/**
	 * Reduce bomb available number and shows on Player's board attribute.
	 */
	public void returnBomb() {
		bombsPlacedNumber -= 1;
		ui.refreshBombsNumber();
	}

	/**
	 * Increase bomb available number and shows on Player's board attribute.
	 */
	public void incrementBombsNumber() {
		bombsNumber += 1;
		ui.refreshBombsNumber();
	}

	/**
	 * Increase bomb radius and shows on Player's board attribute.
	 */
	public void incrementBombRadius() {
		bombRadius += 1;
		ui.refreshBombRadius();
	}

	/**
	 * Increase Player's score and shows on Player's board attribute.
	 * 
	 * @param ticks Time increase.
	 */
	public void incrementKingTime(double ticks) {
		kingTime += ticks;
		ui.refreshKingTime();
	}

	/**
	 * 
	 * @return Player's score.
	 */
	public double getKingTime() {
		return kingTime;
	}

	/**
	 * 
	 * @return Player's name
	 */
	public String getName() {
		return this.name;
	}

	class PlayerUi extends VBox {
		private final Player player;
		private final Label nameLabel;
		private final Label bombsNumberLabel;
		private final Label bombRadiusLabel;
		private final Label kingTimeLabel;
		private double kingTimeDebounced = -1;

		/**
		 * Create Player's board attribute and remaining time.
		 */
		private PlayerUi(Pane root, Player player) {
			this.player = player;

			root.getChildren().add(this);
			this.nameLabel = new Label(player.name);

			this.bombsNumberLabel = new Label();
			this.bombRadiusLabel = new Label();
			this.kingTimeLabel = new Label();

			this.setPadding(new Insets(24));

			this.getChildren().addAll(nameLabel, bombsNumberLabel, bombRadiusLabel, kingTimeLabel);
			this.setAlignment(Pos.BASELINE_LEFT);

			refreshBombsNumber();
			refreshBombRadius();
			refreshKingTime();
		}

		/**
		 * Refresh bomb available number and shows on Player's board attribute.
		 */
		protected void refreshBombsNumber() {
			int total = player.bombsNumber;
			int used = player.bombsPlacedNumber;
			Formatter f = new Formatter();
			String r = f.format("Bombs: %2d / %2d", total - used, total).toString();
			f.close();
			this.bombsNumberLabel.setText(r);
		}

		/**
		 * Refresh bomb radius and shows on Player's board attribute.
		 */
		protected void refreshBombRadius() {
			int radius = player.bombRadius;
			Formatter f = new Formatter();
			String r = f.format("Bomb Radius: %2d", radius).toString();
			f.close();
			this.bombRadiusLabel.setText(r);
		}

		/**
		 * Refresh Player's score and shows on Player's board attribute.
		 */
		protected void refreshKingTime() {
			double kingTime = player.kingTime;
			double v = Math.floor(kingTime / 6.0);
			if (v != kingTimeDebounced) {
				kingTimeDebounced = v;
				Formatter f = new Formatter();
				String r = f.format("Score: %3.1f", kingTimeDebounced / 10).toString();
				f.close();
				this.kingTimeLabel.setText(r);
			}
		}
	}

}