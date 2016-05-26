package com.example.muge.certainwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by muge on 15.5.2016.
 */
public class SnoozeReceiver extends BroadcastReceiver {
    private static final String TAG = "SnoozeReceiver";

    Context context;
    int snoozeCounter;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;

        Bundle extras = intent.getExtras();
        if (extras != null) {
            snoozeCounter = extras.getInt("SNOOZE_COUNTER");
        }

        Intent newIntent = new Intent(context, ActiveAlarmActivity.class);
        newIntent.putExtra("SNOOZE_COUNTER", snoozeCounter);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

        Log.e(TAG, "ALARM RECEIVED!!!");
    }
}
