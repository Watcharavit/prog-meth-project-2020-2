package entity.livings;

import entity.Being;
import entity.terrains.TempWall;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;

public class MonsterWallThorn extends LivingBeing implements Updatable, Collidable {
	private static final Sprite sprite = new Sprite(2);
	public static final double SIZE = 1;
	public String direction;
//	private double moveSpeed;

	public MonsterWallThorn(Tile spawnTile, String direction) {
		super(spawnTile, SIZE);
		this.direction = direction;
//		this.moveSpeed = 0;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub
//		this.moveSpeed += ticksPassed;
//		if (this.moveSpeed / 1000 >1) {
//			move();
//			this.moveSpeed /= 1000;
//		}
		
	}

	@Override
	public void collide(Being otherCharacter) {
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		} else if (otherCharacter instanceof MonsterGhost) {
			((MonsterGhost) otherCharacter).die();
		} // Wall-Wall don't do anything
	}

	private void move() {
//		int x = (int) this.getX();
//		int y = (int) this.getY();
//		if(this.direction =="lr") {
//			if(canMove(x-1, y)) {
//				GameSingleton.removeBeing(this);
//				this.setX(x-0.5);
//				this.setY(y-0.5);
//				GameSingleton.addBeing(this);
//			}else if (canMove(x+1, y)) {
//				GameSingleton.removeBeing(this);
//				this.setX(x+0.5);
//				this.setY(y+0.5);
//				GameSingleton.addBeing(this);
//				
//			}
//		}
	}
	
//	private boolean canMove(int x, int y) {
//		if(GameSingleton.getTileObject(x, y) instanceof TempWall) 
//			return false;
//		return true;
//	}
	@Override
	protected void onDeath() {
		// TODO Auto-generated method stub

	}

}
