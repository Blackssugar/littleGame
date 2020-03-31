package stickman.config.parser;

import java.util.Collections;
import java.util.List;
import org.json.simple.JSONObject;
import stickman.Constants;
import stickman.config.ConfigurationUtils;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.factory.EntityFactory;
import stickman.model.entity.factory.EntityFactoryImpl;
import stickman.model.entity.impl.HeroEntity.HeroSize;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.entity.strategy.HostileStrategy;
import stickman.model.entity.strategy.PassiveStrategy;
import stickman.model.entity.strategy.ShyStrategy;
import stickman.model.level.Level;

/** The EntityParser implementation class. */
public class EntityParserImpl implements EntityParser {

  private EntityFactory entityFactory;

  private double xPos;
  private double yPos;

  public EntityParserImpl() {
    this.entityFactory = new EntityFactoryImpl();
  }

  @Override
  public Entity parseEntity(String type, JSONObject object, Level level) {

    switch (type.toUpperCase()) {
      case "CLOUD":
        return parseCloud(object, level);
      case "ENEMY":
        return parseEnemy(object, level);
      case "PLATFORM":
        return parsePlatform(object, level);
      case "FLAG":
        return parseFlag(object, level);
      default:
        throw new IllegalArgumentException("Entity type \"" + type + "\" does not exist.");
    }
  }

  @Override
  public Controllable parseControllable(String type, JSONObject object, Level level) {

    switch (type.toUpperCase()) {
      case "HERO":
        return parseHero(object, level);
      default:
        throw new IllegalArgumentException("Controllable type \"" + type + "\" does not exist.");
    }
  }

  /**
   * Parses and constructs a Hero Controllable type from JSON configuration.
   *
   * @param object the JSONObject to parse data from.
   * @param level the level to which the Controllable will belong.
   * @return the created Controllable.
   */
  private Controllable parseHero(JSONObject object, Level level) {

    // set defaults
    this.xPos = 0.0;
    this.yPos = level.getFloorHeight();

    // get position
    parsePosition(object);

    // get hero-related values
    HeroSize size = HeroSize.NORMAL;
    try {
      String parsed = ConfigurationUtils.parseStringFromObject(object, "size");
      size = HeroSize.valueOf(parsed.toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      // do nothing, if IAE size is invalid, if NPE size not provided
    }

    int lives = 3;
    Integer newLives = ConfigurationUtils.parseIntegerFromObject(object, "lives");
    if (newLives != null) {
      lives = newLives;
    }

    return entityFactory.createControllable("HERO", xPos, yPos, List.of(size, lives));
  }

  /**
   * Parses and constructs a Cloud Entity type from JSON configuration.
   *
   * @param object the JSONObject to parse data from.
   * @param level the level to which the Entity will belong.
   * @return the created Entity.
   */
  private Entity parseCloud(JSONObject object, Level level) {

    // set defaults
    this.xPos = 20.0;
    this.yPos = 100.0;

    // get position
    parsePosition(object);

    // get cloud-related values
    double cloudVelocity = 2.0;

    Double newVelocity = ConfigurationUtils.parseDoubleFromObject(object, "velocity");
    if (newVelocity != null) {
      cloudVelocity = newVelocity;
    }

    return entityFactory.createEntity("CLOUD", xPos, yPos, List.of(cloudVelocity));
  }

  /**
   * Parses and constructs a Enemy Entity type from JSON configuration.
   *
   * @param object the JSONObject to parse data from.
   * @param level the level to which the Entity will belong.
   * @return the created Entity.
   */
  private Entity parseEnemy(JSONObject object, Level level) {

    // set defaults
    this.xPos = 100.0;
    this.yPos = level.getFloorHeight() - Constants.ENEMY_HEIGHT - 5;

    // get position
    parsePosition(object);

    // get enemy-related values
    EnemyStrategy strategy =
        parseEnemyStrategy(ConfigurationUtils.parseStringFromObject(object, "strategy"));

    return entityFactory.createEntity("ENEMY", xPos, yPos, List.of(strategy));
  }

  /**
   * Parses and constructs a Platform Entity type from JSON configuration.
   *
   * @param object the JSONObject to parse data from.
   * @param level the level to which the Entity will belong.
   * @return the created Entity.
   */
  private Entity parsePlatform(JSONObject object, Level level) {

    // set defaults
    this.xPos = 100.0;
    this.yPos = 200.0;

    // get position
    parsePosition(object);

    return entityFactory.createEntity("PLATFORM", xPos, yPos, Collections.emptyList());
  }

  /**
   * Parses and constructs a Flag Entity type from JSON configuration.
   *
   * @param object the JSONObject to parse data from.
   * @param level the level to which the Entity will belong.
   * @return the created Entity.
   */
  private Entity parseFlag(JSONObject object, Level level) {

    // set defaults
    this.xPos = 800.0;
    this.yPos = level.getFloorHeight() - Constants.FLAG_HEIGHT;

    // get position
    parsePosition(object);

    return entityFactory.createEntity("FLAG", xPos, yPos, Collections.emptyList());
  }

  /**
   * Parses the x and y position of an entity from a JSONObject.
   *
   * @param object the JSON configuration to parse
   */
  private void parsePosition(JSONObject object) {

    Double newXPos = ConfigurationUtils.parseDoubleFromObject(object, "xPos");
    if (newXPos != null) {
      xPos = newXPos;
    }

    Double newYPos = ConfigurationUtils.parseDoubleFromObject(object, "yPos");
    if (newYPos != null) {
      yPos = newYPos;
    }
  }

  /**
   * Determines the correct EnemyStrategy from String definition.
   *
   * @param strategy the String representation of the strategy.
   * @return the EnemyStrategy.
   */
  private EnemyStrategy parseEnemyStrategy(String strategy) {

    if (strategy == null) {
      return new PassiveStrategy();
    }

    if (strategy.toUpperCase().equals("HOSTILE")) {
      return new HostileStrategy();
    } else if (strategy.toUpperCase().equals("SHY")) {
      return new ShyStrategy();
    }

    return new PassiveStrategy();
  }
}
