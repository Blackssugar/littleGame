package stickman.model.entity.strategy;

import stickman.model.entity.Entity;
import stickman.model.level.Level;

/** The EnemyStrategy interface. */
public interface EnemyStrategy {

  /**
   * Calculates the enemy's movement strategy.
   *
   * @param level the enemy's current level.
   * @param enemy the enemy entity.
   */
  void think(Level level, Entity enemy);
}
