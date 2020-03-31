package stickman.view;

import javafx.scene.Node;
import stickman.model.entity.Entity;

public interface EntityView {

  /**
   * Updates the entity view's position on screen.
   *
   * @param xViewportOffset the x-displacement
   * @param yViewportOffset the y-displacement
   */
  void updateView(double xViewportOffset, double yViewportOffset);

  /**
   * Whether or not the EntityView matches the specified Entity.
   *
   * @param entity the Entity to check against
   * @return true if the EntityView matches the Entity
   */
  boolean matchesEntity(Entity entity);

  /** Toggles whether the EntityView should be deleted at the next display update. */
  void markForDelete();

  /**
   * Gets the ImageView node object.
   *
   * @return the node.
   */
  Node getNode();

  /**
   * Gets whether or not the EntityView should be deleted at the next display update
   *
   * @return true if the EntityView should be deleted.
   */
  boolean isMarkedForDelete();
}
