package com.example.muge.certainwakeup;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlarmAdapter.CustomButtonListener {

        private ArrayList<AlarmModel> alarms=new ArrayList<AlarmModel>();
        private ListView alarmList;
        private ImageButton addAlarm;
        //private MySharedPreferences sp;
        private AlarmDbHelper alarmdb = new AlarmDbHelper(MainActivity.this);
        TimePickerDialog tpd;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //sp = new MySharedPreferences(MainActivity.this);
            //sp.editor.putInt("alarmId",-1);
            //sp.editor.commit();

            addAlarm = (ImageButton)findViewById(R.id.addAlarm);
            addAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //yani aktivity'e yönlendirme için intent


                }
            });


            alarmdb.deleteAll();
            AlarmModel alarm1 = new AlarmModel(2,9,30,true,false,false,false,false,false,false,true);
            insertDefaultAlarm(alarm1);
            AlarmModel alarm2 = new AlarmModel(3,11,30,true,false,false,false,false,false,false,true);
            AlarmModel alarm=new AlarmModel();
            insertDefaultAlarm(alarm);
            insertDefaultAlarm(alarm2);
            AlarmModel alarm3 = new AlarmModel(4,21,30,true,false,false,false,false,false,false,true);
            AlarmModel alarm4=new AlarmModel(5,22,30,true,true,false,false,false,false,false,true);
            insertDefaultAlarm(alarm3);
            insertDefaultAlarm(alarm4);
            AlarmModel alarm5 = new AlarmModel(6,23,30,true,false,false,false,false,false,false,true);
            AlarmModel alarm6=new AlarmModel(7,00,30,true,true,false,false,false,false,false,true);
            insertDefaultAlarm(alarm5);
            insertDefaultAlarm(alarm6);

            /*
            alarmdb.deleteAll();
            AlarmModel alarm1 = new AlarmModel(2,9,30,true,false,false,false,false,false,false,true);
            AlarmModel alarm=new AlarmModel();
            insertDefaultAlarm(alarm);
            insertDefaultAlarm(alarm1);
            */

            getAlarms();//sql lite ile kayıtlı attached alarmları alarms listesine atar

            //adaptor işlemleri
            alarmList=(ListView)findViewById(R.id.alarmList);
            AlarmAdapter adapter=new AlarmAdapter(MainActivity.this,R.layout.alarm_row,alarms);
            adapter.setDeleteButtonClickListener(MainActivity.this);
            adapter.setTimeSetButtonClickListener(MainActivity.this);
            adapter.setExpandToggleButtonClickListener(MainActivity.this);
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
        boolean result=alarmdb.InsertAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());

    }

    private void getAlarms()
    {
        alarmdb=new AlarmDbHelper(MainActivity.this);
        alarmdb.GetAttachedAlarm(alarms);
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

    private void updateAlarm(AlarmModel alarm)
    {
        AlarmDbHelper alarmdb=new AlarmDbHelper(this);
        boolean result=alarmdb.UpdateAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());
        if(result==false)
            Toast.makeText(this,"Güncelleme Ekleme Başarısız",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDeleteButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {


    }

    @Override
    public void onTimeSetButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {

        //timepickerHandle(position);
        tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override//istenilen alarm saati bilgileri buraya geliyor.
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                alarm.setHour(hourOfDay);
                alarm.setMinute(minute);
                updateAlarm(alarm);
                vh.btnTime.setText(alarm.toString());
                //bir sonraki sıradakinin butonunun textini değiştiriyor nedenini bulamadım

            }
        }, alarm.getHour(), alarm.getMinute(), true);

        tpd.setTitle("Saat Seçiniz");
        tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tpd.dismiss();
            }
        });
        tpd.show();

    }

    @Override
    public void onExpandToggleButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {
        if (vh.expandActivate.isChecked()) {
            vh.expand_area.setVisibility(View.VISIBLE);
        } else vh.expand_area.setVisibility(View.INVISIBLE);
    }
}


