package stickman.model.level;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.config.LevelSettings;
import stickman.model.entity.Controllable;
import stickman.model.entity.Damageable;
import stickman.model.entity.Entity;
import stickman.model.entity.impl.HeroEntity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.entity.spawner.EntitySpawnerImpl;
import stickman.model.level.collision.CollisionHandler;

/** The implementation class of the Level interface. */
public class LevelImpl implements Level {

  private EntitySpawner entitySpawner;
  private CollisionHandler collisionHandler;

  private List<Entity> entities;
  private Controllable hero;
  private Damageable heroLife;

  private double width;
  private double floorHeight;
  private long targetTime;
  private Instant startTime;
  private long score;
  private int numOfEntities;
  private boolean dead;
  private boolean winning;
  private boolean reload;
  private long pausedTime = 0;

  public LevelImpl(ConfigurationProvider provider) {

    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.entities = new ArrayList<>();

    LevelSettings levelData = provider.getLevelData();
    this.width = levelData.getWidth();
    this.floorHeight = levelData.getFloorHeight();
    this.targetTime = levelData.getTargetTime();
    this.dead = false;
    this.winning = false;
    this.reload = false;
  }

  public LevelImpl(Level toBeCloned) {
    this.reload = true;
    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.score = toBeCloned.getCurrentScore();
    this.width = toBeCloned.getWidth();
    this.floorHeight = toBeCloned.getFloorHeight();
    this.targetTime = toBeCloned.getTargetTime();
    this.dead = toBeCloned.isDead();
    this.winning = toBeCloned.isWinning();
    this.startTime = toBeCloned.getStartTime();
    this.entities = new ArrayList<>();
    this.numOfEntities = toBeCloned.getNumOfEntities();
    this.pausedTime = toBeCloned.getPausedTime();
    for(int i=0;i<toBeCloned.getEntities().size();i++) {
      Entity clonedEntity = toBeCloned.getEntities().get(i).clone();
      this.entities.add(clonedEntity);
      if(clonedEntity.getClass()==HeroEntity.class) {
        this.hero = (Controllable) clonedEntity;
      }
    }
  }

  @Override
  public void start(ConfigurationProvider provider) {
    if(!reload) {
      spawnHero(provider);
      spawnEntities(provider);
      populateScene(provider);
      heroLife = (Damageable) hero;
      numOfEntities = entities.size();
      startTime = Instant.now();
      score = targetTime;
    }
  }

  @Override
  public void finish(String outcome) {

    int repeat;
    if ("DEATH".equals(outcome.toUpperCase())) {
      System.out.println("\n=== YOU DIED! ===");
      System.out.println("Oops! You should be more careful next time.");
      repeat = 17;
      dead = true;
    } else {
      System.out.println("\n=== YOU WON! ===");
      System.out.println("Congratulations! You finished the level.");
      repeat = 16;
      winning = true;
    }
    Duration elapsed = Duration.between(startTime.plusSeconds(pausedTime), Instant.now());
    System.out.println("Your time: " + prettyTimeFormat(elapsed));
    System.out.println("=".repeat(repeat));
  }

  public boolean isDead() { return dead; }

  public boolean isWinning() { return winning; }

  @Override
  public List<Entity> getEntities() {
    return this.entities;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getFloorHeight() {
    return this.floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public double getHeroY() {
    return hero.getYPos();
  }

  @Override
  public Instant getStartTime() {
    return startTime;
  }

  @Override
  public boolean jump() {
    return hero.setJumping(true);
  }

  @Override
  public boolean moveLeft() {
    return hero.setMovingLeft(true);
  }

  @Override
  public boolean moveRight() {
    return hero.setMovingRight(true);
  }

  @Override
  public boolean stopMoving() {
    hero.setMovingRight(false);
    hero.setMovingLeft(false);
    return true;
  }

  @Override
  public long getCurrentScore() {
    return score;
  }

  @Override
  public int getNumOfEntities() {
    return numOfEntities;
  }

  @Override
  public long getTargetTime() {
    return targetTime;
  }

  @Override
  public void tick() {
    if(!dead || !winning) {
      entities.forEach(e -> e.move(this));
      collisionHandler.detectCollisions(this, hero);
      calculateScore();
    }
  }

  @Override
  public long getPausedTime() {
    return pausedTime;
  }

  @Override
  public int getHeroLife() {
    return heroLife.getLife();
  }


  public void calculateScore() {
    score = targetTime - Duration.between(startTime, Instant.now()).getSeconds() + pausedTime;
    if(entities.size() < numOfEntities) {
      score += (numOfEntities - entities.size())*100;
    }
  }

  @Override
  public void setPausedTime(long pausedTime) {
    this.pausedTime += pausedTime;
  }

  private void spawnHero(ConfigurationProvider provider) {
    this.hero = entitySpawner.createHero(provider, this);
    entities.add(this.hero);
  }

  private void spawnEntities(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createEntities(provider, this));
  }

  private void populateScene(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createBackgroundEntities(provider, this));
  }

  private String prettyTimeFormat(Duration duration) {
    return duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
  }
}
