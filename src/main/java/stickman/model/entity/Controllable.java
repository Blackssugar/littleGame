package stickman.model.entity;

/** The Controllable interface. */
public interface Controllable extends Entity {

  /**
   * Gets whether or not the controllable is moving left or not.
   *
   * @return True if the controllable will move left at the next tick.
   */
  boolean isMovingLeft();

  /**
   * Sets whether the controllable should move left.
   *
   * @param set whether the controllable should move left.
   * @return True if the controllable will move left at the next tick.
   */
  boolean setMovingLeft(boolean set);

  /**
   * Gets whether or not the controllable is moving right or not.
   *
   * @return whether the controllable will move right at the next tick.
   */
  boolean isMovingRight();

  /**
   * Sets whether the controllable should move right.
   *
   * @param set whether the controllable should move right.
   * @return True if the controllable will move right at the next tick.
   */
  boolean setMovingRight(boolean set);

  /**
   * Gets whether or not the controllable is jumping or not.
   *
   * @return True if the controllable will attempt to jump right at the next tick.
   */
  boolean isJumping();

  /**
   * Sets whether the controllable should attempt to jump.
   *
   * @param set whether the controllable should attempt to jump.
   * @return True if the controllable will attempt to jump at the next tick.
   */
  boolean setJumping(boolean set);

  /**
   * Gets whether or not the controllable is on solid ground.
   *
   * @return True if the controllable is on a solid body.
   */
  boolean isOnGround();

  /**
   * Sets whether the controllable is on solid ground.
   *
   * @param set whether the controllable is on solid ground.
   */
  void setOnGround(boolean set);

  /**
   * Gets the controllable's potential horizonal movement speed.
   *
   * @return the controllable's run speed.
   */
  double getRunSpeed();

  /**
   * Get's the controllable's potential vertical movement speed.
   *
   * @return the controllable's jump strength.
   */
  double getJumpStrength();

  /**
   * Sets the controllable's current y-axis velocity.
   *
   * @param yVelocity the vertical velocity.
   */
  void setYSpeed(double yVelocity);

  /**
   * Changes the controllable's current x and y velocities.
   *
   * @param dx the change in x velocity
   * @param dy the change in y velocity
   */
  void accelerate(double dx, double dy);

  /**
   * Changes the controllable's current x and y positions.
   *
   * @param dx the change in x position
   * @param dy the change in y position
   */
  void step(double dx, double dy);

  /** Updates the controllable's movement. */
  void update();
}
