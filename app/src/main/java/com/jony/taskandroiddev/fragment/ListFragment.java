package com.jony.taskandroiddev.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jony.taskandroiddev.MainActivity;
import com.jony.taskandroiddev.R;

import com.jony.taskandroiddev.fragment.component.ModelList;
import com.jony.taskandroiddev.fragment.component.ItemList;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;
import java.util.List;


public class ListFragment extends Fragment implements AbsListView.OnItemClickListener {


    private final String TAG = this.getClass().getName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;
    private ListAdapter mAdapter;


    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<ItemList>(getActivity(),
                R.layout.item, R.id.itemText, ModelList.ITEMS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        List<Record> list = null;
        try {
            list = HelperFactory.getHelper().getRecordDao().getAllRecord();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ModelList.clearList();

        if (list != null) {
            for (Record record : list) {
                ModelList.addItem(new ItemList(Integer.toString(record.getId()), record.getStr()));
            }
        }

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Set adapter
        mListView = (AbsListView) view.findViewById(R.id.listRecords);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

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
            mListener.onFragmentInteraction(ModelList.ITEMS.get(position).content);
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
