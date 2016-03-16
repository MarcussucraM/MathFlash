package com.example.marcus.mathflash.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.fileHelper.MathFlashFileHelper;
import com.example.marcus.mathflash.models.InputValidator;
import com.example.marcus.mathflash.models.MathFlashUser;
import com.example.marcus.mathflash.models.MathResources;

import java.io.IOException;
import java.util.ArrayList;

/**
 * MathProblemFragment is where all the fun happens
 * It creates set of problems to complete in a predefined timelimit
 * As the problems are completed a data object is filled out that
 * contains number of problems, number completed, number correct, and number incorrect
 * This object's state is saved at the end of the session to be
 * loaded in history later to see how you did.
 *
 */


public class MathProblemFragment extends Fragment implements View.OnClickListener {
    //for saving/loading state
    public static final String ARG_TIME_LEFT = "timeLeft";
    public static final String ARG_PROBLEM_NUM = "problem number";
    public static final String ARG_NUM_CORRECT = "number correct";
    public static final String ARG_NUM_INCORRECT = "number incorrect";
    public static final String ARG_FINISHED = "fragment finished";

    //parent activity that holds this fragment
    private OnGameEndedListener mListener;

    //Our View Handles
    private EditText editText_userAnswer;
    private TextView textView_timer;
    private TextView textView_mathProblem;
    private Button button_send;

    //amount of time left
    private long timeLeft;
    private final long SECOND = 1000;

    //Our Math Problems
    private ArrayList<String> mathProblems;
    private int currentProblem = 0;

    //The User of the Program
    MathFlashUser user;

    //The Flash Card Type
    String flashCardType;

    //A boolean stating whether finished or not - because there are two ways to end the fragment
    //On time ended and when the user runs out of problems.
    boolean finished = false;

    public MathProblemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameEndedListener) {
            mListener = (OnGameEndedListener) context;
            Log.i("MyActivity", mListener.toString() + "instantiated");
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_math_problem, container, false);
    }

    @Override
    //Get handles on all of our view objects here and set up the onClick listener
    public void onViewCreated(View view, Bundle state){
        super.onViewCreated(view, state);
        editText_userAnswer = (EditText)view.findViewById(R.id.problem_answer);
        textView_mathProblem = (TextView)view.findViewById(R.id.problem_text);
        textView_timer = (TextView)view.findViewById(R.id.count_down_text);
        button_send = (Button)view.findViewById(R.id.button_send_answer);

        //set up button_send onClickListener
        button_send.setOnClickListener(this);
    }

    @Override
    //Create a new user object on start
    //Fill its state with saved values if necessary
    //Also restore the timeField with current time.
    public void onActivityCreated(Bundle state){
        super.onActivityCreated(state);
        user = new MathFlashUser();

        if(state != null){
            timeLeft = state.getLong(ARG_TIME_LEFT);
            currentProblem = state.getInt(ARG_PROBLEM_NUM);
            user.setNumCorrect(state.getInt(ARG_NUM_CORRECT));
            user.setNumIncorrect(state.getInt(ARG_NUM_INCORRECT));
            finished = state.getBoolean(ARG_FINISHED);
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        //set up our mathProblem textView
        textView_mathProblem.setText(mathProblems.get(currentProblem));

        //start up our countdown timer, when time is up, we end the activity
        //and show the results of the flash card app.
        CountDownTimer timer = new CountDownTimer(timeLeft, SECOND) {
            //every second update our textView_timer View
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                String secondsLeft = Long.toString(timeLeft/SECOND);
                textView_timer.setText("Time Left: " + secondsLeft);
            }

            public void onFinish() {
                //uhh for some reason this only works if you do this check
                if(mListener != null && !finished){
                    finished = true;
                    setUpFinalUserValues();
                    saveUserData();
                    mListener.onGameEnded(user);
                }
            }
        }.start();
    }

    //Here we must save how much time is left
    //what problem we are currently on
    //how many problems the user has got correct/incorrect
    //and if we're finished or not, incase the user
    //decides to flip the screen after we run out of
    //problems and time is still ticking.
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_TIME_LEFT, timeLeft);
        outState.putInt(ARG_PROBLEM_NUM, currentProblem);
        outState.putInt(ARG_NUM_CORRECT, user.getNumCorrect());
        outState.putInt(ARG_NUM_INCORRECT, user.getNumIncorrect());
        outState.putBoolean(ARG_FINISHED, finished);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //Set Parameters for this fragment to work.
    //Create the math problems here
    public void setUpFragment(int low, int high, int time, String type){
        flashCardType = type;
        mathProblems = MathResources.createProblems(low, high, type);
        //time = amount of time per problem allowed
        //so multiply that by the #of problems
        //then by #milliseconds in a second.
        timeLeft = time * mathProblems.size() * SECOND;
    }

    //where pretty much all of the logic takes place in this app
    //When the user clicks the send button their answer is checked
    //to see if it's ok - if it is, its compared to the actual answer
    //of the problem, afterwards incorrect/correct is incremented
    //and the next problem is shown if correct.
    public void onClick(View v) {
        if (InputValidator.isMathProblemInputOk(editText_userAnswer.getText().toString())) {
            int actual = MathResources.getProblemAnswer(mathProblems.get(currentProblem));
            int userAns = Integer.parseInt(editText_userAnswer.getText().toString());

            if (actual == userAns) {
                //if the answer matches up with the actual answer - increment player score.
                Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
                user.incrementCorrect();
                //make sure there are more problems before you try to switch to the next one.
                if(!outOfProblems()) {
                    textView_mathProblem.setText(mathProblems.get(++currentProblem));
                }
            }
            else{
                //else increment number wrong and show the player the answer
                String incorrectMsg = "Incorrect, Actual Answer: " + Integer.valueOf(actual).toString();
                Toast.makeText(getActivity(), incorrectMsg, Toast.LENGTH_LONG).show();
                user.incrementIncorrect();
                //make sure there are more problems before you try to switch to the next one.
                if(!outOfProblems()) {
                    textView_mathProblem.setText(mathProblems.get(++currentProblem));
                }
            }
        }
        //finally - reset the edit text field
        editText_userAnswer.setText("");
    }

    //if we run out of mathProblems - end the game
    private boolean outOfProblems(){
        if (currentProblem == mathProblems.size()-1 && !finished) {
            setUpFinalUserValues();
            finished = true;
            saveUserData();
            mListener.onGameEnded(user);
            return true;
        }
        return false;
    }

    //this helper method is to finish up the user data object
    //this way we don't have to save the state of these values over and over
    //and keep reloading them into the user object on every activity/fragment restart
    private void setUpFinalUserValues(){
        user.setModePlayed(flashCardType);
        user.setNumCompleted(currentProblem + 1);
        user.setNumProblems(mathProblems.size());
    }

    private void saveUserData(){
        //save data to txt file here
        MathFlashFileHelper fileHelper = new MathFlashFileHelper(getContext());
        try {
            fileHelper.saveData(user);
        }catch(IOException e){
            Log.e("My Activity", "File was not saved..");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGameEndedListener {
        // TODO: Update argument type and name
        void onGameEnded(MathFlashUser user);
    }
}
