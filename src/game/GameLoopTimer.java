package game;

import javafx.animation.AnimationTimer;

class GameLoopTimer extends AnimationTimer {
	long lastFrameNano;
	boolean isPaused = false;
	@Override
	public void handle(long nowNano) {
		//long startNano = System.nanoTime();
        long animDuration = nowNano - this.lastFrameNano;
        this.lastFrameNano = nowNano;
        if (!isPaused) tick(animDuration);
        //long finishNano = System.nanoTime();
        //System.out.println(finishNano - startNano);
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
