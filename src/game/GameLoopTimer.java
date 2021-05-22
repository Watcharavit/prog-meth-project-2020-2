package game;

import javafx.animation.AnimationTimer;

/**
 * The game timer implementation. This uses JavaFX's AnimationTimer.
 *
 */
class GameLoopTimer extends AnimationTimer {
	/** Nanosecond timestamp of the latest frame. */
	private long lastFrameNano;
	/** Whether or not the timer is paused. */
	private boolean isPaused = false;
	/** Whether or not the timer is still alive. */
	private boolean isActive = false;

	/**
	 * Handle a frame. Calculate the amount of time sine {@link #lastFrameNano}.
	 * Update {@link #lastFrameNano}. If not {@link #isPaused}, run
	 * {@link #tick(long)}.
	 */
	@Override
	public void handle(long nowNano) {
		// long startNano = System.nanoTime();
		long animDuration = nowNano - this.lastFrameNano;
		this.lastFrameNano = nowNano;
		if (!isPaused)
			tick(animDuration);
		// long finishNano = System.nanoTime();
		// System.out.println(finishNano - startNano);
	}

	/**
	 * Start the timer.
	 */
	@Override
	public void start() {
		this.lastFrameNano = System.nanoTime();
		this.isActive = true;
		super.start();
	}

	/**
	 * Pause the timer.
	 */
	protected void pause() {
		isPaused = true;
	}

	/**
	 * Resume the timer.
	 */
	protected void resume() {
		isPaused = false;
	}

	/**
	 * Stop and destroy the timer. {@link #isActive} will return false after this
	 * runs.
	 */
	@Override
	public void stop() {
		this.isActive = false;
		super.stop();
	}

	/**
	 * Getter for {@link #isActive}
	 * 
	 * @return Whether or not the timer is still alive.
	 */
	protected boolean isActive() {
		return isActive;
	}

	/**
	 * Ran on every frame. To be overriden.
	 * 
	 * @param frameTimeNano The amount of time since last frame in nanoseconds.
	 */
	protected void tick(long frameTimeNano) {

	}
}
