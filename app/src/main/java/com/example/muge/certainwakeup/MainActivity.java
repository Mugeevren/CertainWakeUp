package com.example.muge.certainwakeup;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ExpandableListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class MainActivity extends AppCompatActivity implements AlarmAdapter.CustomButtonListener {

        private ArrayList<AlarmModel> alarms=new ArrayList<AlarmModel>();
        private ListView alarmList;
        private ImageButton addAlarm;
        private AlarmAdapter adapter;
        private Button deneme;

        //private MySharedPreferences sp;
        int ab=0;
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

            //AlarmCall();

            alarmdb=new AlarmDbHelper(MainActivity.this);

            deneme = (Button)findViewById(R.id.btnDeneme);
            deneme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,EndAlarmMathEquationActivity.class);
                    startActivity(intent);
                }
            });

            addAlarm = (ImageButton)findViewById(R.id.addAlarm);
            addAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Ekleme yapılaak activitynin baslatılamsı

                    Intent intent=new Intent(MainActivity.this,AddUpdateAlarmActivity.class);
                    intent.putExtra("ButonAdi",false);
                    startActivity(intent);
                }
            });


            //// TODO: 7.5.2016 alarmlistesinin bir elemanına tıklandığında updatealarm ekranına yönlendirme 
            /*
            alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "teufchdanjlfc", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
*/


/*
            alarmdb.deleteAll();
            AlarmModel alarm1 = new AlarmModel(2,9,30,true,false,false,false,false,false,false,true);
            AlarmModel alarm=new AlarmModel();
            insertDefaultAlarm(alarm);
            insertDefaultAlarm(alarm1);
*/
           //alarmdb.deleteAll();

            alarmdb.GetAttachedAlarm(alarms);//sql lite ile kayıtlı attached alarmları alarms listesine atar

            //adaptor işlemleri
            alarmList=(ListView)findViewById(R.id.alarmList);
            refreshAdapter();



            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        }

    private void refreshAdapter()
    {
        adapter=new AlarmAdapter(MainActivity.this,R.layout.alarm_row,alarms);
        adapter.setDeleteButtonClickListener(MainActivity.this);
        adapter.setTimeSetButtonClickListener(MainActivity.this);
        adapter.setExpandToggleButtonClickListener(MainActivity.this);
        adapter.setMondayToggleButtonListener(MainActivity.this);
        adapter.setTuesdayToggleButtonListener(MainActivity.this);
        adapter.setWednesdayToggleButtonListener(MainActivity.this);
        adapter.setThursdayToggleButtonListener(MainActivity.this);
        adapter.setFridayToggleButtonListener(MainActivity.this);
        adapter.setSaturdayToggleButtonListener(MainActivity.this);
        adapter.setSundayToggleButtonListener(MainActivity.this);
        alarmList.setAdapter(adapter);
    }

    private void insertDefaultAlarm(AlarmModel alarm)
    {
        alarmdb=new AlarmDbHelper(MainActivity.this);
        boolean result=alarmdb.InsertAttachedAlarm(alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());
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
    public void onMondayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        
        alarm.setMonday(vh.mon.isChecked());
        updateAlarm(alarm);
    }
    @Override
    public void onTuesdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setTuesday(vh.tue.isChecked());
        updateAlarm(alarm);
    }

    @Override
    public void onWednesdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setWednesday(vh.tue.isChecked());
        updateAlarm(alarm);
    }
    
    @Override
    public void onThursdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setThursday(vh.tue.isChecked());
        updateAlarm(alarm);
    }

    @Override
    public void onFridayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setFriday(vh.tue.isChecked());
        updateAlarm(alarm);
    }
    
    @Override
    public void onSaturdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setSaturday(vh.tue.isChecked());
        updateAlarm(alarm);
    }

    @Override
    public void onSundayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setSunday(vh.tue.isChecked());
        updateAlarm(alarm);
    }

    @Override
    public void onOnOffSwitchClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        alarm.setIsActive(vh.onoff.isActivated());
        updateAlarm(alarm);
    }


    @Override
    public void onDeleteButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {

        alarmdb.deleteAlarm(alarm.getId());
        alarmdb.GetAttachedAlarm(alarms);
        refreshAdapter();
    }

    @Override
    public void onTimeSetButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {

        //timepickerHandle(position);
        tpd = new TimePickerDialog(this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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
        } else {
            vh.expand_area.setVisibility(View.INVISIBLE);
            alarmList.setAdapter(adapter);
        }
    }


/*
    public void AlarmCall()
    {
        Intent intent=new Intent(MainActivity.this, MyBroadcastReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this,12,intent,0);
        AlarmManager am=(AlarmManager)MainActivity.this.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(10*1000),pi);
    }
    */
}


