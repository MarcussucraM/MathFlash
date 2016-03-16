package com.example.marcus.mathflash.models;

/**
 * Created by MARCUS on 3/12/2016.
 * Contains a bunch of static methods to check input for
 * various Views
 */
public class InputValidator {
    public static boolean isDialogInputOk(String low, String high, String time){
        //we can't have any that are empty
        if(low.equals("") || high.equals("") || time.equals("")) return false;

        //make sure these values are numeric
        int lowInt, highInt, timeInt;
        try{
            lowInt = Integer.parseInt(low);
            highInt = Integer.parseInt(high);
            timeInt = Integer.parseInt(time);
        }catch(NumberFormatException e){
            return false;
        }

        //make sure values are within bounds also.
        if(lowInt > highInt) return false;
        if(lowInt > 99 || lowInt < 0) return false;
        if(highInt > 99 || highInt < 0) return false;
        if(timeInt < 1 || timeInt > 10) return false;

        //if all of these tests pass then input is ok
        return true;
    }

    //Makes sure the user didn't input something retarded
    public static boolean isMathProblemInputOk(String answer){
        if(answer.equals("")) return false;
        try{
            Integer.parseInt(answer);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    //called when user presses send and input is correct on their end
    public static boolean isAnswerCorrect(int actual, String userInput){
        return actual == Integer.parseInt(userInput);
    }
}
