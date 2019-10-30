package com.example.game.Presenter;

import com.example.game.Contract.IData;
import com.example.game.Contract.IGameManager;
import com.example.game.Contract.ILevel2;
import com.example.game.Model.GameManager;
import com.example.game.Model.Level2.Basket;
import com.example.game.Model.Level2.FallingObject;
import com.example.game.Model.Level2.GameLevel2;
import com.example.game.Model.Level2.blueObject;
import com.example.game.Model.Level2.redObject;
import com.example.game.Model.Level2.yellowObject;
import com.example.game.Model.Student;
import com.example.game.R;

import java.util.ArrayList;

public class Level2Presenter extends com.example.game.Presenter.LevelPresenter implements ILevel2.ILevel2Presenter {
    private ILevel2.ILevel2View view;
    private GameLevel2 gameLevel;
    private ArrayList<FallingObject> fallingObjects;
    private com.example.game.Model.Level2.redObject redObject;
    private com.example.game.Model.Level2.blueObject blueObject;
    private com.example.game.Model.Level2.yellowObject yellowObject;
    private int frameWidth = 1000;
    private int frameHeight = 1500;
    private int basketInt = 1455;
    private GameManager gameManager;

    public Level2Presenter(ILevel2.ILevel2View view, IData.IDataSaver saver,
                           IData.IDataLoader loader, String username){
        super(saver, loader, username);
        this.view = view;
        this.gameManager = new GameManager(saver, loader, username);
        ArrayList<FallingObject> fallingObjects = new ArrayList<>();
        redObject = new redObject((int)(Math.random()*frameWidth), -100);
        yellowObject = new yellowObject(((int)(Math.random()*frameWidth)) , -100);
        blueObject = new blueObject(((int)(Math.random() *frameWidth)), -100);
        fallingObjects.add(redObject);
        fallingObjects.add(blueObject);
        fallingObjects.add(yellowObject);
        Basket basket = new Basket(R.id.character, 0, basketInt);
        this.gameLevel = new GameLevel2(this.gameManager.getCurrentStudent(), basket, frameWidth, frameHeight, fallingObjects, this);
        this.initDisplay(view);
    }

    /** proceed to the next level
     *
     */
    @Override
    public void goToNextLevel(){
        view.goToLevel3();
    }

    @Override
    public void updateViewPosById(int id) {
        view.updateViewPosById(id);
    }

    @Override
    public void quitGame() {
        view.quitGame();
        // adding the score of the player to their hp
        gameLevel.getStudent().incrementHp(gameLevel.getScore());
        this.updateDisplay(view);
    }

    /** start the catching ball game
     *
     */
    public void startGame(){
        gameLevel.play();
    }

    /** get the score of the game
     *
     * @return int score (the score of the catching game
     */
    public int getScore(){
        return gameLevel.getScore();
    }

    /** resume playing the game after pausing the game
     *
     */
    public void resumeGame(){
        gameLevel.resumeGame();
    }

    /** Pause the game
     *
     */
    public void pauseGame(){
        gameLevel.pauseGame();
    }


    /** Move the basket to the left by 20 units
     *
     */
    public void move_left(){
        gameLevel.getBasket().move_left(20, frameWidth);
    }

    /** Move the basket to the right by 20 units
     *
     */
    public void move_right(){
        gameLevel.getBasket().move_right(20, frameWidth);
    }

    /** return student in the catching ball game
     *
     * @return student
     */
    Student getStudent(){
        return gameLevel.getStudent();
    }

    /** returns the red ball of the catching ball game
     *
     * @return redObject
     */
    public redObject getRedObj(){
        return redObject;
    }
    /** returns the blue ball of the catching ball game
     *
     * @return blueObject
     */
    public blueObject getBlueObj(){
        return blueObject;
    }
    /** returns the yellow ball of the catching ball game
     *
     * @return yellowObject
     */
    public yellowObject getYellowObj(){
        return yellowObject;
    }

    /** return the basket of the game
     *
     * @return Basket
     */
    public Basket getBasket(){
        return gameLevel.getBasket();
    }

    /** set the score of the game in the frontend
     *
     */
    @Override
    public void setScore() {
        view.setScore();
    }

    /** return the amount of seconds left before the game ends from the game
     *
     * @return long secondsRemaining
     */
    public long getSecondsRemaining(){
        return gameLevel.getSecondsRemaining();
    }

    /** set the amounts of seconds left in the frontend
     *
     */
    @Override
    public void setSecondsRemaining() {
        view.setSecondRemaining();
    }
}
