package com.example.universitylife.Student;

import java.util.Arrays;

class StudentPerformance {
    private int numberOfGames = 3;
    private int numberOfLevels = 4;
    private int numberOfBonusLevels = 3;
    private int credit;
    private double gpa = 0;
    private int[] highestLevels = new int[numberOfBonusLevels];

    private double[][] scores = new double[numberOfGames][numberOfLevels - numberOfBonusLevels];
    // Bonus levels do not have a score associated with them.

    StudentPerformance(){
        Arrays.fill(highestLevels, 1);
    }

    /**
     * Records the student's performance in a game. (Used only when loading data of existing users)
     *
     * @param game         the index of the game, starting from 1
     * @param highestLevel the highest level reached by the user, starting from 1
     * @param scores       the scores the student received in each of the levels.
     */
    void setGamePerformance(int game, int highestLevel, double[] scores) {
        highestLevels[game - 1] = highestLevel;
        for (int level = 1; level <= numberOfLevels - numberOfBonusLevels; level++) {
            setLevelScore(game, level, scores[level - 1]);
        }
        updateCredit();
        updateGpa();
    }


    /**
     * Register the results of a non-bonus level. If the student has already completed this level,
     * the highest level will not be increased, but the score will be registered if it is higher
     * than the previous score.
     *
     * @param game  index of the game (>=1)
     * @param level index of the level (>=1)
     * @param score score received
     */
    void registerLevelResults(int game, int level, double score) {
        if (highestLevels[game - 1] <= level) {
            unlockNextLevel(game);
        }
        if (getLevelScore(game, level) < score) {
            setLevelScore(game, level, score);
        }
    }

    /**
     * Register the results of a bonus level.
     *
     * @param game  index of the game
     * @param level index of the level
     */
    void registerLevelResults(int game, int level) {
        if (highestLevelOf(game) <= level) {
            unlockNextLevel(game);
        }
    }


    /**
     * Calculate the total score for a game.
     *
     * @param game index of the game (>=1)
     * @return total score for this game
     */
    private double calculateGameTotal(int game) {
        double sum = 0;
        for (double s: scores[game-1]){
            sum += s;
        }
        return sum;
    }


    private void updateGpa() {
        double tempGpa = 0;
        for (int game = 1; game <= numberOfGames; game++) {
            if (gameIsCompleted(game)) {
                tempGpa += calculateGameTotal(game);
            }
        }
        if (credit != 0) {
            tempGpa = tempGpa / credit;
        }
        this.gpa = tempGpa;
    }

    private void updateCredit() {
        int credit = 0;
        for (int game = 1; game <= numberOfGames; game++) {
            if (gameIsCompleted(game)){
                credit ++;
            }
        }
        this.credit = credit;
    }

    boolean validateGraduation() {
        return credit >= numberOfGames;
    }

    int getCredit() {
        return credit;
    }


    double getGpa() {
        return gpa;
    }

    /**
     * Unlock the next level for the given game.
     *
     * @param game the index of teh game
     */
    private void unlockNextLevel(int game) {
        highestLevels[game - 1]++;
        if (highestLevelOf(game) == numberOfLevels - numberOfBonusLevels) {
            updateCredit();
            updateGpa();
            // register game as complete when the user reaches the final non-bonus level.
        }
    }


    private void setLevelScore(int game, int level, double score){
        scores[game - 1][level - 1] = score;
    }


    double getLevelScore(int game, int level) {
        return scores[game - 1][level - 1];
    }


    int highestLevelOf(int game){
        return highestLevels[game - 1];
    }

    boolean gameIsCompleted(int game) {
        return highestLevelOf(game) >= numberOfLevels - numberOfBonusLevels;
    }
}