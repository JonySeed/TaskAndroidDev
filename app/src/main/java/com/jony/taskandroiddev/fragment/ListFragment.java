package com.jony.taskandroiddev.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jony.taskandroiddev.activity.MainActivity;
import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.adapter.MyAdapter;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;
import java.util.List;


public class ListFragment extends Fragment implements AbsListView.OnItemClickListener, View.OnClickListener {


    private final String TAG = this.getClass().getName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private MyAdapter myAdapter;

    private List<Record> list;

    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
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

        myAdapter = new MyAdapter(getActivity(),list);
        mListView = (ListView) view.findViewById(R.id.listRecords);
//        View footer = getActivity().getLayoutInflater().inflate(R.layout.footer, null);
//        mListView.addFooterView(footer);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);

        Button deleteSelected = (Button) view.findViewById(R.id.buttoDeleteSelected);
        deleteSelected.setOnClickListener(this);

        Button clear = (Button)view.findViewById(R.id.buttoClear);
        clear.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

        mListener = (OnFragmentInteractionListener)activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (null != mListener) {
            mListener.onFragmentInteraction(myAdapter.getProduct(position).getStr());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(String emptyText) {

        View emptyView = getActivity().findViewById(R.id.textEmpty);
        if (emptyView != null){
            ((TextView) emptyView).setText(emptyText);
        }

    }

    @Override
    public void onClick(View v) {
        if (null != mListener) {
            mListener.onButtonClickListenerListFragment(myAdapter, v);

        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
        public void onButtonClickListenerListFragment(MyAdapter myAdapter, View v);
    }

}
