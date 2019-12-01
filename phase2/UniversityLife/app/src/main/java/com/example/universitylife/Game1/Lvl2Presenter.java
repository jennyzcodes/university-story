package com.example.universitylife.Game1;

import com.example.universitylife.LevelPresenter;

public class Lvl2Presenter extends LevelPresenter {
    private ILevel1.ILevel1View2 view;
    private Level2 gameLevel;
    private long secondsRemaining;
    private boolean nextLevelUnlocked = false;

    public Lvl2Presenter(ILevel1.ILevel1View2 view, String username){
        super(username);
        this.view = view;
        this.gameLevel = new Level2(gameManager.getCurrentStudent());
        if (gameManager.getHighestLevel(1) > 2){
            nextLevelUnlocked = true;
        }
    }

    /**
     * Starts the math game
     */
    public void startGame() {
        this.view.startTimer(60000);
        newQuestion();
    }

    /**
     * Resumes the game and timer
     */
    public void resumeGame() {
        view.startTimer(secondsRemaining * 1000);
    }

    /**
     * Gets the number of questions correctly answered from the game level
     *
     * @return an integer referring to the number of correct answers
     */
    public int getCorrectScore() {
        return this.gameLevel.getNumCorrectAnswers();
    }

    /**
     * Asks the view to display the new question
     */
    private void newQuestion() {
        this.view.displayQuestion(gameLevel.createQuestion());
    }

    /**
     * Evaluates the user's answer to the question. If it is correct, update the correct answer score
     * and if it is incorrect, update the incorrect answer score. If the input is invalid, display
     * a warning
     *
     * @param answerReceived the answer input from the user
     */
    public void evaluateAnswer(String answerReceived) {
        try {
            int ans = Integer.parseInt(answerReceived);
            boolean correct = this.gameLevel.evaluateAnswer(ans);
            if (!correct) {
                view.displayWarning("Wrong Answer!");
            }
            view.displayCorrectScore(gameLevel.getNumCorrectAnswers());
            view.displayIncorrectScore(gameLevel.getNumIncorrectAnswers());
            gameLevel.updateScore(gameLevel.getNumCorrectAnswers());
            view.displayScore(gameLevel.getTotalScore());
            view.resetHintDisplay();
            newQuestion();
        } catch (NumberFormatException e) {
            view.displayWarning("Invalid!");
        }
    }

    /**
     * Checks if the user has successfully completed the level or not. If successful, unlock the
     * next level.
     */
    public void levelComplete() {
        int clearingScore = gameLevel.getClearingScore();
        if (gameLevel.getNumCorrectAnswers() < clearingScore) {
            view.displayWarning("Play again to unlock the next level!");
        } else {
            view.displayWarning("Congratulations, you have cleared this level!");
            nextLevelUnlocked = true;
            gameLevel.levelPass();
        }
        gameManager.saveBeforeExit();
        updateDisplay(view);
        view.endGame();
    }

    /**
     * Ask the view to display the number of seconds left in the game.
     *
     * @param millisUntilFinished number of milliseconds left in the game.
     */
    public void tick(long millisUntilFinished) {
        secondsRemaining = millisUntilFinished / 1000;
        view.setSecondsRemaining(secondsRemaining);
    }

    /**
     * Check if the Level 3 is unlocked or not. If unlocked, go to Level 3. Else, display a warning.
     */
    public void validateLevel3() {
        if (nextLevelUnlocked) {
            view.goToNextLevel();
        } else {
            view.displayWarning("Sorry, the next level has not been unlocked");
        }
    }

    public void getHint(){
        int correctAnswer = gameLevel.getCorrectAnswer();
        int lowerBound = correctAnswer - (int) (Math.random() * 6);
        int upperBound = correctAnswer + (int) (Math.random() * 6);
        view.displayHint(lowerBound, upperBound);
    }
}
