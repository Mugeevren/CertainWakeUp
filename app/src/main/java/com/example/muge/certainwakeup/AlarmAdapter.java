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
    private Button btnTime;
    private Switch onoff;
    private TextView alarmId;
    private LinearLayout expand_area;
    private ToggleButton expandActivate,mon,tue,wed,thu,fri,sat,sun;
    ImageButton btnDelete;




    public AlarmAdapter(Context context, int resource, ArrayList<AlarmModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.items=objects;
    }

    public void identification(View view)
    {

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(resource, parent, false);


        //tanımlamalar
        //identification(view);
        btnTime= (Button) view.findViewById(R.id.btnAlarmTime);
        onoff=(Switch)view.findViewById(R.id.onoff);
        alarmId = (TextView)view.findViewById(R.id.alarmId);
        expand_area=(LinearLayout)view.findViewById(R.id.expand_area);//alarmın onoff true olması durumunda açılacak
        expandActivate = (ToggleButton)view.findViewById(R.id.toggleFrameActivate);
        mon = (ToggleButton)view.findViewById(R.id.toggleMonday);
        tue = (ToggleButton)view.findViewById(R.id.toggleTuesday);
        wed = (ToggleButton)view.findViewById(R.id.toggleWednesday);
        thu = (ToggleButton)view.findViewById(R.id.toggleThursday);
        fri = (ToggleButton)view.findViewById(R.id.toggleFriday);
        sat = (ToggleButton)view.findViewById(R.id.toggleSaturday);
        sun = (ToggleButton)view.findViewById(R.id.toggleSunday);
        btnDelete = (ImageButton)view.findViewById(R.id.delete);

        //alarmın özelliklerinin gösterimi
        btnTime.setText(items.get(position).toString());
        onoff.setChecked(items.get(position).isActive());
        alarmId.setText(String.valueOf(items.get(position).getId()));
        //repeat days handle
        mon.setChecked(items.get(position).isMonday());
        tue.setChecked(items.get(position).isTuesday());
        wed.setChecked(items.get(position).isWednesday());
        thu.setChecked(items.get(position).isThursday());
        fri.setChecked(items.get(position).isFriday());
        sat.setChecked(items.get(position).isSaturday());
        sun.setChecked(items.get(position).isSunday());

        //Kurulu alarmın timePickerDialog ile saat güncellemesi
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timepickerHandle(position);
                tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override//istenilen alarm saati bilgileri buraya geliyor.
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourSet = hourOfDay;
                        minuteSet=minute;
                        items.get(position).setHour(hourSet);
                        items.get(position).setMinute(minuteSet);
                        updateAlarm(items.get(position));

                        //getView(position,convertView,parent);
                        //bir sonraki sıradakinin butonunun textini değiştiriyor nedenini bulamadım
                        btnTime.setText(items.get(position).toString());
                    }
                }, items.get(position).getHour(),items.get(position).getMinute(), true);

                tpd.setTitle("Saat Seçiniz");
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
        });



        //toggle button ile istenilen detaylı görüntü seçilebilir.
        expandActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandActivate.isChecked()) {
                    expand_area.setVisibility(View.VISIBLE);
                } else expand_area.setVisibility(View.INVISIBLE);
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


        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setMonday(mon.isChecked());
                updateAlarm(items.get(position));
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setTuesday(tue.isChecked());
                updateAlarm(items.get(position));
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setWednesday(wed.isChecked());
                updateAlarm(items.get(position));
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setThursday(thu.isChecked());
                updateAlarm(items.get(position));
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setFriday(fri.isChecked());
                updateAlarm(items.get(position));
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setSaturday(sat.isChecked());
                updateAlarm(items.get(position));
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setSunday(sun.isChecked());
                updateAlarm(items.get(position));
            }
        });

        return view;

    }


    private void repeatDaysHandle(int position)
    {

    }

    private void timepickerHandle(final int position) {


        tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override//istenilen alarm saati bilgileri buraya geliyor.
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourSet = hourOfDay;
                minuteSet=minute;
                items.get(position).setHour(hourSet);
                items.get(position).setMinute(minuteSet);
                updateAlarm(items.get(position));

                //getView(position,convertView,parent);
                //bir sonraki sıradakinin butonunun textini değiştiriyor nedenini bulamadım
                //btnTime.setText(items.get(position).toString());
            }
        }, items.get(position).getHour(),items.get(position).getMinute(), true);

        tpd.setTitle("Saat Seçiniz");
        tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
