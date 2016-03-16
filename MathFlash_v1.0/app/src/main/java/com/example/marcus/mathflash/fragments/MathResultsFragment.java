package com.example.marcus.mathflash.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.models.MathFlashUser;

/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MathResultsFragment.OnResultsFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MathResultsFragment extends DialogFragment implements View.OnClickListener {
    private OnResultsFragmentInteractionListener mListener;

    //View handles
    TextView text_modePlayed;
    TextView text_numCompleted;
    TextView text_numCorrect;
    TextView text_numIncorrect;
    Button button_ok;

    public MathResultsFragment() {
        // Required empty public constructor
    }

    public static MathResultsFragment newInstance(MathFlashUser user){
        MathResultsFragment resultsFragment = new MathResultsFragment();
        Bundle args = new Bundle();
        DateFormat dateFormat = DateFormat.getInstance();
        args.putString("dateCreated", dateFormat.format(user.getDateCreated()));
        args.putString("mathMode", user.getModePlayed());
        args.putInt("numCorrect", user.getNumCorrect());
        args.putInt("numIncorrect", user.getNumIncorrect());
        args.putInt("numCompleted", user.getNumCompleted());
        args.putInt("numProblems", user.getNumProblems());

        resultsFragment.setArguments(args);
        return resultsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResultsFragmentInteractionListener) {
            mListener = (OnResultsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnResultsFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_math_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle state){
        //get handles on all of our views here
        text_modePlayed = (TextView)view.findViewById(R.id.dialog_mode_text);
        text_numCompleted = (TextView)view.findViewById(R.id.dialog_completed_text);
        text_numCorrect = (TextView)view.findViewById(R.id.dialog_correct_text);
        text_numIncorrect = (TextView)view.findViewById(R.id.dialog_incorrect_text);
        button_ok = (Button)view.findViewById(R.id.dialog_results_button);

        button_ok.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        //get our arguments and set up all of the views.
        Bundle args = getArguments();

        //create our string for the numCompleted text field.
        String numCompleted = "Completed: ";
        numCompleted += Integer.valueOf(args.getInt("numCompleted")).toString() + "/";
        numCompleted += Integer.valueOf(args.getInt("numProblems")).toString();

        //set up the views based on these arguments
        text_modePlayed.setText("Mode: " + args.getString("mathMode"));
        text_numCompleted.setText(numCompleted);
        text_numCorrect.setText("Correct: " + Integer.valueOf(args.getInt("numCorrect")).toString());
        text_numIncorrect.setText("Incorrect: " + Integer.valueOf(args.getInt("numIncorrect")).toString());
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        mListener.onResultsFragmentInteraction();
        this.dismiss();
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
    public interface OnResultsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onResultsFragmentInteraction();
    }
}
