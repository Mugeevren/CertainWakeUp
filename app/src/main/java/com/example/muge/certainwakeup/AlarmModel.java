package com.example.muge.certainwakeup;


//import android.text.format.Time;

import java.lang.Object;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.sql.Time;
import java.util.List;

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
    private int daysFromNow;
    private List<Integer> days;

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
        days = new ArrayList<>();
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
        getDays();

    }

    public AlarmModel(int id,int hour,int minute,boolean mon,boolean tue,boolean wed,boolean thu,boolean fri,boolean str,boolean sun,boolean act,String label,String sound,String volume,boolean vibration,int stopCriter,int difficulty,boolean snooze,int snoozeCount)
    {
        days = new ArrayList<>();
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
        getDays();
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

    public boolean isShowingCurrentOrPastTime() {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY) >= getHour()
                && c.get(Calendar.MINUTE) >= getMinute()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Returns the number of days in between the weekdays passed as arguments.
     * The day1 and day2 variables must be integers between 1 and 7. The result
     * is the day difference between these two values. For instance if day1 has
     * value 6 (Friday) and day2 has value 3 (Tuesday), the returned value will
     * be 4 (since there is a 4 day difference between current Friday and next
     * weeks Tuesday).
     *
     * @param day1
     * @param day2
     * @return
     */
    public int getDaysBetween(int day1, int day2) {
        int counter = 0;
        while (true) {
            if (day1 == day2) {
                return counter;
            } else {
                day1++;
                if (day1 == 8) {
                    day1 = 1;
                }
                counter++;
            }
        }
    }

    public void getDays()
    {
        days.clear();
        if (isMonday())
            days.add(2);
        if (isTuesday())
            days.add(3);
        if (isWednesday())
            days.add(4);
        if (isThursday())
            days.add(5);
        if (isFriday())
            days.add(6);
        if (isSaturday())
            days.add(7);
        if (isSunday())
            days.add(1);
    }

    public List<Integer> getDaysFromNow()//servis tarafında getDaysFromNow ile haftanın alarm kurulacak günleri döndürülecek
    {
        List<Integer> list = new ArrayList<>();
        if (days.isEmpty())
            return null;
        Calendar c = Calendar.getInstance();

        for (int day:days)
        {
            list.add(getDaysBetween(c.get(Calendar.DAY_OF_WEEK),day));
        }

        //Collections.sort(list);
        return list;
    }

}
