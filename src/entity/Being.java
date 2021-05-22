package entity;

public abstract class Being extends PhysicalEntity {

	/**
	 * Its position.
	 */
	private double x, y;

	/**
	 * Its size.
	 */
	public final double halfSize;

	/**
	 * Constructor for sub class.
	 * 
	 * @param x    Its position.
	 * @param y    Its position.
	 * @param size Its size.
	 */
	public Being(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.halfSize = size / 2;
	}

	/**
	 * 
	 * @return Its X position.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return Its Y position.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Set its X position.
	 * 
	 * @param x Its X position.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set its Y position.
	 * 
	 * @param y Its Y position.
	 */
	public void setY(double y) {
		this.y = y;
	}

}
