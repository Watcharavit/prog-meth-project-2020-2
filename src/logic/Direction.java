package logic;

public enum Direction {

	/**
	 * Directions possible in the game.
	 */
	LEFT, RIGHT, UP, DOWN;

	/**
	 * 
	 * @return -1 if control go left, 1 if control go right, 0 if no control.
	 */
	public int getDeltaX() {
		if (this == LEFT)
			return -1;
		if (this == RIGHT)
			return 1;
		return 0;
	}

	/**
	 * 
	 * @return -1 if control go up, 1 if control go down, 0 if no control.
	 */
	public int getDeltaY() {
		if (this == UP)
			return -1;
		if (this == DOWN)
			return 1;
		return 0;
	}

	/**
	 * Get direction randomly
	 * 
	 * @return 25% probability each direction.
	 */
	public static Direction random() {
		double r = Math.random();
		if (r >= 0.75)
			return DOWN;
		else if (r >= 0.50)
			return UP;
		else if (r >= 0.25)
			return RIGHT;
		else
			return LEFT;
	}

	/**
	 * When monsters collide, go to opposite direction
	 * 
	 * @return Opposite direction of original direction
	 */
	public Direction flip() {
		if (this == LEFT)
			return RIGHT;
		if (this == RIGHT)
			return LEFT;
		if (this == UP)
			return DOWN;
		if (this == DOWN)
			return UP;

		// Unreachable.
		return null;
	}
}
