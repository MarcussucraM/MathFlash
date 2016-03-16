package com.example.marcus.mathflash.models;

/**
 * Created by MARCUS on 3/12/2016.
 * Contains a bunch of static methods to check input for
 * various Views
 */
public class InputValidator {

    //In the MathSelectionActivity after a selection has been made
    //A settings dialog comes up and asks the user for a low number
    //high number and time per problem
    //this method checks to make sure those values are ok before moving on.
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

    //Makes sure when inputting an answer to a math problem
    //it is an actual number not an empty string or invalid number.
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
