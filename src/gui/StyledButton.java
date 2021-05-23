package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Button with our red-and-yellow style.
 *
 */
public class StyledButton extends Button {
	/**
	 * Create a button with the specified text.
	 * 
	 * @param text The text content of the button.
	 */
	public StyledButton(String text) {
		this(text, new CornerRadii(12));
	}

	/**
	 * Create a button with the specified text and corner radii.
	 * 
	 * @param text  The text content of the button.
	 * @param radii The corner radii.
	 */
	public StyledButton(String text, CornerRadii radii) {
		super(text);
		this.setBackground(new Background(new BackgroundFill(new Color(0.62, 0.22, 0.22, 1), radii, Insets.EMPTY)));
		this.setPadding(new Insets(12));
		this.setTextFill(new Color(1.0, 0.8, 0.4, 1));
		this.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
		DropShadow shadow = new DropShadow(4, Color.BLACK);
		this.setEffect(shadow);
	}
}