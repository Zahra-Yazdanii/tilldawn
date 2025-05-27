package com.tilldawn.model;

public class GameRecord {
    private final String heroName;
    private final int score;
    private final boolean finished;

    public GameRecord(String heroName, int score, boolean finished) {
        this.heroName = heroName;
        this.score = score;
        this.finished = finished;
    }

    public String getHeroName() {
        return heroName;
    }

    public int getScore() {
        return score;
    }

    public boolean isFinished() {
        return finished;
    }
}
