package interfaces;

public interface Updatable {

	/**
	 * Render the game every 1/60 seconds according to the frame rate.
	 * 
	 * @param ticksPassed Frame rate
	 */
	public abstract void update(double ticksPassed);
}
