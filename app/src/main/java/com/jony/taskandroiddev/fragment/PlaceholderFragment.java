package com.jony.taskandroiddev.fragment;

/**
 * Created by Andrey on 24.06.2015.
        */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jony.taskandroiddev.MainActivity;
import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    EditText editTextRec;
    Button buttonAddRec;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_rec, container, false);
        HelperFactory.setHelper(rootView.getContext());

        buttonAddRec = (Button)rootView.findViewById(R.id.buttoAddRec);
        buttonAddRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("================", "button click");
                Log.i("================", editTextRec.getText().toString());

                Record newRec = new Record(editTextRec.getText().toString());

                try {
                    HelperFactory.getHelper().getRecordDao().insertRecord(newRec);
                    Log.i("==========","add record");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                editTextRec.setText("");

            }
        });
        editTextRec = (EditText)rootView.findViewById(R.id.editTextRec);
        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
