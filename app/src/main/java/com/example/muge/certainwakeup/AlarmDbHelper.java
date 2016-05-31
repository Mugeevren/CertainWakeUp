package com.example.muge.certainwakeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by caglacagla on 28.3.2016.
 */
public class AlarmDbHelper extends SQLiteOpenHelper {
    //sqlLite adımızı sabitledik
    private static String DB_NAME="CertainWakeUpDatabase";
    private static int DB_VERSİON=1;

    public AlarmDbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSİON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AttachedAlarm");

    }
    @Override
    //AttachedAlarm tablosunu oluşturur.
    public void onCreate(SQLiteDatabase db)
    {
        //sonlandırma kriteri 0 ise kriter yok, 1 ise soru-cevap,2 ise matematik işlemi
        //zorluk seviyesimatematik işlemi değil ise 0 olsun.Eğer mat. islemi ise; 1 ise kolay,2 ise orta,3 ise zor olsun
        db.execSQL("CREATE TABLE AttachedAlarm(id INTEGER PRIMARY KEY,hour INTEGER,minute INTEGER,monday INTEGER,tuesday INTEGER,wednesday INTEGER,thursday INTEGER,friday INTEGER,saturday INTEGER,sunday INTEGER,isActive INTEGER,label TEXT,sound TEXT,volume TEXT,vibration INTEGER,stopCriter INTEGER,difficulty INTEGER,snooze INTEGER,snoozeCount INTEGER);");

    }

    //Yeni alarm ekleme işlemleri
    public boolean InsertAttachedAlarm(int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act,String label,String sound,String volume,boolean vibration,int stopCriter,int difficulty,boolean snooze,int snoozeCount)
    {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues newRow=new ContentValues();

        newRow.put("hour",hour);
        newRow.put("minute",minute);
        newRow.put("monday",mon);
        newRow.put("tuesday",tue);
        newRow.put("wednesday",wed);
        newRow.put("thursday",thu);
        newRow.put("friday",fri);
        newRow.put("saturday",str);
        newRow.put("sunday",sun);
        newRow.put("isActive", act);
        newRow.put("label",label);
        newRow.put("sound",sound);
        newRow.put("volume",volume);
        newRow.put("vibration",vibration);
        newRow.put("stopCriter",stopCriter);
        newRow.put("difficulty",difficulty);
        newRow.put("snooze",snooze);
        newRow.put("snoozeCount",snoozeCount);
        db.insert("AttachedAlarm",null,newRow);
        return true;
    }

    public void deleteAlarm(int id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("AttachedAlarm", " id = "+id, null);
        } catch (SQLException e) {
            Log.e("SQLite Error : ","Silinemedi!");
        }
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("AttachedAlarm","",null);
    }

    //TODO: label
    //attached alarmları listeler
    public void GetAttachedAlarm(ArrayList<AlarmModel> alarms)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM AttachedAlarm", null);
        cursor.moveToFirst();
        alarms.clear();
        AlarmModel alarm;
        for (int i=0;i<cursor.getCount();i++)
        {
            alarm=new AlarmModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    (cursor.getInt(3)!=0),(cursor.getInt(4)!=0),(cursor.getInt(5)!=0),
                    (cursor.getInt(6)!=0),(cursor.getInt(7)!=0),(cursor.getInt(8)!=0),
                    (cursor.getInt(9)!=0),(cursor.getInt(10)!=0),cursor.getString(11),
                    cursor.getString(12),cursor.getString(13),(cursor.getInt(14)!=0),
                    cursor.getInt(15),cursor.getInt(16),(cursor.getInt(17)!=0),cursor.getInt(18));
            alarms.add(alarm);
            cursor.moveToNext();
        }
    }
    public AlarmModel getAlarm(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM AttachedAlarm WHERE id="+String.valueOf(id), null);
        cursor.moveToFirst();
        AlarmModel alarm=new AlarmModel(
                cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                (cursor.getInt(3)!=0),(cursor.getInt(4)!=0),(cursor.getInt(5)!=0),
                (cursor.getInt(6)!=0),(cursor.getInt(7)!=0),(cursor.getInt(8)!=0),
                (cursor.getInt(9)!=0),(cursor.getInt(10)!=0),cursor.getString(11),
                cursor.getString(12),cursor.getString(13),(cursor.getInt(14)!=0),
                cursor.getInt(15),cursor.getInt(16),(cursor.getInt(17)!=0),cursor.getInt(18));
        return alarm;

    }

    //İstenilen attached alarm Güncellemsi yapar.
    public boolean UpdateAttachedAlarm(int id,int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act,String label,String sound,String volume,boolean vibration,int stopCriter,int difficulty,boolean snooze,int snoozeCount)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues newRow=new ContentValues();
        newRow.put("hour",hour);
        newRow.put("minute",minute);
        newRow.put("monday",mon);
        newRow.put("tuesday",tue);
        newRow.put("wednesday",wed);
        newRow.put("thursday",thu);
        newRow.put("friday",fri);
        newRow.put("saturday",str);
        newRow.put("sunday",sun);
        newRow.put("isActive", act);
        newRow.put("label",label);
        newRow.put("sound",sound);
        newRow.put("volume",volume);
        newRow.put("vibration",vibration);
        newRow.put("stopCriter",stopCriter);
        newRow.put("difficulty",difficulty);
        newRow.put("snooze",snooze);
        newRow.put("snoozeCount",snoozeCount);
        db.update("AttachedAlarm",newRow,"id=?",new String[] {Integer.toString(id)});
        return  true;
    }

}
