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

public class MathProblemActivity extends AppCompatActivity implements MathProblemFragment.OnGameEndedListener, MathResultsFragment.OnResultsFragmentInteractionListener{

    @Override
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
    public void onGameEnded(MathFlashUser user) {
        //get all of the results - the toast is kind of useless soon
        /*String results = "";
        results += "Date Created: " + user.getDateCreated().toString() + "\n";
        results += "Mode: " + user.getModePlayed() + "\n";
        results += "Number Correct: " + user.getNumCorrect() + "\n";
        results += "Number Incorrect: " + user.getNumIncorrect() + "\n";
        Toast.makeText(this, results, Toast.LENGTH_SHORT).show();*/

        //switch to results page!!
        MathResultsFragment resultsFragment = MathResultsFragment.newInstance(user);
        resultsFragment.show(getSupportFragmentManager(), "resultsDialog");
    }

    @Override
    public void onResultsFragmentInteraction() {
        //switch back to selection page!!
        Intent intent = new Intent(this, MathSelectionActivity.class);
        startActivity(intent);
    }
}
