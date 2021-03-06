package com.example.universitylife.Game2;

import com.example.universitylife.DataHandler.IData;
import com.example.universitylife.GameManager;
import com.example.universitylife.LevelPresenter;
import com.example.universitylife.R;

import java.util.ArrayList;

public class Game2Lvl1Presenter extends LevelPresenter implements ILevel2.ILevel2PresenterLvl1 {
    private ILevel2.ILevel2View view;
    private GameLevel2Lvl1 gameLevel;
    private FallingObject redObject, yellowObject, blueObject;
    private int frameWidth = 1000;
    private int frameHeight = 1500;
    private int basketInt = 1455;
    private int clearingScore = 30;
    private boolean nextLevelUnlocked;

    public Game2Lvl1Presenter(ILevel2.ILevel2View view, String username, IData dataHandler) {
        super(username, dataHandler);
        this.view = view;
        this.gameManager = new GameManager(username, dataHandler);
        if (gameManager.getHighestLevel(2)> 1){
            nextLevelUnlocked = true;
        }
        ArrayList<FallingObject> fallingObjects = new ArrayList<>();
        FallingObjectFactory factory = new FallingObjectFactoryLvl1();
        redObject = factory.getFallingObject("red");
        yellowObject = factory.getFallingObject("yellow");
        blueObject = factory.getFallingObject("blue");
        fallingObjects.add(redObject);
        fallingObjects.add(blueObject);
        fallingObjects.add(yellowObject);
        Basket basket = new Basket(R.id.character, 0, basketInt);
        this.gameLevel = new GameLevel2Lvl1(this.gameManager.getCurrentStudent(), basket, frameWidth, frameHeight, fallingObjects, this);
    }

    /** proceed to the next level
     *
     */
    @Override
    public void goToNextLevel(){
        if (nextLevelUnlocked) {
            view.goToNextLevel();
        } else{
            view.displayMessage("Sorry, the current level has not been unlocked. You need to score at " +
                    "least 10 points to clear the level.");
        }
    }

    @Override
    public void updateViewPosById(int id) {
        view.updateViewPosById(id);
    }

    @Override
    public void quitGame() {
        // adding the score of the player to their hp
        if (gameLevel.getScore() >= clearingScore){
            view.displayMessage("Congratulation, you have cleared this level!");
            gameLevel.levelClear();
            this.nextLevelUnlocked = true;
        } else {
            view.displayMessage("Sorry, you did not clear this level!");
        }
        this.updateDisplay(view);
        gameManager.saveBeforeExit();
        view.quitGame();
    }

    /** play the catching ball game
     *
     */
    @Override
    public void play(){
        gameLevel.play();
    }

    /** get the score of the game
     *
     * @return int score (the score of the catching game
     */
    @Override
    public int getScore(){
        return gameLevel.getScore();
    }

    @Override
    public void move_left(){
        gameLevel.getBasket().move_left(40, 0);
    }

    @Override
    public void move_right(){
        gameLevel.getBasket().move_right(40, frameWidth);
    }

    @Override
    public void setScore() {
        view.setScore();
    }

    @Override
    public int getRedAppearence(){
        return redObject.getAppearence();
    }

    @Override
    public int getBlueAppearence(){
        return blueObject.getAppearence();
    }

    @Override
    public int getYellowAppearence(){
        return yellowObject.getAppearence();
    }

    @Override
    public int getBasketAppearence(){
        return gameLevel.getBasket().getAppearence();
    }

    @Override
    public int getRedX(){
        return redObject.getX_coordinate();
    }

    @Override
    public int getRedY(){
        return redObject.getY_coordinate();
    }

    @Override
    public int getBlueX(){
        return blueObject.getX_coordinate();
    }

    @Override
    public int getBlueY(){
        return blueObject.getY_coordinate();
    }

    @Override
    public int getYellowX(){
        return yellowObject.getX_coordinate();
    }

    @Override
    public int getYellowY(){
        return yellowObject.getY_coordinate();
    }

    @Override
    public int getBasketX(){
        return gameLevel.getBasket().getX();
    }

    @Override
    public int getBasketY(){
        return gameLevel.getBasket().getY();
    }

    @Override
    public void initializeGame(){
        gameLevel.initializeGame();
    }

}
