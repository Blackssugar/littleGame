package stickman.model.engine;

public interface GameSubject {
    int getHeroLife();
    long getCurrentScore();
    long getTotalScore();
    boolean isSaved();
    boolean isDead();
    boolean isWinning();
}
