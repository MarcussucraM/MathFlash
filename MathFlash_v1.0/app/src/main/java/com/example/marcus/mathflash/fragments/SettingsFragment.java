package com.example.marcus.mathflash.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcus.mathflash.R;
import com.example.marcus.mathflash.models.InputValidator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnDialogFinishedListener} interface
 * to handle interaction events.
 */
public class SettingsFragment extends DialogFragment implements View.OnClickListener {
    //parent activity
    private OnDialogFinishedListener mListener;

    //Handles for View objects
    EditText editText_highValue;
    EditText editText_lowValue;
    EditText editText_timeValue;
    Button dialog_button;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle state){
        super.onViewCreated(view, state);

        //get handles on all of our views
        editText_highValue = (EditText)view.findViewById(R.id.dialog_highValue);
        editText_lowValue = (EditText)view.findViewById(R.id.dialog_lowValue);
        editText_timeValue = (EditText)view.findViewById(R.id.dialog_timeValue);
        dialog_button = (Button)view.findViewById(R.id.dialog_button);

        //set the listener for button
        dialog_button.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogFinishedListener) {
            mListener = (OnDialogFinishedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view){
        //check to make sure values aren't fucked here
        String low = editText_lowValue.getText().toString();
        String high = editText_highValue.getText().toString();
        String time = editText_timeValue.getText().toString();

        //if ok - then send to activity
        if (InputValidator.isDialogInputOk(low, high, time)){
            int lowInt = Integer.parseInt(low);
            int highInt = Integer.parseInt(high);
            int timeInt = Integer.parseInt(time);
            mListener.onDialogFinished(lowInt, highInt, timeInt);
            this.dismiss();
        }
        //otherwise let the user know
        else{
            String errorText = "Input Error\n";
            errorText += "Low/High Values Must Be 0 - 99\n";
            errorText += "Low must be lower than High\n";
            errorText += "Time Per Problem Must Be 1 - 10";
            Toast.makeText(getActivity(), errorText, Toast.LENGTH_LONG).show();
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
    public interface OnDialogFinishedListener {
        void onDialogFinished(int low, int high, int time);
    }
}
