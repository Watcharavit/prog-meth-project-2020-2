package application;

import java.util.HashSet;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
	Node node;
	HashSet<KeyCode> activeKeys;
	protected InputManager(Node node) {
		this.node = node;
		this.activeKeys = new HashSet<KeyCode>();
		this.initialize();
		node.setFocusTraversable(true);
	}
	private void initialize() {
		node.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				activeKeys.add(code);
			}
		});
		node.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				activeKeys.remove(code);
			}
		});
	}
	protected HashSet<KeyCode> getActiveKeys() {
		return this.activeKeys;
	}
}
