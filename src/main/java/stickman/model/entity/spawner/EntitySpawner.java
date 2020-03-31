package stickman.model.entity.spawner;

import java.util.Collection;
import stickman.config.ConfigurationProvider;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

public interface EntitySpawner {

  /**
   * Spawns a hero object using configuration data.
   *
   * @param provider the ConfigurationProvider containing the entity data.
   * @param level the level which will contain the hero.
   * @return the Controllable hero object.
   */
  Controllable createHero(ConfigurationProvider provider, Level level);

  /**
   * Spawns zero or more entity types using configuration data.
   *
   * @param provider the ConfigurationProvider containing the entity data.
   * @param level the level which will contain the entities.
   * @return the Collection of newly spawned entities.
   */
  Collection<Entity> createEntities(ConfigurationProvider provider, Level level);

  /**
   * Spawns necessary entities for the level's background using configuration data.
   *
   * @param provider the ConfigurationProvider containing the entity data.
   * @param level the level which will contain the entities.
   * @return the Collection of newly spawned entities.
   */
  Collection<Entity> createBackgroundEntities(ConfigurationProvider provider, Level level);
}
