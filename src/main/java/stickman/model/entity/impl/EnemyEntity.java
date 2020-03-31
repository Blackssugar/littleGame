package stickman.model.entity.impl;

import stickman.model.entity.Animatable;
import stickman.model.entity.Damageable;
import stickman.model.entity.Entity;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.entity.strategy.HostileStrategy;
import stickman.model.entity.strategy.PassiveStrategy;
import stickman.model.entity.strategy.ShyStrategy;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The enemy Entity type. */
public class EnemyEntity extends AbstractEntity implements Damageable, Animatable {

  private EnemyStrategy strategy;
  private int life;
  private int frames = 0;

  public EnemyEntity(
      String imagePath,
      double xPos,
      double yPos,
      double width,
      double height,
      Layer layer,
      EnemyStrategy strategy) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.strategy = strategy;
    this.life = 1;
  }

  @Override
  public void move(Level level) {
    animate();
    strategy.think(level, this);
  }

  @Override
  public void die(Level level) {
    level.getEntities().remove(this);
  }

  @Override
  public void takeDamage(Level level) {

    int newLife = life - 1;

    if (newLife <= 0) {
      die(level);
    } else {
      this.life = newLife;
    }
  }

  @Override
  public int getLife() {
    return life;
  }

  @Override
  public boolean handleCollision(Entity other, CollisionDirection direction, Level level) {

    if (other instanceof HeroEntity) {

      if (!direction.equals(CollisionDirection.BOTTOM)) {
        return false;
      }

      takeDamage(level);
      return true;
    }

    return false;
  }

  // exposed for testing
  public EnemyStrategy getStrategy() {
    return strategy;
  }

  @Override
  public EnemyEntity clone() {
    if(this.getStrategy().getClass() == HostileStrategy.class) {
      return new EnemyEntity(this.getImagePath(),this.getXPos(),this.getYPos(),this.getWidth(),
              this.getHeight(),this.getLayer(),new HostileStrategy());
    }
    else if(this.getStrategy().getClass() == PassiveStrategy.class) {
      return new EnemyEntity(this.getImagePath(), this.getXPos(), this.getYPos(), this.getWidth(),
              this.getHeight(), this.getLayer(), new PassiveStrategy());
    } else {
      return new EnemyEntity(this.getImagePath(), this.getXPos(), this.getYPos(), this.getWidth(),
              this.getHeight(), this.getLayer(), new ShyStrategy());
    }
  }

  @Override
  public void animate() {
    frames++;
    if(frames%40!=0) return;
    switch(getImagePath()){
      case "media/slimeBa.png":
        this.setImagePath("media/slimeBb.png");
        break;
      case "media/slimeBb.png":
        this.setImagePath("media/slimeBa.png");
        break;
      case "media/slimeGa.png":
        this.setImagePath("media/slimeGb.png");
        break;
      case "media/slimeGb.png":
        this.setImagePath("media/slimeGa.png");
        break;
      case "media/slimePa.png":
        this.setImagePath("media/slimePb.png");
        break;
      case "media/slimePb.png":
        this.setImagePath("media/slimePa.png");
        break;
      case "media/slimeRa.png":
        this.setImagePath("media/slimeRb.png");
        break;
      case "media/slimeRb.png":
        this.setImagePath("media/slimeRa.png");
        break;
      case "media/slimeYa.png":
        this.setImagePath("media/slimeYb.png");
        break;
      default:
        this.setImagePath("media/slimeYa.png");
    }
  }
}
