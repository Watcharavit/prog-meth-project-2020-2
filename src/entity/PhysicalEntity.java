package entity;

import resources.Sprite;

/**
 * This class shows that the sub class is visible for player.
 */
public abstract class PhysicalEntity extends Entity {

	/**
	 * Get the sprite to be rendered for this entity.
	 */
	public abstract Sprite getSprite();
	// return image that should be rendered

}