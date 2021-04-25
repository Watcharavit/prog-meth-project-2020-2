package entity;

import entity.base.Passable;
import gui.Tile;

public class BombFlame implements Passable {

	@Override
	public void pass(Character character, Tile currentTile) {
		// TODO Auto-generated method stub
		if (character instanceof Player ||character instanceof BalloonMonster) {
			character.setAlive(false);
		}
	}

}
