package stickman.model.entity.factory;

import java.util.List;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;

public interface EntityFactory {

  /**
   * Creates and returns an Entity of the specified type.
   *
   * @param entityType the type of entity to create.
   * @param xPos the entity's x position.
   * @param yPos the entity's y position.
   * @param common a list of extra data that may be necessary for entity instantiation.
   * @return a new Entity instance of the specified type.
   */
  Entity createEntity(String entityType, double xPos, double yPos, List<Object> common);

  /**
   * Creates and returns a Controllable of the specified type.
   *
   * @param entityType the type of entity to create.
   * @param xPos the entity's x position.
   * @param yPos the entity's y position.
   * @param common a list of extra data that may be necessary for entity instantiation.
   * @return a new Controllable instance of the specified type.
   */
  Controllable createControllable(String entityType, double xPos, double yPos, List<Object> common);
}
