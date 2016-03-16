package com.example.marcus.mathflash.models;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by MARCUS on 3/12/2016.
 * An object that contains result data
 * from our MathFlash app
 * This data will be stored in a file
 * where it will be loaded in the historyActivity
 */
public class MathFlashUser {
    private String modePlayed;
    private int numCorrect;
    private int numIncorrect;
    private int numProblems;
    private int numCompleted;
    private Date dateCreated;


    public MathFlashUser(){
        dateCreated = new Date();
        numCorrect = 0;
        numIncorrect = 0;
        modePlayed = "";
        numProblems = 0;
        numCompleted = 0;
    }

    public void incrementCorrect(){ ++numCorrect; }

    public void incrementIncorrect(){ ++numIncorrect; }

    public String getModePlayed(){ return modePlayed; }

    public int getNumCorrect(){ return numCorrect; }

    public int getNumIncorrect(){ return numIncorrect; }

    public Date getDateCreated(){ return dateCreated; }

    public int getNumProblems(){ return numProblems; }

    public int getNumCompleted() { return numCompleted; }

    public void setModePlayed(String mode){ modePlayed = mode; }

    public void setNumCorrect(int correct){ numCorrect = correct; }

    public void setNumIncorrect(int incorrect) { numIncorrect = incorrect; }

    public void setNumCompleted(int completed) { numCompleted = completed; }

    public void setNumProblems(int numProblems){ this.numProblems = numProblems; }

    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }

    public String toString(){
        String resultString = "";
        DateFormat format = DateFormat.getInstance();

        resultString += "Date Created: " + format.format(dateCreated) + "\n";
        resultString += "Mode Played: " + modePlayed + "\n";
        resultString += "Completed: " + numCompleted + "/" + numProblems + "\n";
        resultString += "Correct: " + numCorrect + "\n";
        resultString += "Incorrect: " + numIncorrect + "\n\n";

        return resultString;
    }

}
