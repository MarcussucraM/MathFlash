package com.example.marcus.mathflash.fileHelper;

import android.content.Context;

import com.example.marcus.mathflash.models.MathFlashUser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by MARCUS on 3/14/2016.
 */
public class MathFlashFileHelper {
    //The context of our application - used to retrieve files
    Context context;
    final String fileName = "mathFlashData.txt";

    public MathFlashFileHelper(Context context){
        this.context = context;
    }

    //This loads data from a text file in the form
    //Date::Mode::Completed::NumProblems::NumCorrect::NumIncorrect
    public ArrayList<MathFlashUser> loadData() throws IOException, ParseException {
        ArrayList<MathFlashUser> users = new ArrayList<MathFlashUser>();

        FileInputStream fi = context.openFileInput(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fi));

        DateFormat format = DateFormat.getInstance();

        //collect data and insert it into the data object
        String line;
        while((line = br.readLine()) != null){
            MathFlashUser data = new MathFlashUser();
            String[] values = line.split("::");
            data.setDateCreated(format.parse(values[0]));
            data.setModePlayed(values[1]);
            data.setNumCompleted(Integer.parseInt(values[2]));
            data.setNumProblems(Integer.parseInt(values[3]));
            data.setNumCorrect(Integer.parseInt(values[4]));
            data.setNumIncorrect(Integer.parseInt(values[5]));

            users.add(data);
        }

        return users;
    }

    //This saves data into the textfile mathFlashData.txt in the form
    //Date::Mode::Completed::NumProblems::NumCorrect::NumIncorrect
    public void saveData(MathFlashUser data) throws IOException{
        String lineToSave = "";

        FileOutputStream fo = context.openFileOutput(fileName, Context.MODE_APPEND);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(fo));

        //write data to file
        DateFormat format = DateFormat.getInstance();
        lineToSave += format.format(data.getDateCreated()).toString() + "::";
        lineToSave += data.getModePlayed() + "::";
        lineToSave += Integer.valueOf(data.getNumCompleted()).toString() + "::";
        lineToSave += Integer.valueOf(data.getNumProblems()).toString() + "::";
        lineToSave += Integer.valueOf(data.getNumCorrect()).toString() + "::";
        lineToSave += Integer.valueOf(data.getNumIncorrect()).toString() + "::";

        pw.println(lineToSave);

        pw.close();
    }
}
