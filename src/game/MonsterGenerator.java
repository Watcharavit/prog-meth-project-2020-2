package game;

import entity.livings.LivingBeing;
import entity.livings.MonsterGhost;
import entity.livings.MonsterWallThorn;
import entity.terrains.Floor;
import entity.terrains.TempWall;
import logic.Direction;

public class MonsterGenerator {

	protected MonsterGenerator() {
		addMonster();
	}

	protected void addMonster() {
		addWallThornMonster("WallThorn1", GameSingleton.tiles[GameSingleton.WIDTH / 2 - 1][GameSingleton.HEIGHT / 2], Direction.LEFT);
		addWallThornMonster("WallThorn2", GameSingleton.tiles[GameSingleton.WIDTH / 2][GameSingleton.HEIGHT / 2 - 1], Direction.UP);
		addGhost("Ghost1", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)),
				5)][GameSingleton.HEIGHT / 4]);
		addGhost("Ghost2", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)),
				5)][GameSingleton.HEIGHT / 4]);
		addGhost("Ghost3", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)),
				5)][GameSingleton.HEIGHT * 3 / 4]);
		addGhost("Ghost4", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)),
				5)][GameSingleton.HEIGHT * 3 / 4]);

	}

	private void addWallThornMonster(String name, Tile spawnTile, Direction direction) {
		MonsterWallThorn wallThorn = new MonsterWallThorn(spawnTile, direction);
		GameSingleton.addBeing(wallThorn);
		clearTileAtMonster(wallThorn);

	}

	private void addGhost(String name, Tile spawnTile) {
		MonsterGhost ghost = new MonsterGhost(spawnTile);
		GameSingleton.addBeing(ghost);
		clearTileAtMonster(ghost);
	}

	private void clearTileAtMonster(LivingBeing being) {
		int x = (int) being.getX();
		int y = (int) being.getY();
		if (GameSingleton.getTileObject(x, y) instanceof TempWall)
			GameSingleton.setTileObject(x, y, new Floor());
	}

}
