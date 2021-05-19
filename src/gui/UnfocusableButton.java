package gui;

import javafx.scene.control.Button;

public class UnfocusableButton extends Button {
	public UnfocusableButton(String label) {
		super(label);
		this.setFocusTraversable(false);
	}
}
