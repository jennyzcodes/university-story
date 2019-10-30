package com.example.game.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.Contract.ILevel2;
import com.example.game.DataHandler.DataLoader;
import com.example.game.DataHandler.DataSaver;
import com.example.game.Presenter.Level2Presenter;
import com.example.game.R;

public class Lvl2GameActivity extends AppCompatActivity implements ILevel2.ILevel2View {
    private Level2Presenter level2Presenter;
    private TextView credit_tv, gpa_tv, hp_tv;
    private ImageView red, blue, yellow;
    private LinearLayout resultBox;
    private boolean nextLevelUnlocked;
    private boolean pauseGame = false;
    private int clearingScore = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lvl_2);
        String username = (String) getIntent().getSerializableExtra("UserName");
        // finding textviews, imageviews and buttons in from the xml file
        credit_tv = findViewById(R.id.credit);
        gpa_tv = findViewById(R.id.gpa);
        hp_tv = findViewById(R.id.hp);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        yellow = findViewById(R.id.yellow);
        initiateImageView();
        resultBox = findViewById(R.id.resultBox);
        red.setVisibility(View.INVISIBLE);
        blue.setVisibility(View.INVISIBLE);
        yellow.setVisibility(View.INVISIBLE);
        level2Presenter = new Level2Presenter(this, new DataSaver(), new DataLoader(), username);
    }

    /** set image resources for all the imageview in the xml file
     *
     */
    private void initiateImageView() {
        red.setImageResource(level2Presenter.getRedObj().getAppearence());
        blue.setImageResource(level2Presenter.getBlueObj().getAppearence());
        yellow.setImageResource(level2Presenter.getYellowObj().getAppearence());
        ImageView basket = findViewById(R.id.character);
        basket.setImageResource(level2Presenter.getBasket().getAppearence());
    }


    @Override
    public void goToLevel3() {
        if (!nextLevelUnlocked){
            Toast.makeText(this,
                    "Sorry, the current level has not been unlocked", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, Lvl3StartActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void updateViewPosById(int id) {
        ImageView image = findViewById(id);
        switch (id) {
            case R.id.red:
                image.setX(level2Presenter.getRedObj().getX_coordinate());
                image.setY(level2Presenter.getRedObj().getY_coordinate());
                break;
            case R.id.blue:
                image.setX(level2Presenter.getBlueObj().getX_coordinate());
                image.setY(level2Presenter.getBlueObj().getY_coordinate());
                break;
            case R.id.yellow:
                image.setX(level2Presenter.getYellowObj().getX_coordinate());
                image.setY(level2Presenter.getYellowObj().getY_coordinate());
                break;
            case R.id.character:
                image.setX(level2Presenter.getBasket().getX());
                image.setY(level2Presenter.getBasket().getY());
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void quitGame() {
        setScore();
        TextView textView = findViewById(R.id.final_score);
        textView.setText(Integer.toString(level2Presenter.getScore()));
        resultBox.setVisibility(View.VISIBLE);
        if (level2Presenter.getScore() >= clearingScore){
            Toast.makeText(this, "Congratulation, you have cleared this level!",
                    Toast.LENGTH_SHORT).show();
            nextLevelUnlocked = true;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setScore() {
        TextView textView = findViewById(R.id.score);
        textView.setText(Integer.toString(level2Presenter.getScore()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setSecondRemaining() {
        TextView seconds = findViewById(R.id.secondRemaining);
        seconds.setText(Long.toString(level2Presenter.getSecondsRemaining()));
    }

    @Override
    public void displayProfilePic(int num) {

    }

    @Override
    public void displayName(String name) {

    }

    /**
     * displays the gpa of the student
     *
     * @param gpa: how well the player
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void displayGPA(double gpa) {
        gpa_tv.setText(Double.toString(gpa));
    }


    /**
     * displays the hp of the player
     *
     * @param hp: the hp of the player
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void displayHP(double hp) {
        hp_tv.setText(Double.toString(hp));
    }

    /**
     * displays the credit of the player
     *
     * @param credit: the credit of the player
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void displayCredit(int credit) {
        credit_tv.setText(Double.toString(credit));
    }

    /**
     * moves the basket(the character) to the right
     *
     * @param view: the character imageview in the xml file
     */
    public void move_right(View view) {
        level2Presenter.move_right();
        updateViewPosById(R.id.character);
    }

    /**
     * moves the basket(the character) to the left
     *
     * @param view: the characater imageview in the xml file
     */
    public void move_left(View view) {
        level2Presenter.move_left();
        updateViewPosById(R.id.character);
    }

    /**
     * starts the catching ball game
     *
     * @param view the start button in the linear layout
     */
    public void start_game(View view) {
        resultBox.setVisibility(View.INVISIBLE);
        red.setVisibility(View.VISIBLE);
        blue.setVisibility(View.VISIBLE);
        yellow.setVisibility(View.VISIBLE);
        level2Presenter.startGame();
    }

    /**
     * pause or resume the game
     *
     * @param view: the pause button in the xml file
     */
    public void pauseOrResume_game(View view) {
        if (pauseGame) {
            level2Presenter.resumeGame();
        } else {
            level2Presenter.pauseGame();
        }
        pauseGame = !pauseGame;
    }

}