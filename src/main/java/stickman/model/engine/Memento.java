package stickman.model.engine;

import stickman.model.level.Level;

public class Memento {
    class State {
        private Level savedLevel;
        private long accumulatedScore;
        private int currentLevelId;
        public State(Level savedLevel,long accumulatedScore, int currentLevelId) {
            this.savedLevel = savedLevel;
            this.accumulatedScore = accumulatedScore;
            this.currentLevelId = currentLevelId;
        }
        public Level getSavedLevel() {
            return savedLevel;
        }
        public int getCurrentLevelId() {
            return currentLevelId;
        }
        public long getAccumulatedScore() {
            return accumulatedScore;
        }
    }
    private State state;

    public Memento(Level savedLevel,long accumulatedScore, int currentLevelId) {
        this.state = new State(savedLevel,accumulatedScore,currentLevelId);
    }
    public State getState() {
        return state;
    }
}
