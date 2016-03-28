package com.example.muge.certainwakeup;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView alarmsList;
    ArrayList<AlarmModel> alarms;
    Button btnAlarmTime;
    boolean bs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bs=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setSupportActionBar(toolbar);


        btnAlarmTime = (Button) findViewById(R.id.btnAlarmTime);
        alarms = new ArrayList<AlarmModel>();
        alarms.add(new AlarmModel());
        alarmsList = (ListView)findViewById(R.id.alarmList);
        AlarmAdapter adapter = new AlarmAdapter(MainActivity.this,R.layout.alarm_row,alarms);
        alarmsList.setAdapter(adapter);





    }

}
