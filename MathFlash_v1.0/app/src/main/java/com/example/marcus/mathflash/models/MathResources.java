package com.example.marcus.mathflash.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by MARCUS on 3/12/2016.
 * Get Math Problems Created from here
 * Also contains different selections
 * to navigate to different activities
 */
public class MathResources {
    public static final String[] selections = {"Addition", "Subtraction", "Wild", "History"};

    //creates a number of addition problems with a low end like 5 and a high end like 10
    //so 5+5, 5+6,.... to 10+10 will be created.
    public static ArrayList<String> createAdditionProblems(int low, int high){
        ArrayList<String> additionProblems = new ArrayList<String>();

        for(int i = low; i <= high; i++){
            int number1 = i;
            for(int j = low; j <= high; j++){
                int number2 = j;
                String problem = number1 + " + " + number2;
                additionProblems.add(problem);
            }
        }
        return additionProblems;
    }

    public static ArrayList<String> createSubtractionProblems(int low, int high){
        ArrayList<String> subtractionProblems = new ArrayList<String>();

        for(int i = high; i >= low; i--){
            int number1 = i;
            for(int j = low; j <= number1; j++){
                int number2 = j;
                String problem = number1 + " - " + number2;
                subtractionProblems.add(problem);
            }
        }
        return subtractionProblems;
    }

    public static ArrayList<String> createWildProblems(int low, int high, int amount){
        ArrayList<String> wildProblems = new ArrayList<String>();
        String operator = "";
        Random random = new Random();

        for(int i = 0; i < amount; i++){
            if(i%2 == 0) operator = "+";
            else operator = "-";

            int number1 = random.nextInt((high - low) + 1) + low;
            int number2 = 0;

            //since we can't have negative numbers - number1 is the highest we can go
            if(operator.equals("-")){
                number2 = random.nextInt((number1 - low) + 1) + low;
            }else{
                number2 = random.nextInt((high - low) + 1) + low;
            }
            String problem = number1 + " " + operator + " " + number2;
            wildProblems.add(problem);
        }
        Collections.shuffle(wildProblems);

        return wildProblems;
    }

    public static ArrayList<String> createProblems(int low, int high, String type){
        if(type.equals("Addition")){
            return createAdditionProblems(low, high);
        }
        else if(type.equals("Subtraction")){
            return createSubtractionProblems(low, high);
        }
        else if(type.equals("Wild")){
            return createWildProblems(low, high, 30);
        }
        else return null;
    }

    //Since our problem is basic(in the form x + y) we'll split based on spaces
    //and switch expressions based on the operator in the middle.
    public static int getProblemAnswer(String problem){
        int answer = 0;
        String[] values = problem.split(" ");
        int number1 = Integer.parseInt(values[0]);
        String operator = values[1];
        int number2 = Integer.parseInt(values[2]);

        switch(operator){
            case "+":
                answer = number1 + number2;
                break;
            case "-":
                answer = number1 - number2;
        }

        return answer;
    }
}
