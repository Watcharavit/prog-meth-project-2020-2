package entity;

import resources.Sprite;


/**
 * Override in sub class.
 * @author Watch
 *
 */
public abstract class PhysicalEntity extends Entity {

	/**
	 * Override in sub class.
	 */
	public abstract Sprite getSprite();
	// return image that should be rendered

}