package com.example.muge.certainwakeup;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class ActiveAlarmActivity extends AppCompatActivity {


    private final static int RequestCode = 1;
    private static final String TAG = "ActiveAlarmActivity";

    private MediaPlayer player;
    private Vibrator vibrator;
    private int snoozeCounter;
    private AlarmModel alarm;
    private TextView tvAlarmClock;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        tvAlarmClock = (TextView)findViewById(R.id.textClock);


        // get alarmId and snoozeCount from the intent
        extras = getIntent().getExtras();
        if (extras != null) {
            alarmOlustur(extras.getInt("alarm"));
            tvAlarmClock.setText(alarm.toString());
            if (extras.getBoolean("isSnoozed",false)==true)//erteleme ile gelen alarm ise
                snoozeCounter = extras.getInt("snoozeCounter");
            else//ilk çalışı ise
            {
                snoozeCounter = alarm.getSnoozeCount();
            }
        }
        Log.d(TAG, "onCreate() - snoozeCounter = " + snoozeCounter);
        // set button listeners
        Button buttonOff = (Button) findViewById(R.id.btnStopAlarm);
        buttonOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarm.getStopCriter()==0) {
                    Intent intent = new Intent(ActiveAlarmActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    alarmOffIntent();
            }
        });
        Button buttonSnooze = (Button) findViewById(R.id.btnSnoozeAlarm);
        buttonSnooze.setBackgroundColor(Color.rgb(0, 150, 150));
        buttonSnooze.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.getBoolean("isSnoozed",true);
                extras.putInt("snoozeCounter",snoozeCounter);
                alarmOffIntent();
                //alarmSnooze(); // bu çalıştırılmalı
            }
        });


        // execute alarm sound and vibration async task
        new SoundAlarm().execute();

    }

    public void alarmOlustur(int alarmId)
    {
        //İlgili alarmı getirir
        AlarmDbHelper db = new AlarmDbHelper(ActiveAlarmActivity.this);
        alarm=new AlarmModel();
        alarm=db.getAlarm(alarmId);
    }

    /**
     * Method used to deactivate the alarm after the user used the "Wake up"
     * button.
     */
    private void alarmOffIntent() {

        Intent intent=new Intent(ActiveAlarmActivity.this,EndAlarmMathEquationActivity.class);
        intent.putExtras(extras);
        startActivity(intent);

        /*
        new StopAlarm().execute();
        Log.d(TAG, "alarmOff() - snoozeCounter = " + snoozeCounter);
        snoozeCounter = 0;
        resetAlarmService();
        finish();
        */
    }

    /**
     * Method used to deactivate and reschedule the alarm after the user used
     * the "Snooze" button.
     */
    private void alarmSnooze() {
        new StopAlarm().execute();
        snoozeCounter++;
        Log.d(TAG, "alarmSnooze() - snoozeCounter = " + snoozeCounter);
        // setup next snooze alarm time
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 1);//1 olan erteleme aralığı- TODO:kullanıcıdan alınabilsin
        long nextSnoozeTime = c.getTimeInMillis();
        // set new snooze alarm
        Intent intent = new Intent(getApplicationContext(),
                SnoozeReceiver.class);
        intent.putExtra("SNOOZE_COUNTER", snoozeCounter);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), RequestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG, "Snooze set to: " + c.getTime().toString());
        alarmManager
                .set(AlarmManager.RTC_WAKEUP, nextSnoozeTime, pendingIntent);
        finish();
    }

    /**
     * Cancel any AlarmService tasks and run then again with renewed parameters
     * (new alarm date and time).
     */
    private void resetAlarmService() {
        Log.d(TAG, "restart AlarmService");
        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
        stopService(intent);
        startService(intent);
    }


    private void play(Context context, Uri alert) {
        player = new MediaPlayer();
        try {
            player.setDataSource(context, alert);
            final AudioManager audio = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audio.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.prepare();
                player.start();
            }
        } catch (IOException e) {
            Log.e("Error....", "Check code...");
        }
    }


    private Uri getAlarmSound() {
        Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if (alertSound == null) {
            alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alertSound == null) {
                alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alertSound;
    }


    /**
     * AsynTask used for starting the alarm sound and vibration.
     */
    private class SoundAlarm extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {

            /*
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if(alert == null){
                // alert is null, using backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if(alert == null){
                    // alert backup is null, using 2nd backup
                    alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            int maxVolumeAlarm = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            int maxVolumeRing = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolumeAlarm,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolumeRing,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            r.play();
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = { 1000, 1000 };
            vibrator.vibrate(pattern,0);
            Toast.makeText(getApplicationContext(), "alarm started", Toast.LENGTH_LONG).show();
*/

            play(ActiveAlarmActivity.this,getAlarmSound());

            Log.d(TAG, "Is playing: " + player.isPlaying());

            return null;
        }
    }

    /**
     * AsynTask used for stopping the alarm sound and vibration.
     */
    private class StopAlarm extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            player.stop();
            player.reset();
            player.release();
            vibrator.cancel();
            return null;
        }
    }



    @Override
    public void onBackPressed() {
        // snooze alarm if the user presses the back button
        alarmSnooze();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // unlock screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onDestroy();
    }



}


