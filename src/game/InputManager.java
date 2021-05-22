package game;

import java.util.HashSet;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Listens for key events and maintains a set of pressed key to be polled.
 */
public class InputManager {
	/** JavaFX Node to add event filter on. */
	Node inputNode;
	/** Set of currently pressed keys. */
	HashSet<KeyCode> activeKeys;

	/**
	 * Create the listener.
	 * 
	 * @param node The node to listen on.
	 */
	protected InputManager(Node node) {
		this.activeKeys = new HashSet<KeyCode>();
		node.setFocusTraversable(true);
		this.inputNode = node;
		this.initialize();
	}

	/**
	 * Add event filters that will update {@link #activeKeys}.
	 */
	private void initialize() {
		inputNode.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
			KeyCode code = e.getCode();
			activeKeys.add(code);
		});
		inputNode.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
			KeyCode code = e.getCode();
			activeKeys.remove(code);
		});
	}

	protected HashSet<KeyCode> getActiveKeys() {
		return this.activeKeys;
	}
}
