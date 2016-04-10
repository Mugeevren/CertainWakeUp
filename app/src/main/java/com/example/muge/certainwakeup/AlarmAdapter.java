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

    private TimePickerDialog tpd;
    CustomButtonListener customListener;

    public interface CustomButtonListener {
        public void onDeleteButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onTimeSetButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);
        public void onExpandToggleButtonClickListener(int position,AlarmModel alarm,ViewHolder vh);

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


    //switch on-off ve haftanın günleri için de on ve set click listenerlar yapılacak

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





        //alarmın aktif olup olmama durumunu günceller
        viewHolder.onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                   alarm.setIsActive(true);
                else
                    alarm.setIsActive(false);
                updateAlarm(alarm);
            }
        });

        viewHolder.area.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(context,AddUpdateAlarmActivity.class);
                intent.putExtra("Islem", true);
                intent.putExtra("alarmId", alarm.getId());
                context.startActivity(intent);
                return false;
            }
        });



        /*
        viewHolder.mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setMonday(mon.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setTuesday(tue.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setWednesday(wed.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setThursday(thu.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setFriday(fri.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setSaturday(sat.isChecked());
                updateAlarm(items.get(position));
            }
        });
        viewHolder.sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setSunday(sun.isChecked());
                updateAlarm(items.get(position));
            }
        });

        */

        return convertView;

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

