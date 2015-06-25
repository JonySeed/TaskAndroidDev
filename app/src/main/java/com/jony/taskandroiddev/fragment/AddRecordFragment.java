package com.jony.taskandroiddev.fragment;

/**
 * Created by Andrey on 24.06.2015.
        */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.jony.taskandroiddev.MainActivity;
import com.jony.taskandroiddev.R;


public class AddRecordFragment extends Fragment implements View.OnClickListener{


    private OnButtonClickListener mButtonListener;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public static AddRecordFragment newInstance(int sectionNumber) {
        AddRecordFragment fragment = new AddRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AddRecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_rec, container, false);

        Button buttonAddRec = (Button)rootView.findViewById(R.id.buttoAddRec);
        buttonAddRec.setOnClickListener(this);

        Button buttonClear = (Button)rootView.findViewById(R.id.buttoClear);
        buttonClear.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

        mButtonListener = (OnButtonClickListener)activity;
    }

    @Override
    public void onClick(View v) {
        if (mButtonListener != null){
            mButtonListener.onButtonClickListener(v);
        }

    }

    public interface OnButtonClickListener{
        public void onButtonClickListener(View v);
    }
}


