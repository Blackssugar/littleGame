package stickman.model.engine;

import stickman.model.level.Level;

/** The GameEngine interface */
public interface GameEngine extends GameSubject, QuickSave {

  /**
   * Returns the currently active level.
   *
   * @return The current level.
   */
  Level getCurrentLevel();

  /** Loads and starts the current level */
  void startLevel();

  /**
   * Calls the current level's jump method.
   *
   * @return True if the hero will jump, else false.
   */
  boolean jump();

  /**
   * Calls the current level's moveLeft method.
   *
   * @return True if the hero will move left, else false.
   */
  boolean moveLeft();

  /**
   * Calls the current level's moveRight method.
   *
   * @return True if the hero will move right, else false.
   */
  boolean moveRight();

  /**
   * Calls the current level's stopMoving method.
   *
   * @return True if the hero will cease all movement, else false.
   */
  boolean stopMoving();

  /** Calls the current level's tick method. */
  void tick();


}
