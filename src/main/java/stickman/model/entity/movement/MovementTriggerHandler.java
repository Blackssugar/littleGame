package stickman.model.entity.movement;

import stickman.model.entity.Controllable;
import stickman.model.level.Level;

/** The MovementTriggerHandler interface. */
public interface MovementTriggerHandler {

  /**
   * Conditionally triggers a specific movement ability.
   *
   * @param level the hero's current level.
   * @param hero the hero entity.
   */
  void trigger(Level level, Controllable hero);
}
