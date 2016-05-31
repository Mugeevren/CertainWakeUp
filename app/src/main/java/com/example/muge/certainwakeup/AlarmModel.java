package com.example.muge.certainwakeup;


//import android.text.format.Time;

import java.lang.Object;
import java.util.Date;
import java.sql.Time;
/**
 * Created by muge on 22.3.2016.
 */
public class AlarmModel {
    private int id;
    private int hour;
    private int minute;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private boolean isActive;
    private String label;
    private String sound;
    private String volume;
    private boolean vibration;
    private int stopCriter;
    private int difficulty;
    private boolean snooze;
    private int snoozeCount;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public int getStopCriter() {
        return stopCriter;
    }

    public void setStopCriter(int stopCriter) {
        this.stopCriter = stopCriter;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public int getSnoozeCount() {
        return snoozeCount;
    }

    public void setSnoozeCount(int snoozeCount) {
        this.snoozeCount = snoozeCount;
    }

    public AlarmModel(){
        setId(1);
        setHour(8);
        setMinute(0);
        setIsActive(true);

        setMonday(true);
        setTuesday(true);
        setWednesday(true);
        setThursday(true);
        setFriday(true);
        setSaturday(false);
        setSunday(false);
        setLabel("deneme alarm");
        setSound(null);
        setVolume(null);
        setVibration(true);
        setStopCriter(0);
        setDifficulty(0);
        setSnooze(false);
        setSnoozeCount(0);

    }

    public AlarmModel(int id,int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act,String label,String sound,String volume,boolean vibration,int stopCriter,int difficulty,boolean snooze,int snoozeCount)
    {
        setId(id);
        setHour(hour);
        setMinute(minute);
        setFriday(fri);
        setMonday(mon);
        setIsActive(act);
        setSaturday(str);
        setThursday(thu);
        setWednesday(wed);
        setTuesday(tue);
        setSunday(sun);
        setLabel(label);
        setSound(sound);
        setVolume(volume);
        setVibration(vibration);
        setStopCriter(stopCriter);
        setDifficulty(difficulty);
        setSnooze(snooze);
        setSnoozeCount(snoozeCount);
    }
    @Override
    public String toString() {
        if (getHour()<10&&getMinute()<10)
            return "0"+getHour()+" : 0"+getMinute();
        else if(getHour()<10)
            return "0"+getHour()+" : "+getMinute();
        else if (getMinute()<10)
            return getHour()+" : 0"+getMinute();
        return getHour()+" : "+getMinute();
    }

}
