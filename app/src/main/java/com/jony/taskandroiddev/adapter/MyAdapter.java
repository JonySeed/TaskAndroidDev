package com.jony.taskandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.model.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 26.06.2015.
 */
public class MyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Record> list;

    public MyAdapter(Context context,  List<Record> list) {

        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // create but not use view

        View view = convertView;
        if (view == null) {
            view = inflater.inflate( R.layout.item, parent, false);
        }

        Record record = getProduct(position);

        TextView textView = ((TextView) view.findViewById(R.id.itemText));
        textView.setText(record.getStr());
        textView.setTag(position);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.itemCheckBox);
        checkBox.setOnCheckedChangeListener(myCheckChangList);
        checkBox.setTag(position);
        checkBox.setChecked(record.isCheckBox());

        return view;
    }

    public Record getProduct(int position){
        return (Record)getItem(position);
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            getProduct((Integer) buttonView.getTag()).setCheckBox(isChecked);
        }
    };


    public ArrayList<Record> getSelectedItem() {
        ArrayList<Record> selected = new ArrayList<Record>();
        for (Record p : list) {

            if (p.isCheckBox())
                selected.add(p);
        }
        return selected;
    }
}
