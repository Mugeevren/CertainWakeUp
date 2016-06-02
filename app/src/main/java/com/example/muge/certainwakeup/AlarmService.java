package com.example.muge.certainwakeup;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by muge on 15.5.2016.
 */
public class AlarmService extends Service {
    private static final String TAG = "AlarmService";

    final static int RequestCode = 1;
    int snoozeCounter;
    AlarmModel alarm;
    boolean issnooze;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("servis stop:","servis nesnesi yok edildi");
    }

    /**
     * This method is called after the start() command of the service. It sets a
     * new alarm based on UserData file and starts the service.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO: bu metod da alarm bilgisi alınacak
        //erteleme sayısı da alarm bilgisi ile gelecek.
        //setAlarm metodu çağırılacak ve notification oluşturulacak

        Log.d(TAG, "onStartCommand");
        // get snooze counter
        Bundle extras = intent.getExtras();
        if (extras != null) {
            snoozeCounter = extras.getInt("snoozeCounter");
            alarmOlustur(extras.getInt("alarm"));
            issnooze=extras.getBoolean("issnooze");
        }




        // set alarm
        setAlarm(alarm);

        /*
        // build notification
        NotificationCompat.Builder noteBuilder = new NotificationCompat.Builder(
                this);
        noteBuilder.setSmallIcon(R.drawable.ic_launcher);
        noteBuilder.setContentTitle(getString(R.string.service_note_title));
        noteBuilder.setContentText(getString(R.string.service_note_message)
                + "\n" + nextAlarmString);
        Notification note = noteBuilder.getNotification();
*/
        // start service
        //startForeground(1, null);


        return START_STICKY;
    }

    /**
     * Sets a new alarm.
     *
     * @param alarmInfo
     *            Information about the alarm being set.
     */
    private void setAlarm(AlarmModel alarmInfo) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        if(issnooze==true)
        {
            Intent intent = new Intent(getApplicationContext(), SnoozeReceiver.class);
            intent.putExtra("snoozeCounter", snoozeCounter);
            //intent.putExtra("alarm",alarmInfo.getId());
            // create intent
            pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(),(int) System.currentTimeMillis(), intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            // get alarm manager
            alarmManager = (AlarmManager) getApplicationContext()
                    .getSystemService(Context.ALARM_SERVICE);
            // cancel any previous alarms if set
            alarmManager.cancel(pendingIntent);
            // if the alarm is active, choose time and date of the alarm and
            // set it in the alarm manager
        }
        else
        {
            // set snooze counter
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("snoozeCounter", snoozeCounter);
            intent.putExtra("alarm",alarmInfo.getId());
            // create intent
            pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(),(int) System.currentTimeMillis(), intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            // get alarm manager
            alarmManager = (AlarmManager) getApplicationContext()
                    .getSystemService(Context.ALARM_SERVICE);
            // cancel any previous alarms if set
            alarmManager.cancel(pendingIntent);
            // if the alarm is active, choose time and date of the alarm and
            // set it in the alarm manager

            if (alarmInfo.isActive()) {
                Calendar c = Calendar.getInstance();
                List<Integer> daysFromNow = alarmInfo.getDaysFromNow();
                c.set(Calendar.HOUR_OF_DAY, alarmInfo.getHour());
                c.set(Calendar.MINUTE, alarmInfo.getMinute());
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                long alarmTime;
                int date = Calendar.DATE;
                if (daysFromNow==null)
                {
                    if (alarmInfo.isShowingCurrentOrPastTime()) {
                        c.add(date, 1);
                    }
                    alarmTime = c.getTimeInMillis();
                    Log.d(TAG, "Alarm set to: " + c.getTime().toString());
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
                }
                else
                {
                    if (daysFromNow.contains(0)&&alarmInfo.isShowingCurrentOrPastTime()) {
                        daysFromNow.remove(new Integer(0));
                        daysFromNow.add(new Integer(7));
                    }
                    for(int days:daysFromNow) {
                        c.add(date, days);
                        alarmTime = c.getTimeInMillis();
                        Log.d(TAG, "Alarm set to: " + c.getTime().toString());
                        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
                    }
                }


            }

        }



    }


    public void alarmOlustur(int alarmId)
    {
        //İlgili alarmı getirir
        AlarmDbHelper db = new AlarmDbHelper(AlarmService.this);
        alarm=new AlarmModel();
        alarm=db.getAlarm(alarmId);
    }



}
