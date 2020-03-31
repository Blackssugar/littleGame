package stickman.model.entity.spawner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.simple.JSONObject;
import stickman.Constants;
import stickman.config.ConfigurationProvider;
import stickman.config.ConfigurationUtils;
import stickman.config.parser.EntityParser;
import stickman.config.parser.EntityParserImpl;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.factory.EntityFactory;
import stickman.model.entity.factory.EntityFactoryImpl;
import stickman.model.entity.impl.EnemyEntity;
import stickman.model.level.Level;

public class EntitySpawnerImpl implements EntitySpawner {

  private EntityParser parser;
  private EntityFactory entityFactory;

  public EntitySpawnerImpl() {
    parser = new EntityParserImpl();
    entityFactory = new EntityFactoryImpl();
  }

  @Override
  public Controllable createHero(ConfigurationProvider provider, Level level) {

    JSONObject data = provider.getLevelData().getEntityData();

    if (!ConfigurationUtils.containsValidJSONObject(data, "hero")) {
      System.out.println("Error: Hero configuration is missing or improperly configured.");
      System.out.println("Please refer to the documentation regarding configuration.");
      throw new IllegalArgumentException("Hero configuration is missing or improperly configured.");
    }

    return parser.parseControllable(
        "HERO", ConfigurationUtils.toJSONObject(data.get("hero")), level);
  }

  @Override
  public Collection<Entity> createEntities(ConfigurationProvider provider, Level level) {

    List<Entity> created = new ArrayList<>();
    JSONObject data = provider.getLevelData().getEntityData();

    spawnClouds(data, created, level);
    spawnEnemies(data, created, level);
    spawnPlatforms(data, created, level);

    return created;
  }

  @Override
  public Collection<Entity> createBackgroundEntities(ConfigurationProvider provider, Level level) {

    List<Entity> created = new ArrayList<>();
    JSONObject data = provider.getLevelData().getEntityData();

    // create floor tiles
    /*for (int i = 0; i <= provider.getLevelData().getWidth() * 2; i += Constants.TILE_SIDE) {
      created.add(
          entityFactory.createEntity("TILE", i, provider.getLevelData().getFloorHeight(), null));
    }*/

    // create flag
    if (ConfigurationUtils.containsValidJSONObject(
        provider.getLevelData().getEntityData(), "flag")) {
      created.add(
          parser.parseEntity("FLAG", ConfigurationUtils.toJSONObject(data.get("flag")), level));
    }

    return created;
  }

  /**
   * Spawns zero or more clouds based on JSON configuration data.
   *
   * @param data the JSON configuration object.
   * @param created the list of newly created clouds.
   * @param level the level which will contain the clouds.
   */
  private void spawnClouds(JSONObject data, Collection<Entity> created, Level level) {

    if (ConfigurationUtils.containsValidJSONObject(data, "clouds")) {
      JSONObject clouds = ConfigurationUtils.toJSONObject(data.get("clouds"));

      for (Object key : clouds.keySet()) {

        Entity e =
            parser.parseEntity("CLOUD", ConfigurationUtils.toJSONObject(clouds.get(key)), level);
        if (e != null) {
          created.add(e);
        }
      }
    }
  }

  /**
   * Spawns zero or more enemies based on JSON configuration data.
   *
   * @param data the JSON configuration object.
   * @param created the list of newly created enemies.
   * @param level the level which will contain the enemies.
   */
  private void spawnEnemies(JSONObject data, Collection<Entity> created, Level level) {

    if (ConfigurationUtils.containsValidJSONObject(data, "enemies")) {
      JSONObject enemies = ConfigurationUtils.toJSONObject(data.get("enemies"));

      for (Object key : enemies.keySet()) {

        Entity e =
            parser.parseEntity("ENEMY", ConfigurationUtils.toJSONObject(enemies.get(key)), level);
        if (e != null) {
          created.add(e);
        }
      }
    }
  }

  /**
   * Spawns zero or more platforms based on JSON configuration data.
   *
   * @param data the JSON configuration object.
   * @param created the list of newly created platforms.
   * @param level the level which will contain the platforms.
   */
  private void spawnPlatforms(JSONObject data, Collection<Entity> created, Level level) {

    if (ConfigurationUtils.containsValidJSONObject(data, "platforms")) {
      JSONObject platforms = ConfigurationUtils.toJSONObject(data.get("platforms"));

      for (Object key : platforms.keySet()) {
        Entity e =
            parser.parseEntity(
                "PLATFORM", ConfigurationUtils.toJSONObject(platforms.get(key)), level);
        if (e != null) {
          created.add(e);
        }
      }
    }
  }
}
