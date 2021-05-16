package entity.base;

import application.GameSingleton;
import gui.Sprite;  

public abstract class Entity {
	public Entity() {
		GameSingleton.addEntity(this);
	}
	
	public void destroy() {
		GameSingleton.removeEntity(this);
	}
	
	public abstract Sprite getSprite();
	// return image that should be rendered

}
