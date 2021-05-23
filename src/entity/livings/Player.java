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
 * A player in the game.
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
	 * Update on every frame: If player is not dead, poll pressed keys
	 * ({@link GameSingleton#getActiveKeys()} for control keys and move, place bomb,
	 * or use equipment accordingly.
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
	 * Place a bomb if possible and play bomb placed sound.
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
	 * Set the equipment for this Player.
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
	 * Spawn cooldown for player is 1 second.
	 * 
	 * @return always return 60.0
	 */
	@Override
	protected double getSpawnCooldown() {
		return 60.0;
	}

	/**
	 * Penalty when Player is death: reduce {@link #bombRadius} and
	 * {@link #bombsNumber}.
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
	 * Nothing to do here.
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
	 * To be called when a bomb goes off: reduce {@link #bombsPlacedNumber} by 1.
	 */
	public void returnBomb() {
		bombsPlacedNumber -= 1;
		ui.refreshBombsNumber();
	}

	/**
	 * To be called upon picking {@link entity.drops.DropBombQuantityUpgrade}:
	 * Increase bomb available number and show on Player's board attribute.
	 */
	public void incrementBombsNumber() {
		bombsNumber += 1;
		ui.refreshBombsNumber();
	}

	/**
	 * To be called upon picking {@link entity.drops.DropBombFlameUpgrade}: Increase
	 * bomb radius and show on Player's board attribute.
	 */
	public void incrementBombRadius() {
		bombRadius += 1;
		ui.refreshBombRadius();
	}

	/**
	 * Increase Player's score and show on Player's board attribute.
	 * 
	 * @param ticks The amount of time to increase.
	 */
	public void incrementKingTime(double ticks) {
		kingTime += ticks;
		ui.refreshKingTime();
	}

	/**
	 * Getter for {@link #kingTime} (which is the score).
	 * 
	 * @return Player's kingTime.
	 */
	public double getKingTime() {
		return kingTime;
	}

	/**
	 * Getter for {@link #name}.
	 * 
	 * @return Player's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * The left hand side UI that display the player's stats.
	 *
	 */
	class PlayerUi extends VBox {
		/**
		 * The player object.
		 */
		private final Player player;
		/**
		 * Label for a stat.
		 */
		private final Label nameLabel, bombsNumberLabel, bombRadiusLabel, kingTimeLabel;
		/**
		 * 1-decimal-point value for kingTime, so we only need to update
		 * {@link #kingTimeLabel} every 1/10 second or so rather than every 1/60.
		 */
		private double kingTimeDebounced = -1;

		/**
		 * Create the UI for displaying the player's attributes. Shows
		 * {@link Player#name}, {@link Player#bombRadius}, {@link Player#bombsNumber},
		 * and {@link Player#kingTime}.
		 * 
		 * @param root   The JavaFX Pane to build the UI on.
		 * @param player The player object itself.
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
		 * Refresh bomb available number and show on Player's board attribute.
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
		 * Refresh bomb radius and show on Player's board attribute.
		 */
		protected void refreshBombRadius() {
			int radius = player.bombRadius;
			Formatter f = new Formatter();
			String r = f.format("Bomb Radius: %2d", radius).toString();
			f.close();
			this.bombRadiusLabel.setText(r);
		}

		/**
		 * Refresh Player's score and show on Player's board attribute.
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