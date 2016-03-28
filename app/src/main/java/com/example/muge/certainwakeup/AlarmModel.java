package com.example.muge.certainwakeup;


//import android.text.format.Time;

import java.lang.Object;
import java.util.Date;
import java.sql.Time;
/**
 * Created by muge on 22.3.2016.
 */
public class AlarmModel {
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

    @Override
    public String toString() {
        if (getHour()<10&&getMinute()<10)
            return "0"+getHour()+" : 0"+getMinute();
        else if(getHour()<10)
            return "0"+getHour()+" : "+getMinute();
        else if (getMinute()<10)
            return getMinute()+" : 0"+getMinute();
        return getHour()+" : "+getMinute();
    }

    public AlarmModel()
    {
        setHour(8);
        setMinute(8);
        setMonday(true);
        setTuesday(true);
        setWednesday(true);
        setThursday(true);
        setFriday(true);
        setSaturday(false);
        setSunday(false);
        setIsActive(false);
    }


}
