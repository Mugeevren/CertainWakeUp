package com.example.muge.certainwakeup;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private TimePickerDialog tpd;
    private Button time;
    private Switch onoff;
    private TextView alarmId;
    private CheckBox repeat_onoff;
    private LinearLayout expand_area;
    private LinearLayout repeat_days;
    private ToggleButton expandActivate,monday,tuesday;
    ImageButton btnDelete;




    public AlarmAdapter(Context context, int resource, ArrayList<AlarmModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.items=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(resource, parent, false);

        time= (Button) view.findViewById(R.id.btnAlarmTime);
        onoff=(Switch)view.findViewById(R.id.onoff);
        alarmId = (TextView)view.findViewById(R.id.alarmId);
        repeat_onoff=(CheckBox)view.findViewById(R.id.repeat_onoff);//tekrarlanmasını isteyip istemediğimiz zaman devreye girer
        expand_area=(LinearLayout)view.findViewById(R.id.expand_area);//alarmın onoff true olması durumunda açılacak
        repeat_days=(LinearLayout)view.findViewById(R.id.repeat_days);
        expandActivate = (ToggleButton)view.findViewById(R.id.toggleFrameActivate);
        monday = (ToggleButton)view.findViewById(R.id.toggleMonday);
        tuesday = (ToggleButton)view.findViewById(R.id.toggleTuesday);
        btnDelete = (ImageButton)view.findViewById(R.id.delete);

        time.setText(items.get(position).toString());
        onoff.setChecked(items.get(position).isActive());
        alarmId.setText(String.valueOf(items.get(position).getId()));


        //Kurulu alarmın timePickerDialog ile saat güncellemesi
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepickerHandle(position);
            }
        });


        //toggle button ile istenilen detaylı görüntü seçilebilir.
        expandActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandActivate.isChecked()){
                    expand_area.setVisibility(View.VISIBLE);
                }
                else expand_area.setVisibility(View.INVISIBLE);
            }
        });


        //alarmın aktif olup olmama durumunu günceller
        onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    items.get(position).setIsActive(true);
                else
                    items.get(position).setIsActive(false);
                updateAlarm(items.get(position));
            }
        });


        //Alarmın tekrarlanıp tekrarlanmama durumunu günceller
        repeat_onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeat_days.setVisibility(View.VISIBLE);
                    repeatDaysHandle(position);
                }
                else{
                    repeat_days.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }


    ///////***eksik- düzenleme günlerin tekrarı
    private void repeatDaysHandle(int position)
    {
        if(items.get(position).isMonday())
        {
            monday.setChecked(true);
        }
        if(items.get(position).isTuesday())
        {
            tuesday.setChecked(true);
        }
        //devamı
    }

    private void timepickerHandle(final int position) {

        tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override//istenilen alarm saati bilgileri buraya geliyor.
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourSet = hourOfDay;
                minuteSet = minute;
            }
        }, items.get(position).getHour(),items.get(position).getMinute(), true);

        tpd.setTitle("Saat Seçiniz");
        tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.get(position).setHour(hourSet);
                items.get(position).setMinute(minuteSet);
                updateAlarm(items.get(position));
                time.setText(items.get(position).toString());
            }
        });
        tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tpd.dismiss();
            }
        });
        tpd.show();
    }


    private void updateAlarm(AlarmModel alarm)
    {
        AlarmDbHelper alarmdb=new AlarmDbHelper(context);
        boolean result=alarmdb.UpdateAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());
        if(result==false)
            Toast.makeText(context,"Güncelleme Ekleme Başarısız",Toast.LENGTH_SHORT).show();
    }

}
