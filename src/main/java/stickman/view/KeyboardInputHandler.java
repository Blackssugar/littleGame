package stickman.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import stickman.model.engine.GameEngine;

/** A listener and handler for Keyboard input */
class KeyboardInputHandler {

  private final GameEngine model;
  private final SoundHandler soundHandler;
  private boolean left = false;
  private boolean right = false;
  private Set<KeyCode> pressedKeys = new HashSet<>();

  private Map<String, MediaPlayer> sounds = new HashMap<>();

  KeyboardInputHandler(GameEngine model) {

    this.model = model;
    this.soundHandler = new SoundHandler("/media/");

    soundHandler.registerSound("jump", "jump.wav");
  }

  /**
   * Listens for and handles Key press events.
   *
   * @param keyEvent the Key being pressed.
   */
  void handlePressed(KeyEvent keyEvent) {
    if (pressedKeys.contains(keyEvent.getCode())) {
      return;
    }
    pressedKeys.add(keyEvent.getCode());

    if (keyEvent.getCode().equals(KeyCode.UP)) {
      if (model.jump()) {
        soundHandler.playSound("jump");
      }
    }

    if (keyEvent.getCode().equals(KeyCode.LEFT)) {
      left = true;
    } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
      right = true;
    } else if (keyEvent.getCode().equals(KeyCode.Q)) {
      model.load();
    } else if (keyEvent.getCode().equals(KeyCode.S)) {
      model.save();
    }

    if (left) {
      if (right) {
        model.stopMoving();
      } else {
        model.moveLeft();
      }
    }
    else if (right) {
      model.moveRight();
    }
  }

  /**
   * Listens for and handles Key release events.
   *
   * @param keyEvent the key being released.
   */
  void handleReleased(KeyEvent keyEvent) {
    pressedKeys.remove(keyEvent.getCode());

    if (keyEvent.getCode().equals(KeyCode.LEFT)) {
      left = false;
    } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
      right = false;
    } else {
      return;
    }

    if (!(right || left)) {
      model.stopMoving();
    } else if (right) {
      model.moveRight();
    } else if (left) {
      model.moveLeft();
    }


  }
}
