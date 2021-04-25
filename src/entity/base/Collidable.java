package entity.base;

public interface Collidable {
	public abstract void collide(entity.Character otherCharacter);
	// Do something to the other character upon collision
}
