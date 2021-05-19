package game;

import java.util.HashSet;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
	Node inputNode;
	HashSet<KeyCode> activeKeys;
	protected InputManager(Node node) {
		
		this.activeKeys = new HashSet<KeyCode>();
		node.setFocusTraversable(true);
		this.inputNode = node;
		this.initialize();
		
	}
	private void initialize() {
		inputNode.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
			KeyCode code = e.getCode();
			activeKeys.add(code);
		});
		inputNode.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
			KeyCode code = e.getCode();
			activeKeys.remove(code);
		});
		/*
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				activeKeys.add(code);
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				activeKeys.remove(code);
			}
		});*/
	}
	protected HashSet<KeyCode> getActiveKeys() {
		return this.activeKeys;
	}
}
