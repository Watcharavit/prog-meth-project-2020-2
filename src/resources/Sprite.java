package resources;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * This class used to get sprite for each object in the game.
 *
 */
public class Sprite {

	/**
	 * Image of every objects.
	 */
	private static Image mainSprites = new Image(ClassLoader.getSystemResource("spritesheet.png").toString());

	/**
	 * Sprite size that will be used in the game.
	 */
	private static final int spriteSize = 24;

	/**
	 * Image of each object.
	 */
	private final WritableImage img;

	/**
	 * Get the image for each object.
	 * 
	 * @param index Index of the image.
	 */
	public Sprite(int index) {
		img = new WritableImage(mainSprites.getPixelReader(), index * spriteSize, 0, spriteSize, spriteSize);
	}

	/**
	 * Set Top left scope of image
	 * 
	 * @param gc Graphic.
	 * @param x  Position.
	 * @param y  Position.
	 */
	public void drawTopLeft(GraphicsContext gc, double x, double y) {
		gc.drawImage(img, x, y);
	}

	/**
	 * Set center of image
	 * 
	 * @param gc Graphic.
	 * @param x  Position.
	 * @param y  Position.
	 */
	public void drawCenter(GraphicsContext gc, double x, double y) {
		gc.drawImage(img, x - spriteSize / 2, y - spriteSize / 2);
	}
}