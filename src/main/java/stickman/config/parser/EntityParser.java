package stickman.config.parser;

import org.json.simple.JSONObject;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

/** The EntityParser interface. */
public interface EntityParser {

  /**
   * Parses an entity from JSON configuration.
   *
   * @param type the entity type's name.
   * @param object the JSONObject to parse data from.
   * @param level the entity's destination level.
   * @return the created entity.
   */
  Entity parseEntity(String type, JSONObject object, Level level);

  /**
   * Parses a controllable from JSON configuration.
   *
   * @param type the controllable type's name
   * @param object the JSONObject to parse data from
   * @param level the controllable's destination level.
   * @return the created controllable.
   */
  Controllable parseControllable(String type, JSONObject object, Level level);
}
