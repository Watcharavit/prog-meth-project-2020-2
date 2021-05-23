package resources;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class Sound {

	/**
	 * Sound effect.
	 */
	private final AudioClip clip;

	/**
	 * Load sound effect from resources.
	 * 
	 * @param name URl for a sound effect file.
	 */
	public Sound(String name) {
		URL url = ClassLoader.getSystemResource("sounds/" + name);
		clip = new AudioClip(url.toString());
	}

	/**
	 * Play the sound effect.
	 */
	public void play() {
		clip.play();
	}
}
