package stickman.model.entity.movement;

import stickman.model.entity.Controllable;
import stickman.model.level.Level;

public class SimpleMovementHandler implements MovementHandler {

  public void updateMovement(Level level, Controllable hero) {

    if (hero.isMovingRight()) {
      hero.step(hero.getRunSpeed(), 0);
    }

    if (hero.isMovingLeft()) {
      hero.step(-hero.getRunSpeed(), 0);
    }

    hero.update();
  }
}
