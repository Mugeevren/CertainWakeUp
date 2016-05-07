package com.example.muge.certainwakeup;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AddUpdateAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private ToggleButton mon;
    private ToggleButton tue;
    private ToggleButton wed;
    private ToggleButton thu;
    private ToggleButton fri;
    private ToggleButton sat;
    private ToggleButton sun;
    private Button kaydet;
    private AlarmDbHelper db;

    private boolean islem;
    private int alarmId;

    AlarmModel newAlarm;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        this.islem=intent.getBooleanExtra("Islem", false);

        setContentView(R.layout.activity_add_update_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //view nesnelerinini ve diğerlerinin atama işlemleri
        init();

        //Eğer güncelleme yapılacaksa istenilen alarm bligileri yazdırılıyor
        if(islem){
            AlarmOlustur();
            timePicker.setCurrentHour(newAlarm.getHour());
            timePicker.setCurrentMinute(newAlarm.getMinute());
            mon.setChecked(newAlarm.isMonday());
            thu.setChecked(newAlarm.isTuesday());
            wed.setChecked(newAlarm.isWednesday());
            thu.setChecked(newAlarm.isThursday());
            fri.setChecked(newAlarm.isFriday());
            sat.setChecked(newAlarm.isSaturday());
            sun.setChecked(newAlarm.isSunday());
        }

        //kaydet butonuna basılması
        saveNewAlarm();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    public void init() {
        timePicker = (TimePicker) findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        mon = (ToggleButton) findViewById(R.id.toggleMonday2);
        tue = (ToggleButton) findViewById(R.id.toggleTuesday2);
        wed = (ToggleButton) findViewById(R.id.toggleWednesday2);
        thu = (ToggleButton) findViewById(R.id.toggleTuesday2);
        fri = (ToggleButton) findViewById(R.id.toggleFriday2);
        sat = (ToggleButton) findViewById(R.id.toggleSaturday2);
        sun = (ToggleButton) findViewById(R.id.toggleSunday2);
        kaydet = (Button) findViewById(R.id.save);
        db = new AlarmDbHelper(AddUpdateAlarmActivity.this);



    }

    public void saveNewAlarm() {
        kaydet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Eğer yeni kayıt eklenecekse yani artı işaretine basılmışsa
                if(!islem)
                {
                    newAlarm = new AlarmModel();
                    newAlarm.setHour(timePicker.getCurrentHour());
                    newAlarm.setMinute(timePicker.getCurrentMinute());
                    newAlarm.setId(12);
                    newAlarm.setIsActive(true);
                    newAlarm.setMonday(mon.isChecked());
                    newAlarm.setTuesday(thu.isChecked());
                    newAlarm.setWednesday(wed.isChecked());
                    newAlarm.setThursday(thu.isChecked());
                    newAlarm.setFriday(fri.isChecked());
                    newAlarm.setSaturday(sat.isChecked());
                    newAlarm.setSaturday(sun.isChecked());

                    if (db.InsertAttachedAlarm(newAlarm.getId(), newAlarm.getHour(), newAlarm.getMinute(),
                            newAlarm.isMonday(), newAlarm.isTuesday(), newAlarm.isWednesday(), newAlarm.isThursday(),
                            newAlarm.isFriday(), newAlarm.isSaturday(), newAlarm.isSunday(), newAlarm.isActive())) {
                        Intent intent = new Intent(AddUpdateAlarmActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(AddUpdateAlarmActivity.this, "Alarm eklenemedi", Toast.LENGTH_SHORT);
                }

                //Uzun tıklama ile buraya gelinmiş ise yani güncelleme yapılacaksa
                else
                {
                    AlarmOlustur();
                    newAlarm.setHour(timePicker.getCurrentHour());
                    newAlarm.setMinute(timePicker.getCurrentMinute());
                    newAlarm.setIsActive(true);
                    newAlarm.setMonday(mon.isChecked());
                    newAlarm.setTuesday(thu.isChecked());
                    newAlarm.setWednesday(wed.isChecked());
                    newAlarm.setThursday(thu.isChecked());
                    newAlarm.setFriday(fri.isChecked());
                    newAlarm.setSaturday(sat.isChecked());
                    newAlarm.setSaturday(sun.isChecked());

                    if (db.UpdateAttachedAlarm(newAlarm.getId(), newAlarm.getHour(), newAlarm.getMinute(),
                            newAlarm.isMonday(), newAlarm.isTuesday(), newAlarm.isWednesday(), newAlarm.isThursday(),
                            newAlarm.isFriday(), newAlarm.isSaturday(), newAlarm.isSunday(), newAlarm.isActive())) {
                        Intent intent = new Intent(AddUpdateAlarmActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(AddUpdateAlarmActivity.this, "Alarm eklenemedi", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    public void AlarmOlustur()
    {
        //İlgili alarmı getirir
        alarmId= intent.getIntExtra("alarmId",0);
        newAlarm=new AlarmModel();
        newAlarm=db.getAlarm(alarmId);
    }





}
