package stickman.model.entity.movement;

import stickman.model.entity.Controllable;
import stickman.model.level.Level;

/** The MovementHandler interface. */
public interface MovementHandler {

  /**
   * Updates the hero's next position based on its speed and position.
   *
   * @param level the hero's current level.
   * @param hero the hero entity.
   */
  void updateMovement(Level level, Controllable hero);
}
