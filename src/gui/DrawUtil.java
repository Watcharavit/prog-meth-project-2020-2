package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class DrawUtil {

	private static String imagePath = ClassLoader.getSystemResource("images/sprites_sheet.png").toString();
	private static Image mainSprites = new Image(imagePath);
	
	public static void drawSprite(GraphicsContext gc, double x, double y, int index) {
		WritableImage img = new WritableImage(mainSprites.getPixelReader(),index*24,0,24,24);
		gc.drawImage(img, x, y);
	}
}
