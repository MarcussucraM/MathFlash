package com.example.marcus.mathflash.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.fileHelper.MathFlashFileHelper;
import com.example.marcus.mathflash.models.MathFlashUser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

//Purpose of this activity is to show the user history from past results playing this app.
public class MathHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_history);
    }


    //Load data from text file, append it to layout - which is a TextView
    public void onStart(){
        super.onStart();

        MathFlashFileHelper fileHelper = new MathFlashFileHelper(this);
        try {
            ArrayList<MathFlashUser> data = fileHelper.loadData();
            TextView text = (TextView)findViewById(R.id.math_history_text);

            for(int i = 0; i < data.size(); i++){
                String pos = Integer.valueOf(i+1).toString() + ".) ";
                text.append(pos + data.get(i).toString());
            }

        }catch(IOException e){
            Log.e("MyActivity", "Data was not loaded...");
        }catch(ParseException p){
            Log.e("MyActivity", "Date was not parsed");
        }
    }
}
