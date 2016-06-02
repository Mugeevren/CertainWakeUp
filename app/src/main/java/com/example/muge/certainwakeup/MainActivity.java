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
import android.util.Log;
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
        private ImageButton addAlarm,btnSettingsOfAlarm;
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
                    Intent intent=new Intent(MainActivity.this,ActiveAlarmActivity.class);
                    intent.putExtra("alarm",alarms.get(0).getId());//şuan listenin başındaki alarma göre çalışsın
                    startActivity(intent);
                }
            });

            addAlarm = (ImageButton)findViewById(R.id.addAlarm);
            addAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Ekleme yapılaak activitynin baslatılamsı

                    Intent intent=new Intent(MainActivity.this,AddUpdateAlarmActivity.class);
                    intent.putExtra("Islem",false);
                    startActivity(intent);
                }
            });





/*
            alarmdb.deleteAll();
            AlarmModel alarm=new AlarmModel();
            insertDefaultAlarm(alarm);
            AlarmModel alarm1=new AlarmModel(1,10,0,false,false,false,false,false,false,false,false,"sdkhxbacl",null,null,true,0,0,true,3);
            insertDefaultAlarm(alarm1);
*/
            //alarmdb.deleteAll();

            alarmdb.GetAttachedAlarm(alarms);//sqlite ile kayıtlı attached alarmları alarms listesine atar

            //adaptor işlemleri
            alarmList=(ListView)findViewById(R.id.alarmList);
            refreshAdapter();

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
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive(),alarm.getLabel(),alarm.getSound(),alarm.getVolume(),
                alarm.isVibration(),alarm.getStopCriter(),alarm.getDifficulty(),alarm.isSnooze(),alarm.getSnoozeCount());
    }




    private void updateAlarm(AlarmModel alarm)
    {
        AlarmDbHelper alarmdb=new AlarmDbHelper(this);
        boolean result=alarmdb.UpdateAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive(),alarm.getLabel(),alarm.getSound(),alarm.getVolume(),
                alarm.isVibration(),alarm.getStopCriter(),alarm.getDifficulty(),alarm.isSnooze(),alarm.getSnoozeCount());
        if(result==false)
            Toast.makeText(this,"Güncelleme Ekleme Başarısız",Toast.LENGTH_SHORT).show();
    }


    

    @Override
    public void onMondayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        
        alarm.setMonday(vh.mon.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }
    @Override
    public void onTuesdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setTuesday(vh.tue.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }

    @Override
    public void onWednesdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setWednesday(vh.wed.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }
    
    @Override
    public void onThursdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setThursday(vh.thu.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }

    @Override
    public void onFridayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setFriday(vh.fri.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }
    
    @Override
    public void onSaturdayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setSaturday(vh.sat.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }

    @Override
    public void onSundayToggleButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {

        alarm.setSunday(vh.sun.isChecked());
        alarm.getDays();
        updateAlarm(alarm);
    }

    @Override
    public void onOnOffSwitchClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        alarm.setIsActive(vh.onoff.isChecked());
        updateAlarm(alarm);
        if (vh.onoff.isChecked())//servis başlatılsın
        {
            startAlarmService(alarm);
        }
        else//servis durdurulsun
        {
            stopAlarmService(alarm);
        }
    }

    @Override
    public void onSettingsOfAlarmButtonClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        Intent intent=new Intent(MainActivity.this,AddUpdateAlarmActivity.class);
        intent.putExtra("Islem",true);
        intent.putExtra("alarmId",alarm.getId());
        startActivity(intent);
    }

    @Override
    public void onSnoozeCheckBoxClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        alarm.setSnooze(vh.ertelemeAktif.isChecked());
        if (vh.ertelemeAktif.isChecked()) {
            vh.layErteleme.setVisibility(View.VISIBLE);
            if (alarm.getSnoozeCount()==0) {
                vh.tvErtelemeSayisi.setText("1");
                alarm.setSnoozeCount(1);
            }
            else
                vh.tvErtelemeSayisi.setText(String.valueOf(alarm.getSnoozeCount()));
        }
        else
            vh.layErteleme.setVisibility(View.GONE);
        updateAlarm(alarm);
    }

    @Override
    public void onVibrateCheckBoxClickListener(int position, AlarmModel alarm, AlarmAdapter.ViewHolder vh) {
        alarm.setVibration(vh.titresimAktif.isChecked());
        updateAlarm(alarm);

    }


    @Override
    public void onDeleteButtonClickListener(int position,final AlarmModel alarm,final AlarmAdapter.ViewHolder vh) {

        stopAlarmService(alarm);
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


    /**
     * Stop any active AlarmService instances and start a new one.
     */
    private void startAlarmService(AlarmModel alarm) {
        Log.d("MainActivity", "startAlarmService()");
        Intent intent = new Intent(this,
                AlarmService.class);
        intent.putExtra("snoozeCounter",alarm.getSnoozeCount() );
        intent.putExtra("alarm",alarm.getId());
        //this.stopService(intent);
        this.startService(intent);
    }
    /**
     * Stop any active AlarmService instances and start a new one.
     */
    private void stopAlarmService(AlarmModel alarm) {
        Log.d("MainActivity", "stopAlarmService()");
        Intent intent = new Intent(getApplicationContext(),
                AlarmService.class);
        intent.putExtra("snoozeCounter",0 );
        intent.putExtra("alarm",alarm.getId());
        getApplicationContext().stopService(intent);
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


