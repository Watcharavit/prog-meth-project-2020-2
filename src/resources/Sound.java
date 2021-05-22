package resources;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class Sound {
	private final AudioClip clip;

	public Sound(String name) {
		URL url = ClassLoader.getSystemResource("sounds/" + name);
		clip = new AudioClip(url.toString());
	}

	public void play() {
		clip.play();
	}
}
