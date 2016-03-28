package com.example.muge.certainwakeup;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muge on 22.3.2016.
 */
class AlarmAdapter extends ArrayAdapter<AlarmModel>{

    private Context context;
    private int resourse;
    private ArrayList<AlarmModel> items;
    public int setHour,setMinute;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resourse,parent,false);
        //view'in içeriği
        Button time = (Button)view.findViewById(R.id.btnAlarmTime);
        Switch onoff = (Switch)view.findViewById(R.id.onoff);
        //haftanın günleri butonları da eklenecek

        time.setText(items.get(position).toString());
        onoff.setChecked(items.get(position).isActive());

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tpd;
                tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        ////// asıl işlem burada yapılacak
                        setHour = hourOfDay;
                        setMinute = minute;
                    }
                }, 0, 0, true);
                tpd.setTitle("Saat Seçiniz");
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", tpd);

                tpd.show();

            }
        });

        return view;
    }

    public AlarmAdapter(Context context, int resource, ArrayList<AlarmModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse = resource;
        this.items = objects;
    }
}
