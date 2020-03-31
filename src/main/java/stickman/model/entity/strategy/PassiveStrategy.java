package stickman.model.entity.strategy;

import stickman.model.entity.Entity;
import stickman.model.level.Level;

/**
 * The PassiveStrategy implementation of EnemyStrategy.
 *
 * <p>Strategy: Do nothing.
 */
public class PassiveStrategy implements EnemyStrategy {

  @Override
  public void think(Level level, Entity enemy) {}
}
