package com.example.universitylife.LevelSelector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.universitylife.CourseSelector.CourseSelectorActivity;
import com.example.universitylife.DataHandler.DataHandler;
import com.example.universitylife.Game3.Game3ViewActivity1;
import com.example.universitylife.Game3.Game3ViewActivity2;
import com.example.universitylife.Game3.Game3ViewActivity3;
import com.example.universitylife.Game3.Game3ViewActivity4;
import com.example.universitylife.R;
import com.example.universitylife.Result.Game3ResultActivity;

public class Game3LevelSelectorActivity extends AppCompatActivity implements ILevelSelector.ILevelSelectorView {

    static final int GAME_TAG = 3;
    static final String GAME_NAME = "Extreme Archery";

    private String username;
    private LevelSelectorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level_selector);

        TextView courseName = findViewById(R.id.courseNameView);
        TextView currGPA = findViewById(R.id.courseGPAView);

        username = (String) getIntent().getSerializableExtra("Username");
        presenter = new LevelSelectorPresenter(this, GAME_TAG, username, new DataHandler(this));

        courseName.setText(GAME_NAME);
        currGPA.setText(presenter.getCurrGPA(3));

        configureLevelButtons();
        configureCompletionButton();
    }

    private void configureLevelButtons() {

        Button level1Button = findViewById(R.id.level1Button);
        level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateLevel(1);
            }
        });

        Button level2Button = findViewById(R.id.level2Button);
        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateLevel(2);
            }
        });

        Button level3Button = findViewById(R.id.level3button);
        level3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateLevel(3);
            }
        });

        Button bonusButton = findViewById(R.id.bonus_level_btn);
        bonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateBonus();
            }
        });

        Button backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(CourseSelectorActivity.class);
            }
        });

    }

    private void configureCompletionButton() {
        Button completionButton = findViewById(R.id.complete_btn);

        completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateCompletion();
            }
        });
    }

    /**
     * Display "message" as a toast.
     */
    @Override
    public void displayWarning(String message) {
        Toast warning = Toast.makeText(this, message, Toast.LENGTH_LONG);
        warning.show();
    }

    /**
     * Starts Game3 Level1 Activity
     */
    @Override
    public void navigateToLevel1() {
        navigateTo(Game3ViewActivity1.class);
    }


    /**
     * Starts Game3 Level2 Activity
     */
    @Override
    public void navigateToLevel2() {
        navigateTo(Game3ViewActivity2.class);
    }

    /**
     * Starts Game3 Level3 Activity
     */
    @Override
    public void navigateToLevel3() {
        navigateTo(Game3ViewActivity3.class);
    }

    @Override
    public void navigateToBonus() {
        navigateTo(Game3ViewActivity4.class);
    }

    @Override
    public void navigateToEnd() {
        navigateTo(Game3ResultActivity.class);
    }

    private void navigateTo(Class nextActivity) {
        Intent intent = new Intent(this, nextActivity);
        intent.putExtra("Username", username);
        startActivity(intent);
    }
}
