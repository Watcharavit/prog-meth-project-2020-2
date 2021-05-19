package entity;

import entity.base.Being;
import entity.base.Passable;
import entity.base.StillObject;
import entity.base.Updatable;
import graphics.Sprite;


public class KingFloor extends StillObject implements Updatable,Passable{
	private static final Sprite sprite = new Sprite(1);
	private Player player;

	
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub
		if(this.player != null) {
			//System.out.println(this.player.getKingTime());
			this.player.setKingTime(this.player.getKingTime()+ticksPassed); // fps = 16ms.
			this.player = null;
			
		}
	}

	@Override
	public void pass(Being character) {
		// TODO Auto-generated method stub
		if (character instanceof Player) {
			this.player = (Player) character;
		}
	}
	
}
