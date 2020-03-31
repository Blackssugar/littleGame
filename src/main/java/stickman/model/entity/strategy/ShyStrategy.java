package stickman.model.entity.strategy;

import stickman.Constants;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

/**
 * The PassiveStrategy implementation of EnemyStrategy.
 *
 * <p>Strategy: Run away from hero within a certain range.
 */
public class ShyStrategy implements EnemyStrategy {

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

    if (heroXPos < enemyXPos) {
      enemy.setXPos(enemy.getXPos() + MOVEMENT_SPEED);
    } else {
      enemy.setXPos(enemy.getXPos() - MOVEMENT_SPEED);
    }
  }
}
