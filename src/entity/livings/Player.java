package entity.livings;

import java.text.MessageFormat;
import java.util.Formatter;
import java.util.Map;
import java.util.Set;

import entity.Being;
import entity.bomb.BombObject;
import entity.equipments.Equipment;
import entity.equipments.EquipmentPuncher;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
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

public class Player extends LivingBeing implements Collidable, Updatable {
	public static final double SIZE = 0.6;
	private static final double SPEED = 0.1;
	private Equipment equipment;
	private IntegerProperty bombsNumber, bombsPlacedNumber, bombRadius;
	private DoubleProperty kingTime;
	private final String name;
	private final Map<PlayerControl, KeyCode> keyMap;
	private final Sprite normalSprite, dyingSprite;
	private final PlayerUi ui;
	// private boolean isDying; // for red tone player's sprite

	public Player(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile, Sprite normalSprite,
			Sprite dyingSprite, Pane uiPane) {
		super(spawnTile, SIZE);
		this.equipment = null;
		this.bombsNumber = new SimpleIntegerProperty(1);
		this.bombsPlacedNumber = new SimpleIntegerProperty(0);
		this.bombRadius = new SimpleIntegerProperty(1);;
		this.name = name;
		this.keyMap = keyMap;
		this.kingTime = new SimpleDoubleProperty(0);
		this.normalSprite = normalSprite;
		this.dyingSprite = dyingSprite;
		this.ui = new PlayerUi(uiPane, this);
		this.setEquipment(new EquipmentPuncher(this));
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getSprite() {
		if (super.isDead())
			return dyingSprite;
		else
			return normalSprite;
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
			System.out.println(this);
			if (this.equipment != null) {
				this.equipment.tryUseEquipment();
			}
		}
	}

	private void placeBomb() {
		if (GameSingleton.getTileObject((int) super.x, (int) super.y) instanceof Floor) {
			if (bombsPlacedNumber.get() < bombsNumber.get()) {
				BombObject bomb = new BombObject(this, bombRadius.get());
				GameSingleton.setTileObject((int) super.x, (int) super.y, bomb);
				bombsPlacedNumber.set(bombsPlacedNumber.get() + 1);
			}
		}
	}
	
	private void setEquipment(Equipment equipment) {
		if (this.equipment != null)
			GameSingleton.removePhantomEntity(this.equipment);
		GameSingleton.addPhantomEntity(equipment);
		this.equipment = equipment;
	}

	@Override
	public void onDeath() {
		bombRadius.set(Math.max(1, bombRadius.get() - 3));
		bombsNumber.set(Math.max(1, bombsNumber.get() - 3));
	}
	
	@Override
	protected double getSpawnCooldown() {
		return 50.0;
	}

	public void returnBomb() {
		bombsPlacedNumber.set(bombsPlacedNumber.get() - 1);
	}

	public void incrementBombsNumber() {
		bombsNumber.set(bombsNumber.get() + 1);
	}

	public void incrementBombRadius() {
		bombRadius.set(bombRadius.get() + 1);
	}

	public void incrementKingTime(double ticks) {
		kingTime.set(kingTime.get() + ticks / 60);
	}

	
	
	class PlayerUi extends VBox {
		private final Player player;
		private final Label nameLabel;
		private final Label bombsNumberLabel;
		private final Label bombRadiusLabel;
		private final Label kingTimeLabel; 
		private PlayerUi(Pane root, Player player) {
			this.player = player;
			
			root.getChildren().add(this);
			this.nameLabel = new Label(player.name);
			
			this.bombsNumberLabel = new Label();
			StringBinding bombsNumberBinding = Bindings.createStringBinding(() -> {
				int total = player.bombsNumber.get();
				int used = player.bombsPlacedNumber.get();
				Formatter f = new Formatter();
				String r = f.format("Bombs: %2d / %2d", total - used, total).toString();
				f.close();
				return r;
			}, player.bombsPlacedNumber, player.bombsNumber);
			bombsNumberLabel.textProperty().bind(bombsNumberBinding);
			
			this.bombRadiusLabel = new Label();
			bombRadiusLabel.textProperty().bind(player.bombRadius.asString("Bomb Radius: %2d"));
			
			this.kingTimeLabel = new Label();
			kingTimeLabel.textProperty().bind(player.kingTime.asString("King Time: %2.2f"));
			
			this.getChildren().addAll(nameLabel, bombsNumberLabel, bombRadiusLabel, kingTimeLabel);
			this.setPadding(new Insets(24));
			this.setAlignment(Pos.BASELINE_LEFT);
			this.setMinWidth(180);
		}
	}

}
