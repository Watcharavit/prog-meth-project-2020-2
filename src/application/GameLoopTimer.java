package application;

import javafx.animation.AnimationTimer;

public class GameLoopTimer extends AnimationTimer {
	long lastFrameNano;
	@Override
	public void handle(long nowNano) {
        long animDuration = nowNano - this.lastFrameNano;
        this.lastFrameNano = nowNano;
        tick(animDuration);
	}
	public void tick(long frameTimeNano) {
		
	}
}
