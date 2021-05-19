package game;

import java.util.Map;

import entity.KingFloor;
import entity.Player;
import entity.WallThornMonster;
import javafx.scene.input.KeyCode;
import logic.PlayerControl;

public class MonsterGenerator {
	
	
	protected MonsterGenerator() {
		addMonster();
	}
	protected void addMonster() {
		addWallThornMonster("WallThornFirst", GameSingleton.tiles[GameSingleton.WIDTH / 2][GameSingleton.HEIGHT / 2]);
	}
	
	private void addWallThornMonster(String name, Tile spawnTile) {
		WallThornMonster wallThorn = new WallThornMonster(spawnTile);
		GameSingleton.addBeing(wallThorn);

	}
	
//	GameSingleton.setTileObject(width / 2 -1 , height / 2 , new  );
//	GameSingleton.setTileObject(width / 2 , height / 2 -1, new KingFloor());
}
