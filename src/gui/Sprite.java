package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Sprite {
	private static Image mainSprites = new Image(ClassLoader.getSystemResource("sprite_sheet.png").toString());
	private static final int spriteSize = 24;
	
	private final WritableImage img;
	
	public Sprite(int index) {
		img = new WritableImage(mainSprites.getPixelReader(), index*spriteSize, 0, spriteSize, spriteSize);
	}
	public void drawTopLeft(GraphicsContext gc, double x, double y) {
		gc.drawImage(img, x, y);
	}
	public void drawCenter(GraphicsContext gc, double x, double y) {
		gc.drawImage(img, x - spriteSize / 2, y - spriteSize / 2);
	}
}
