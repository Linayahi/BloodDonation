package com.upmc.gpotdevin.todolist;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TODOActivity extends AppCompatActivity {

    private List<TODO> data;
    private TodoAdapter adapter;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        ListView lv = (ListView)findViewById(R.id.listView);

        data = new ArrayList<TODO>();
        adapter = new TodoAdapter(this, R.layout.list_item,  data);
        lv.setAdapter(adapter);

        button = (Button)findViewById(R.id.btn);

        final TextView countView = (TextView)findViewById(R.id.count);

        editText = (EditText)findViewById(R.id.et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                button.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                data.remove(position);
//                adapter.notifyDataSetChanged();
//            }
//        });


        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                int count = 0;
                for (TODO todo: data) {
                    if (!todo.isChecked) count++;
                }
                countView.setText(count + " items restants");
            }
        });

    }

    public void addItem(View v){
        String item = editText.getText().toString();
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
        adapter.add(new TODO(item, false));

        editText.setText("");
    }

    public void removeItem(View v){
        TODO item = (TODO)v.getTag();
        adapter.remove(item);
    }

}
