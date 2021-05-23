package entity;

import resources.Sprite;

/**
 * {@link Entity} that is rendered somewhere.
 */
public abstract class PhysicalEntity extends Entity {

	/**
	 * Get the sprite to render for this entity.
	 * 
	 * @return The sprite to render.
	 */
	public abstract Sprite getSprite();

}