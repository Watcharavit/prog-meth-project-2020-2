package resources;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * This class provides all soundtracks.
 *
 */
public final class MusicsLibrary {

	static class Music {
		
		/**
		 * Soundtrack file.
		 */
		private MediaPlayer player;

		/**
		 * Load soundtrack from resources. If soundtrack ends, play again.
		 * @param name URL of a soundtrack.
		 */
		public Music(String name) {
			MediaPlayer player = null;
			try {
				URL url = ClassLoader.getSystemResource("musics/" + name);
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);
				player.setOnEndOfMedia(() -> {
					this.player.seek(Duration.ZERO);
				});
			} catch (Exception e) {
				System.out.println("Media Initialization Failed. Blame JavaFX.");

			}
			this.player = player;
		}

		/**
		 * Play soundtrack.
		 */
		protected void play() {
			if (player != null) {
				player.play();
			}
		}

		/**
		 * Stop soundtrack.
		 */
		protected void stop() {
			if (player != null) {
				player.stop();
			}
		}
	}

	public static Music MAIN_MENU = new Music("main_menu.mp3");
	public static Music GAME_END = new Music("game_end.mp3");
	public static Music GAME_PLAY = new Music("game_play.mp3");
	private static Music activeMusic;

	/**
	 * Control the soundtrack.
	 * @param music Soundtrack music.
	 */
	public static void playMusic(Music music) {
		if (activeMusic != null)
			activeMusic.stop();
		activeMusic = music;
		if (music != null) {
			music.play();
		}
	}
}
