package com.blooddonation.blooddonation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ouiza on 11/01/17.
 */

public class QuizAdapter extends ArrayAdapter<Data> {
    private final int resource; //???

    public QuizAdapter(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        final Data item = getItem(position);

//        final CheckBox cb = (CheckBox)convertView.findViewById(R.id.r_1_1);
//        cb.setChecked(item.isChecked);
//        cb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                item.isChecked = !item.isChecked;
//                cb.setChecked(item.isChecked);
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }
}
