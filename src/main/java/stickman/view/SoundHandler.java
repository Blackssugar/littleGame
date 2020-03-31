package stickman.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** A simple storage class for game sound files. */
public class SoundHandler {

  private Map<String, MediaPlayer> sounds;
  private String mediaRoot;

  public SoundHandler(String mediaRoot) {
    sounds = new HashMap<>();
    this.mediaRoot = mediaRoot;
  }

  /**
   * Plays a sound stored inside the handler.
   *
   * @param sound the sound's identifier.
   * @return true if the sound will be played.
   */
  public boolean playSound(String sound) {

    MediaPlayer player = sounds.get(sound);
    if (player == null) {
      return false;
    }

    player.stop();
    player.play();

    return true;
  }

  /**
   * Registers and stores a sound inside the handler
   *
   * @param soundName the sound's identifier.
   * @param filename the sound's filepath inside the root media folder.
   */
  public void registerSound(String soundName, String filename) {

    URL soundURL = getClass().getResource(mediaRoot + filename);
    MediaPlayer player = new MediaPlayer(new Media(soundURL.toExternalForm()));

    sounds.put(soundName, player);
  }
}
