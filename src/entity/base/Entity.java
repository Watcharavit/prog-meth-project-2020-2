package entity.base;

import logic.Sprite;  

public abstract class Entity {

	protected double x;
	protected double y;

	public abstract Sprite getSprite();
	// return image that should be rendered

}
