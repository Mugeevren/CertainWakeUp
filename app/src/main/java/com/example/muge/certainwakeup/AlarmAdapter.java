package com.example.muge.certainwakeup;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muge on 22.3.2016.
 */
class AlarmAdapter extends ArrayAdapter<AlarmModel>{

    private Context context;
    private int resource;
    private ArrayList<AlarmModel> items;

    int hourSet;
    int minuteSet;

    CustomButtonListener customListener;

    public interface CustomButtonListener {
        public void onDeleteButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onTimeSetButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onExpandToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onMondayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onTuesdayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onWednesdayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onThursdayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onFridayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onSaturdayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onSundayToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onOnOffSwitchClickListener(int position,AlarmModel alarm,ViewHolder vh);
    }

    public void setDeleteButtonClickListener(CustomButtonListener listener) {
        this.customListener = listener;
    }
    public void setTimeSetButtonClickListener(CustomButtonListener listener) {
        this.customListener = listener;
    }
    public void setExpandToggleButtonClickListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setOnOffSwitchClickListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setMondayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setTuesdayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setWednesdayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setThursdayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setFridayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setSaturdayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }
    public void setSundayToggleButtonListener(CustomButtonListener listener){
        this.customListener = listener;
    }


    public class ViewHolder {
        LinearLayout area;
        Button btnTime;
        Switch onoff;
        TextView alarmId;
        LinearLayout expand_area;
        ToggleButton expandActivate,mon,tue,wed,thu,fri,sat,sun;
        ImageButton btnDelete;
    }


    public AlarmAdapter(Context context, int resource, ArrayList<AlarmModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.items=objects;
    }


    @Override
    public View getView(final int position,View convertView,ViewGroup parent)
    {
        final ViewHolder viewHolder;

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.btnTime = (Button)convertView.findViewById(R.id.btnAlarmTime);
            viewHolder.onoff=(Switch)convertView.findViewById(R.id.onoff);
            viewHolder.alarmId = (TextView)convertView.findViewById(R.id.alarmId);
            viewHolder.expand_area=(LinearLayout)convertView.findViewById(R.id.expand_area);//alarmın onoff true olması durumunda açılacak
            viewHolder.expandActivate = (ToggleButton)convertView.findViewById(R.id.toggleFrameActivate);
            viewHolder.mon = (ToggleButton)convertView.findViewById(R.id.toggleMonday);
            viewHolder.tue = (ToggleButton)convertView.findViewById(R.id.toggleTuesday);
            viewHolder.wed = (ToggleButton)convertView.findViewById(R.id.toggleWednesday);
            viewHolder.thu = (ToggleButton)convertView.findViewById(R.id.toggleThursday);
            viewHolder.fri = (ToggleButton)convertView.findViewById(R.id.toggleFriday);
            viewHolder.sat = (ToggleButton)convertView.findViewById(R.id.toggleSaturday);
            viewHolder.sun = (ToggleButton)convertView.findViewById(R.id.toggleSunday);
            viewHolder.btnDelete = (ImageButton)convertView.findViewById(R.id.delete);
            viewHolder.area=(LinearLayout)convertView.findViewById(R.id.area);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final AlarmModel alarm = items.get(position);

        //alarmın özelliklerinin gösterimi
        viewHolder.btnTime.setText(alarm.toString());
        viewHolder.onoff.setChecked(alarm.isActive());
        viewHolder.alarmId.setText(String.valueOf(alarm.getId()));
        //repeat days handle
        viewHolder.mon.setChecked(alarm.isMonday());
        viewHolder.tue.setChecked(alarm.isTuesday());
        viewHolder.wed.setChecked(alarm.isWednesday());
        viewHolder.thu.setChecked(alarm.isThursday());
        viewHolder.fri.setChecked(alarm.isFriday());
        viewHolder.sat.setChecked(alarm.isSaturday());
        viewHolder.sun.setChecked(alarm.isSunday());



        //Kurulu alarmın timePickerDialog ile saat güncellemesi
        viewHolder.btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customListener!=null)
                {
                    customListener.onTimeSetButtonClickListener(position,alarm,viewHolder);
                }
            }
        });

        viewHolder.expandActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customListener!=null)
                    customListener.onExpandToggleButtonClickListener(position,alarm,viewHolder);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customListener!=null)
                    customListener.onDeleteButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onOnOffSwitchClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onMondayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onTuesdayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onWednesdayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onThursdayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onFridayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onSaturdayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });
        viewHolder.sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener!=null)
                    customListener.onSundayToggleButtonClickListener(position,alarm,viewHolder);
            }
        });

        return convertView;
    }


}

