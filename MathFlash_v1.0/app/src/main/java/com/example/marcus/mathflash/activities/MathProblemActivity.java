package com.example.marcus.mathflash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.fragments.MathProblemFragment;
import com.example.marcus.mathflash.fragments.MathResultsFragment;
import com.example.marcus.mathflash.models.MathFlashUser;

//Purpose of this Activity is to display the actual game.
//All of the logic is in MathProblemFragment
public class MathProblemActivity extends AppCompatActivity implements MathProblemFragment.OnGameEndedListener, MathResultsFragment.OnResultsFragmentInteractionListener{

    @Override
    //This Activity starts after a selection has been made
    //The intent that started it has a bundle with
    //lowest value the problems could have (ex: 1)
    //highest value the problems could have (ex: 10)
    //The time per problem (1 - 10 seconds)
    //The mode of play(addition problems, subtraction problems or randomized problems)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_problem);

        Intent intent = getIntent();
        int lowVal = intent.getIntExtra(MathSelectionActivity.EXTRA_LOWINT, 0);
        int highVal = intent.getIntExtra(MathSelectionActivity.EXTRA_HIGHINT, 10);
        int timeVal = intent.getIntExtra(MathSelectionActivity.EXTRA_TIMEINT, 30000);
        String type = intent.getStringExtra(MathSelectionActivity.EXTRA_GAMETYPE);

        MathProblemFragment mathProblemFragment = (MathProblemFragment)getSupportFragmentManager()
                .findFragmentById(R.id.math_addition_fragmentContainer);

        mathProblemFragment.setUpFragment(lowVal, highVal, timeVal, type);
    }

    @Override
    //When time runs out or there are no more problems this callback method is run
    //MathFlashUser is an object containing data collected while the user played the game.
    //This object is passed to a dialog fragment which shows the results of that data collected.
    public void onGameEnded(MathFlashUser user) {
        //switch to results page!!
        MathResultsFragment resultsFragment = MathResultsFragment.newInstance(user);
        resultsFragment.show(getSupportFragmentManager(), "resultsDialog");
    }

    @Override
    //When done with the dialog this is called
    //Switches back to the main page.
    public void onResultsFragmentInteraction() {
        //switch back to selection page!!
        Intent intent = new Intent(this, MathSelectionActivity.class);
        startActivity(intent);
    }
}
