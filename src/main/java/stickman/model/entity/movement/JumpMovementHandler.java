package stickman.model.entity.movement;

import stickman.model.entity.Controllable;
import stickman.model.level.Level;

public class JumpMovementHandler implements MovementTriggerHandler {

  @Override
  public void trigger(Level level, Controllable hero) {

    if (hero.isOnGround() && hero.isJumping()) {
      hero.setYSpeed(0);
      hero.accelerate(0, -hero.getJumpStrength());
      hero.setOnGround(false);
    }
  }
}
