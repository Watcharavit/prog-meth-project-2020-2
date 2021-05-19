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
}
