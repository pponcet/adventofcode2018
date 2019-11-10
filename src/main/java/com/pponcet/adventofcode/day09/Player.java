package com.pponcet.adventofcode.day09;

public class Player {
    private int score;

    public Player() {
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int points) {
        this.score += points;
    }

    @Override
    public String toString() {
        return "Player{" +
                "score=" + score +
                '}';
    }
}
