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
		addWallThornMonster("WallThornFirst", (GameSingleton.WIDTH /2) , (GameSingleton.HEIGHT/2) );
		addWallThornMonster("WallThornFirst", GameSingleton.WIDTH /2 , (GameSingleton.HEIGHT/2)  );
	}
	
	private void addWallThornMonster(String name, double posX, double posY) {
		WallThornMonster wallThorn= new WallThornMonster(posX, posY);
		GameSingleton.addBeing(wallThorn);

	}
	
//	GameSingleton.setTileObject(width / 2 -1 , height / 2 , new  );
//	GameSingleton.setTileObject(width / 2 , height / 2 -1, new KingFloor());
}
