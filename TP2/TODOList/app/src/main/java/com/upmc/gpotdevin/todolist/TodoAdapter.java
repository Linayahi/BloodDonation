package com.upmc.gpotdevin.todolist;

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
 * Created by gpotdevin on 29/11/2016.
 */

public class TodoAdapter extends ArrayAdapter<TODO>{
    private final int resource;

    public TodoAdapter(Context context, int resource, List<TODO> objects) {
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

        final TODO item = getItem(position);

        TextView tv = (TextView)convertView.findViewById(R.id.textView);
        tv.setText(item.text);

        final CheckBox cb = (CheckBox)convertView.findViewById(R.id.checkBox);
        cb.setChecked(item.isChecked);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.isChecked = !item.isChecked;
                cb.setChecked(item.isChecked);
                notifyDataSetChanged();
            }
        });



        ImageView button = (ImageView)convertView.findViewById(R.id.deleteBtn);
        button.setTag(item);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(getItem(position));
//            }
//        });

        return convertView;
    }
}
