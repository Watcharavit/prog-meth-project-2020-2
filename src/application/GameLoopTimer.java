package application;

import javafx.animation.AnimationTimer;

class GameLoopTimer extends AnimationTimer {
	long lastFrameNano;
	@Override
	public void handle(long nowNano) {
        long animDuration = nowNano - this.lastFrameNano;
        this.lastFrameNano = nowNano;
        tick(animDuration);
	}
	protected void tick(long frameTimeNano) {
		
	}
}
