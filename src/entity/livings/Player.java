package entity.livings;

import java.util.Formatter;
import java.util.Map;
import java.util.Set;

import entity.StillObject;
import entity.bomb.BombObject;
import entity.equipments.Equipment;
import entity.equipments.EquipmentBombKicker;
import entity.equipments.EquipmentPuncher;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Updatable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.Direction;
import logic.PlayerControl;

public class Player extends LivingBeing implements Updatable {
	public static final double SIZE = 0.6;
	private static final double SPEED = 0.1;
	private Equipment equipment;
	private int bombsNumber, bombsPlacedNumber, bombRadius;
	private double kingTime;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;
	private final Sprite normalSprite, dyingSprite;
	private final PlayerUi ui;

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
			}
		}
	}

	public void setEquipment(Equipment equipment) {
		if (this.equipment != null)
			GameSingleton.removePhantomEntity(this.equipment);
		if (equipment != null)
			GameSingleton.addPhantomEntity(equipment);
		this.equipment = equipment;
	}

	@Override
	protected double getSpawnCooldown() {
		return 60.0;
	}

	@Override
	public void onDeath() {
		bombRadius = (Math.max(1, bombRadius - 3));
		bombsNumber = (Math.max(1, bombsNumber - 3));
		ui.refreshBombsNumber();
		ui.refreshBombRadius();
	}

	@Override
	protected void onSpawn() {

	}

	@Override
	protected void onAlive() {

	}

	@Override
	protected Sprite getAliveSprite() {
		return normalSprite;
	}

	@Override
	protected Sprite getDyingSprite() {
		return dyingSprite;
	}

	public void returnBomb() {
		bombsPlacedNumber -= 1;
		ui.refreshBombsNumber();
	}

	public void incrementBombsNumber() {
		bombsNumber += 1;
		ui.refreshBombsNumber();
	}

	public void incrementBombRadius() {
		bombRadius += 1;
		ui.refreshBombRadius();
	}

	public void incrementKingTime(double ticks) {
		kingTime += ticks;
		ui.refreshKingTime();
	}
	
	public double getKingTime() {
		return kingTime;
	}

	class PlayerUi extends VBox {
		private final Player player;
		private final Label nameLabel;
		private final Label bombsNumberLabel;
		private final Label bombRadiusLabel;
		private final Label kingTimeLabel;
		private double kingTimeDebounced = -1;

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
		
		protected void refreshBombsNumber() {
			int total = player.bombsNumber;
			int used = player.bombsPlacedNumber;
			Formatter f = new Formatter();
			String r = f.format("Bombs: %2d / %2d", total - used, total).toString();
			f.close();
			this.bombsNumberLabel.setText(r);
		}
		
		protected void refreshBombRadius() {
			int radius = player.bombRadius;
			Formatter f = new Formatter();
			String r = f.format("Bomb Radius: %2d", radius).toString();
			f.close();
			this.bombRadiusLabel.setText(r);
		}
		
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
