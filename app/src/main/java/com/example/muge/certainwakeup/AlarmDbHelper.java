package com.example.muge.certainwakeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        super(context,DB_NAME,null,DB_VERSİON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AttachedAlarm");

    }
    @Override
    //AttachedAlarm tablosunu oluşturur.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AttachedAlarm("+"id INTEGER PRIMARY KEY,"+"hour INTEGER,"+"minute INTEGER,"+"monday INTEGER,"+"tuesday INTEGER,"+ "wednesday INTEGER,"+"thursday INTEGER,"+"friday INTEGER,"+"saturday INTEGER,"+"sunday INTEGER,"+"isActive INTEGER"+");");

    }

    //Yeni alarm ekleme işlemleri
    public boolean InsertAttachedAlarm(int id,int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act)
    {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues newRow=new ContentValues();
        newRow.put("id",id);
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

        db.insert("AttachedAlarm",null,newRow);
        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("AttachedAlarm","",null);
    }

    //attached alarmları listeler
    public void GetAttachedAlarm(ArrayList<AlarmModel> alarms)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM AttachedAlarm",null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++)
        {
            AlarmModel alarm=new AlarmModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    (cursor.getInt(3)!=0),(cursor.getInt(4)!=0),(cursor.getInt(5)!=0),
                    (cursor.getInt(6)!=0),(cursor.getInt(7)!=0),(cursor.getInt(8)!=0),
                    (cursor.getInt(9)!=0),(cursor.getInt(10)!=0));
            alarms.add(alarm);
            cursor.moveToNext();
        }
    }

    //İstenilen attached alarm Güncellemsi yapar.
    public boolean UpdateAttachedAlarm(int id,int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues newRow=new ContentValues();
        newRow.put("id",id);
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
        db.update("AttachedAlarm",newRow,"id=?",new String[] {Integer.toString(id)});
        return  true;
    }

}
