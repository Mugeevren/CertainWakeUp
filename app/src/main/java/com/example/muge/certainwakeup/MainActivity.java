package com.example.muge.certainwakeup;

import android.app.ExpandableListActivity;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private ArrayList<AlarmModel> alarms=new ArrayList<AlarmModel>();
        private ListView alarmList;
        private Button btnAlarmTime;
        //private MySharedPreferences sp;
        private AlarmDbHelper alarmdb;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //sp = new MySharedPreferences(MainActivity.this);
            //sp.editor.putInt("alarmId",-1);
            //sp.editor.commit();

            AlarmModel alarm=new AlarmModel();
            insertDefaultAlarm(alarm);
            getAlarms();//sql lite ile kayıtlı attached alarmları alarms listesine atar

            //adaptor işlemleri

            alarmList=(ListView)findViewById(R.id.alarmList);
            AlarmAdapter adapter=new AlarmAdapter(MainActivity.this,R.layout.alarm_row,alarms);
            alarmList.setAdapter(adapter);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        }

    private void insertDefaultAlarm(AlarmModel alarm)
    {
        alarmdb=new AlarmDbHelper(MainActivity.this);
        boolean result=alarmdb.InsertAttachedAlarm(alarm.getId(),alarm.getHour(),alarm.getMinute()
                ,alarm.isMonday(),alarm.isTuesday(),alarm.isWednesday(),alarm.isThursday(),alarm.isFriday(),
                alarm.isSaturday(),alarm.isSunday(),alarm.isActive());

    }

    private void getAlarms()
    {
        alarmdb=new AlarmDbHelper(MainActivity.this);
        alarms=alarmdb.GetAttachedAlarm();
    }

    private void UpdateDummyData(AlarmModel alarm)
    {
        alarmdb=new AlarmDbHelper(MainActivity.this);
        boolean result=alarmdb.InsertAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());
        if(result==true)
        {
            Toast.makeText(MainActivity.this, "Güncelleme başarıyla eklendi.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Güncelleme Ekleme Başarısız",Toast.LENGTH_SHORT).show();
        }

    }

}


