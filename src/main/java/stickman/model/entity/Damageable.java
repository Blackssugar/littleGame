package stickman.model.entity;

import stickman.model.level.Level;

/** The Damageable interface. */
public interface Damageable {

  /**
   * Initiates the damageable's death sequence.
   *
   * @param level the damageable's current level.
   */
  void die(Level level);

  /**
   * Initiates the damageable's damage taking sequence.
   *
   * @param level the damageable's current level.
   */
  void takeDamage(Level level);

  /**
   * Gets the damageable's remaining life points.
   *
   * @return the damageable's lives.
   */
  int getLife();

}
