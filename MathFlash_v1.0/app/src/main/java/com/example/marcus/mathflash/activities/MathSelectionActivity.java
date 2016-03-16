package com.example.marcus.mathflash.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.fragments.MathSelectionFragment;
import com.example.marcus.mathflash.fragments.SettingsFragment;

public class MathSelectionActivity extends AppCompatActivity implements MathSelectionFragment.OnSelection, SettingsFragment.OnDialogFinishedListener{
    public static final String EXTRA_LOWINT = "com.example.marcus.mathflash.lowint";
    public static final String EXTRA_HIGHINT = "com.example.marcus.mathflash.highint";
    public static final String EXTRA_TIMEINT = "com.example.marcus.mathflash.timeint";
    public static final String EXTRA_GAMETYPE = "com.example.marcus.mathflash.gametype";

    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_selection);
    }

    @Override
    //Receives a string from fragment - tells which activity to nav to next.
    public void onSelectItem(String selectionName) {
        //if it's either one of these we have to handle things differently
        //otherwise show the settings dialog and go.
        if(selectionName.equals("History")){
            //Toast.makeText(this, "Not Yet Created In Beta",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MathHistoryActivity.class);
            startActivity(intent);
        }
        else {
            gameType = selectionName;
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.show(getSupportFragmentManager(),"fragment_settings");
        }

    }

    @Override
    //Recieves the game settings
    public void onDialogFinished(int low, int high, int time) {
        Intent intent = new Intent(this, MathProblemActivity.class);
        intent.putExtra(EXTRA_LOWINT, low);
        intent.putExtra(EXTRA_HIGHINT, high);
        intent.putExtra(EXTRA_TIMEINT, time);
        intent.putExtra(EXTRA_GAMETYPE, gameType);

        startActivity(intent);
    }
}
