package com.example.muge.certainwakeup;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
        final View view= inflater.inflate(resource, parent, false);

        final Button time= (Button) view.findViewById(R.id.btnAlarmTime);
        Switch onoff=(Switch)view.findViewById(R.id.onoff);
        TextView alarmId = (TextView)view.findViewById(R.id.alarmId);
        final CheckBox repeat_onoff=(CheckBox)view.findViewById(R.id.repeat_onoff);//tekrarlanmasını isteyip istemediğimiz zaman devreye girer
        final LinearLayout expand_area=(LinearLayout)view.findViewById(R.id.expand_area);//alarmın onoff true olması durumunda açılacak
        final LinearLayout repeat_days=(LinearLayout)view.findViewById(R.id.repeat_days);

        time.setText(items.get(position).toString());
        onoff.setChecked(items.get(position).isActive());
        alarmId.setText(String.valueOf(items.get(position).getId()));
        if(onoff.isChecked())//alarm ilk açıldığında aktif ise expand alanı görünsün.
        {
            expand_area.setVisibility(View.VISIBLE);
            if(repeat_onoff.isChecked())
            {
                repeat_days.setVisibility(View.VISIBLE);
            }
        }

        //Kurulu alarmın timePickerDialog ile saat güncellemesi
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog tpd;

                tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override//istenilen alarm saati bilgileri buraya geliyor.
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourSet = hourOfDay;
                        minuteSet = minute;
                        items.get(position).setHour(hourOfDay);
                        items.get(position).setMinute(minute);
                        UpdateDummyData(items.get(position));
                        time.setText(items.get(position).toString());
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

        //alarmın aktif olup olmama durumunu günceller
        onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    items.get(position).setIsActive(true);
                    expand_area.setVisibility(View.VISIBLE);// alarm aktifse repeat butonu görnsün
                }
                else
                {
                    items.get(position).setIsActive(false);
                    expand_area.setVisibility(View.GONE);//alarm aktif değilse repeat butonu görünmesin
                }
                UpdateDummyData(items.get(position));
            }
        });


        //Alarmın tekrarlanıp tekrarlanmama durumunu günceller
        repeat_onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeat_days.setVisibility(View.VISIBLE);
                }
                else{
                    repeat_days.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }



    private void UpdateDummyData(AlarmModel alarm)
    {
        AlarmDbHelper alarmdb=new AlarmDbHelper(context);
        boolean result=alarmdb.UpdateAttachedAlarm(alarm.getId(), alarm.getHour(), alarm.getMinute()
                , alarm.isMonday(), alarm.isTuesday(), alarm.isWednesday(), alarm.isThursday(), alarm.isFriday(),
                alarm.isSaturday(), alarm.isSunday(), alarm.isActive());
        if(result==true)
        {
            Toast.makeText(context, "Güncelleme başarıyla eklendi.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,"Güncelleme Ekleme Başarısız",Toast.LENGTH_LONG).show();
        }
    }

}
