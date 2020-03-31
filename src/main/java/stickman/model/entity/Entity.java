package stickman.model.entity;

import javafx.geometry.Rectangle2D;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The Entity interface */
public interface Entity {

  /**
   * Gets the filepath of the Entity's texture.
   *
   * @return The image's filepath
   */
  String getImagePath();

  /**
   * Set's the filepath of the Entity's texture.
   *
   * @param imagePath The image's filepath
   */
  void setImagePath(String imagePath);

  /**
   * Get the position of the entity along the x-axis.
   *
   * @return the entity's x position
   */
  double getXPos();

  /**
   * Set the position of the entity along the x-axis.
   *
   * @param xPos the entity's x position
   */
  void setXPos(double xPos);

  /**
   * Set the position of the entity along the y-axis.
   *
   * @return the entity's y position
   */
  double getYPos();

  /**
   * Sets the position of the entity along the y-axis.
   *
   * @param yPos the entity's y position
   */
  void setYPos(double yPos);

  /**
   * Get the entity's size in the x-axis.
   *
   * @return the entity's width
   */
  double getWidth();

  /**
   * Get the entity's size in the y-axis.
   *
   * @return the entity's height
   */
  double getHeight();

  /**
   * Get the layer where the entity will be displayed in the view
   *
   * @return the entity's layer
   */
  Layer getLayer();

  /** Used to describe where the entity will be displayed in the view. */
  enum Layer {
    BACKGROUND,
    FOREGROUND,
    CHARACTER,
    EFFECT
  }

  /**
   * Get the rectangle bounding the entity.
   *
   * @return the entity's bounding box.
   */
  Rectangle2D getBoundary();

  /**
   * Determines whether or not the entity intersects with another entity.
   *
   * @param other the entity to check for intersection.
   * @return true if the entities intersect.
   */
  boolean intersects(Entity other);

  /**
   * Attempts to move the entity.
   *
   * @param level the entity's current level.
   */
  void move(Level level);

  /**
   * Handles a collision between this entity and another entity.
   *
   * @param other the collided entity.
   * @param direction the direction of collision (e.g. TOP, BOTTOM).
   * @param level the entity's current level.
   * @return true if the entity acknowledges and responds to the collision
   */
  boolean handleCollision(Entity other, CollisionDirection direction, Level level);

  Entity clone();
}
