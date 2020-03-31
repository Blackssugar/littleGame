package stickman.model.entity.strategy;

import stickman.Constants;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

/**
 * The HostileStrategy implementation of EnemyStrategy.
 *
 * <p>Strategy: Move towards hero within a certain range.
 */
public class HostileStrategy implements EnemyStrategy {

  private final double MOVEMENT_SPEED = 0.45;
  private final double RANGE = 200;

  @Override
  public void think(Level level, Entity enemy) {

    double heroXPos = level.getHeroX();
    double heroYPos = level.getHeroY();

    double enemyXPos = enemy.getXPos() + (Constants.ENEMY_HEIGHT / 2);
    double enemyYPos = enemy.getYPos();

    double xDifference = Math.abs(heroXPos - enemyXPos);
    double yDifference = Math.abs(heroYPos - enemyYPos);

    // outside range
    if (xDifference > RANGE || yDifference > RANGE) {
      return;
    }

    // stay still if under hero
    if (xDifference <= MOVEMENT_SPEED) {
      return;
    }

    if (heroXPos < enemyXPos) {
      enemy.setXPos(enemy.getXPos() - MOVEMENT_SPEED);
    } else {
      enemy.setXPos(enemy.getXPos() + MOVEMENT_SPEED);
    }
  }
}
