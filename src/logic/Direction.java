package logic;

public enum Direction {
	LEFT, RIGHT, UP, DOWN;

	public int getDeltaX() {
		if (this == LEFT)
			return -1;
		if (this == RIGHT)
			return 1;
		return 0;
	}

	public int getDeltaY() {
		if (this == UP)
			return -1;
		if (this == DOWN)
			return 1;
		return 0;
	}

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
