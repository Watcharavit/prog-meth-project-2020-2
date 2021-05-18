package game;

import javafx.animation.AnimationTimer;

class GameLoopTimer extends AnimationTimer {
	long lastFrameNano;
	boolean isPaused = false;
	@Override
	public void handle(long nowNano) {
        long animDuration = nowNano - this.lastFrameNano;
        this.lastFrameNano = nowNano;
        if (!isPaused) tick(animDuration);
	}
	protected void pause() {
		isPaused = true;
	}
	protected void resume() {
		isPaused = false;
	}
	protected void tick(long frameTimeNano) {
		
	}
}
