package entity;

import application.Tile;
import entity.base.Being;
import entity.base.Passable;

public class BombFlame implements Passable {

	@Override
	public void pass(Being character, Tile currentTile) {
		// TODO Auto-generated method stub
		if (character instanceof Player ||character instanceof BalloonMonster) {
			character.setAlive(false);
		}
	}

}
