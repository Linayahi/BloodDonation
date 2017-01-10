package com.blooddonation.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import metier.Event;

public class EventActivity extends AppCompatActivity {
    private Button event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Button event = (Button) findViewById(R.id.event);

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launches the calendar then adds an event to it

                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "Don de sang");
                startActivity(intent);

                //save the event in the database

                //Event e = new Event(user_id,cal.getTimeInMillis(), lieu); //how to get the user's id ???
            }
        });

        Button aff_event = (Button) findViewById(R.id.aff_event);

        aff_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all the events from the database

            }
        });

    }
}
