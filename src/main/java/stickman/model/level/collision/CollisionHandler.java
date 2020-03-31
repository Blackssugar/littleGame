package stickman.model.level.collision;

import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

public class CollisionHandler {

  public void detectCollisions(Level level, Controllable hero) {

    // check for ground collision
    if (hero.getYPos() >= level.getFloorHeight() - hero.getHeight()) {
      hero.setYPos(level.getFloorHeight() - hero.getHeight());
      hero.setJumping(false);
      hero.setOnGround(true);
    }

    // check for entity collisions
    for (int i = 0; i < level.getEntities().size(); i++) {

      Entity e1 = level.getEntities().get(i);

      // we don't care about the background
      if (e1.getLayer().equals(Entity.Layer.BACKGROUND)) {
        continue;
      }

      // check for left wall collision
      if (e1.getXPos() <= 0) {
        e1.setXPos(0);
      }

      for (int j = 0; j < level.getEntities().size(); j++) {

        Entity e2 = level.getEntities().get(j);

        // we don't care about the background
        if (e1.getLayer().equals(Entity.Layer.BACKGROUND) || e1.equals(e2)) {
          continue;
        }

        // check for intersection
        if (e1.intersects(e2)) {
          e1.handleCollision(e2, getCollisionDirection(e1, e2), level);
        }
      }
    }
  }

  public static CollisionDirection getCollisionDirection(Entity entityA, Entity entityB) {

    double entityABottom = entityA.getYPos() + entityA.getHeight();
    double entityBBottom = entityB.getYPos() + entityB.getHeight();
    double entityARight = entityA.getXPos() + entityA.getWidth();
    double entityBRight = entityB.getXPos() + entityB.getWidth();

    double top = entityABottom - entityB.getYPos();
    double bottom = entityBBottom - entityA.getYPos();
    double left = entityARight - entityB.getXPos();
    double right = entityBRight - entityA.getXPos();

    if (top < bottom && top < left && top < right) {
      return CollisionDirection.TOP;
    }

    if (bottom < top && bottom < left && bottom < right) {
      return CollisionDirection.BOTTOM;
    }

    if (left < right && left < top && left < bottom) {
      return CollisionDirection.LEFT;
    }

    return CollisionDirection.RIGHT;
  }
}
