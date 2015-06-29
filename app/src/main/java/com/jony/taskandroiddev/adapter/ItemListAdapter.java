package com.jony.taskandroiddev.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.model.entity.Record;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Record> list;
    OnVkShareListener onVkShareListener;

    public ItemListAdapter(Context context, List<Record> list) {

        this.context = context;
        this.list = list;

        try {
            this.onVkShareListener = (OnVkShareListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnVkShareListener.");
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        // create but not use view

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);
        }

        final Record record = getProduct(position);

        final TextView textView = ((TextView) view.findViewById(R.id.itemText));
        textView.setText(record.getStr());
        textView.setTag(position);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.itemCheckBox);
        checkBox.setOnCheckedChangeListener(myCheckChangList);
        checkBox.setTag(position);
        checkBox.setChecked(record.isCheckBox());

        ImageButton shareVk = (ImageButton) view.findViewById(R.id.shareVk);
        shareVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Integer.toString(position), textView.getText().toString());
                onVkShareListener.vkShare(record);

            }
        });

        return view;
    }

    public Record getProduct(int position) {
        return (Record) getItem(position);
    }

    public ArrayList<Record> getSelectedItem() {
        ArrayList<Record> selected = new ArrayList<Record>();
        for (Record p : list) {

            if (p.isCheckBox())
                selected.add(p);
        }
        return selected;
    }


    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            getProduct((Integer) buttonView.getTag()).setCheckBox(isChecked);
        }
    };

    public interface OnVkShareListener {
        public void vkShare(Record record);
    }
}
