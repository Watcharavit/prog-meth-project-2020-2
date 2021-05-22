package game;

import javafx.animation.AnimationTimer;

class GameLoopTimer extends AnimationTimer {
	private long lastFrameNano;
	private boolean isPaused = false;
	private boolean isActive = false;

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

	@Override
	public void start() {
		this.lastFrameNano = System.nanoTime();
		this.isActive = true;
		super.start();
	}

	protected void pause() {
		isPaused = true;
	}

	protected void resume() {
		isPaused = false;
	}

	@Override
	public void stop() {
		this.isActive = false;
		super.stop();
	}

	protected boolean isActive() {
		return isActive;
	}

	protected void tick(long frameTimeNano) {

	}
}
