package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

import java.time.Duration;
import java.time.Instant;

/** The implementation class of the GameEngine interface. */
public class GameEngineImpl implements GameEngine {

  private ConfigurationProvider provider;
  private Level currentLevel;
  private Memento savedMemento;
  private long currentScore;
  private long accumulatedScore;
  private int currentLevelId;
  private final int levelCount;
  private Instant savedTime;

  public GameEngineImpl(String configPath) {
    provider = new ConfigurationProvider(configPath);
    currentLevelId = provider.getCurrentLevelId();
    levelCount = provider.getLevelCount();
    currentLevel = new LevelImpl(provider);
    accumulatedScore = 0;
    startLevel();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
    currentLevel.start(provider);
  }

  @Override
  public boolean jump() {
    return currentLevel.jump();
  }

  @Override
  public boolean moveLeft() {
    return currentLevel.moveLeft();
  }

  @Override
  public boolean moveRight() {
    return currentLevel.moveRight();
  }

  @Override
  public boolean stopMoving() {
    return currentLevel.stopMoving();
  }

  @Override
  public void tick() {
    if(currentLevel.isDead()) {

    }
    else if(currentLevel.isWinning()) {
      if(currentLevelId==levelCount-1) { }
      else {
        levelTransition();
      }
    } else {
      currentLevel.tick();
      currentScore = currentLevel.getCurrentScore();
    }
  }

  @Override
  public boolean isDead() {
    return currentLevel.isDead();
  }

  @Override
  public boolean isWinning() {
    return currentLevel.isWinning() && currentLevelId==levelCount-1;
  }


  public void levelTransition() {
    accumulatedScore += currentScore;

    currentLevelId++;
    provider.selectLevelToRead(currentLevelId);
    currentLevel = new LevelImpl(provider);
    startLevel();
  }

  @Override
  public int getHeroLife() {
    return currentLevel.getHeroLife();
  }

  @Override
  public long getCurrentScore() {
    if(currentScore<0) {
      return 0;
    }
    return currentScore;
  }

  @Override
  public long getTotalScore() {
    long totalScore = accumulatedScore + currentScore;
    if(totalScore < 0) {
      return 0;
    }
    return totalScore;
  }

  @Override
  public boolean isSaved() {
    return savedMemento!=null;
  }

  @Override
  public void save() {
    savedMemento = new Memento(new LevelImpl(currentLevel),accumulatedScore,currentLevelId);
    savedTime = Instant.now();
  }

  @Override
  public void load() {
    if(savedMemento == null) { return; }
    //clone level in memento
    currentLevel = new LevelImpl(savedMemento.getState().getSavedLevel());
    currentLevel.setPausedTime(Duration.between(savedTime,Instant.now()).getSeconds());
    currentLevelId = savedMemento.getState().getCurrentLevelId();
    accumulatedScore = savedMemento.getState().getAccumulatedScore();
  }
}
