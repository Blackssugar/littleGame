package stickman.model.entity.factory;

import java.util.List;
import stickman.Constants;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.Entity.Layer;
import stickman.model.entity.impl.CloudEntity;
import stickman.model.entity.impl.EnemyEntity;
import stickman.model.entity.impl.FlagEntity;
import stickman.model.entity.impl.HeroEntity;
import stickman.model.entity.impl.HeroEntity.HeroSize;
import stickman.model.entity.impl.TileEntity;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.entity.strategy.HostileStrategy;
import stickman.model.entity.strategy.ShyStrategy;

public class EntityFactoryImpl implements EntityFactory {

  @Override
  public Entity createEntity(String type, double xPos, double yPos, List<Object> common) {

    switch (type.toUpperCase()) {
      case "CLOUD":
        double velocity = (double) common.get(0);
        return new CloudEntity(
            "media/cloud_2.png",
            xPos,
            yPos,
            Constants.CLOUD_WIDTH,
            Constants.CLOUD_HEIGHT,
            Layer.BACKGROUND,
            velocity);
      case "ENEMY":
        EnemyStrategy strategy = (EnemyStrategy) common.get(0);
        return new EnemyEntity(
            getEnemyTexture(strategy),
            xPos,
            yPos,
            Constants.ENEMY_WIDTH,
            Constants.ENEMY_HEIGHT,
            Layer.FOREGROUND,
            strategy);
      case "PLATFORM":
        return new TileEntity(
            "media/brick.png",
            xPos,
            yPos,
            Constants.PLATFORM_WIDTH,
            Constants.PLATFORM_HEIGHT,
            Layer.FOREGROUND);
      case "TILE":
        return new TileEntity(
            "media/foot_tile.png",
            xPos,
            yPos,
            Constants.TILE_SIDE,
            Constants.TILE_SIDE,
            Layer.BACKGROUND);
      case "FLAG":
        return new FlagEntity(
            "media/flag-main.png",
            xPos,
            yPos,
            Constants.FLAG_WIDTH,
            Constants.FLAG_HEIGHT,
            Layer.FOREGROUND);
      default:
        throw new IllegalArgumentException("Entity type \"" + type + "\" does not exist.");
    }
  }

  @Override
  public Controllable createControllable(
      String type, double xPos, double yPos, List<Object> common) {

    switch (type.toUpperCase()) {
      case "HERO":
        HeroSize size = (HeroSize) common.get(0);
        int lives = (int) common.get(1);
        return new HeroEntity(
            "media/ch_stand1.png",
            xPos,
            yPos,
            size.getWidth(),
            size.getHeight(),
            Layer.CHARACTER,
            (int) common.get(1));
      default:
        throw new IllegalArgumentException("Controllable type \"" + type + "\" does not exist.");
    }
  }

  /**
   * Gets the correct enemy texture path based on strategy.
   *
   * @param strategy the EnemyStrategy type.
   * @return the String representation of the media path.
   */
  private String getEnemyTexture(EnemyStrategy strategy) {

    if (strategy instanceof HostileStrategy) {
      return "media/slimeRa.png";
    } else if (strategy instanceof ShyStrategy) {
      return "media/slimeBa.png";
    } else {
      return "media/slimePa.png";
    }
  }
}
