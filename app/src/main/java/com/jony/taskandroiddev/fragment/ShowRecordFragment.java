package com.jony.taskandroiddev.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jony.taskandroiddev.activity.MainActivity;
import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.adapter.ItemListAdapter;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;
import java.util.List;


public class ShowRecordFragment extends Fragment implements View.OnClickListener {


    private final String TAG = this.getClass().getName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private ItemListAdapter itemListAdapter;

    private List<Record> list;


    public static ShowRecordFragment newInstance(int sectionNumber) {
        ShowRecordFragment fragment = new ShowRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowRecordFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnFragmentInteractionListener.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        list = null;
        try {
            list = HelperFactory.getHelper().getRecordDao().getAllRecord();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        // create and set adapter

        itemListAdapter = new ItemListAdapter(getActivity(), list);
        mListView = (ListView) view.findViewById(R.id.listRecords);
//        View footer = getActivity().getLayoutInflater().inflate(R.layout.footer, null);
//        mListView.addFooterView(footer);
        mListView.setAdapter(itemListAdapter);

        Button deleteSelected = (Button) view.findViewById(R.id.buttoDeleteSelected);
        deleteSelected.setOnClickListener(this);

        Button clear = (Button) view.findViewById(R.id.buttoClear);
        clear.setOnClickListener(this);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setEmptyText(String emptyText) {

        View emptyView = getActivity().findViewById(R.id.textEmpty);
        if (emptyView != null) {
            ((TextView) emptyView).setText(emptyText);
        }

    }

    @Override
    public void onClick(View v) {
        if (null != mListener) {
            mListener.onButtonClickListenerListFragment(itemListAdapter, v);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onButtonClickListenerListFragment(ItemListAdapter itemListAdapter, View v);
    }

}
