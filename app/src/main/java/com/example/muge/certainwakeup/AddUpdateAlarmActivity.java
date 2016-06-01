package com.example.muge.certainwakeup;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class AddUpdateAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private ToggleButton mon;
    private ToggleButton tue;
    private ToggleButton wed;
    private ToggleButton thu;
    private ToggleButton fri;
    private ToggleButton sat;
    private ToggleButton sun;
    private Button kaydet,expBtnSes,expBtnSonlandirma,expBtnErteleme,expBtnDiger;
    private AlarmDbHelper db;
    private Button back,btnDene;
    private ExpandableRelativeLayout expandableLayoutSes, expandableLayoutSonlandirma, expandableLayoutErteleme, expandableLayoutDiger;
    private boolean islem;
    private int alarmId;
    private RadioGroup rgZorluk,rgKriter;
    private RadioButton rbKriterYok,rbSoru,rbMat,rbKolay,rbOrta,rbZor;
    private CheckBox ertelemeAktif,titresimAktif;
    private TextView tvErtelemeSayisiDialog,tvErtelemeSayisi;
    private EditText etAlarmLabel;
    AlarmModel newAlarm;
    Intent intent;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        this.islem=intent.getBooleanExtra("Islem", false);

        setContentView(R.layout.activity_add_update_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //view nesnelerinini ve diğerlerinin atama işlemleri
        init();

        //Eğer güncelleme yapılacaksa istenilen alarm bligileri yazdırılıyor
        if(islem){
            toolbar.setTitle("Alarm güncelle");
            alarmOlustur();
            timePicker.setCurrentHour(newAlarm.getHour());
            timePicker.setCurrentMinute(newAlarm.getMinute());
            mon.setChecked(newAlarm.isMonday());
            tue.setChecked(newAlarm.isTuesday());
            wed.setChecked(newAlarm.isWednesday());
            thu.setChecked(newAlarm.isThursday());
            fri.setChecked(newAlarm.isFriday());
            sat.setChecked(newAlarm.isSaturday());
            sun.setChecked(newAlarm.isSunday());
            etAlarmLabel.setText(newAlarm.getLabel());
            //ses ayarları eklenecek
            titresimAktif.setChecked(newAlarm.isVibration());
            setRadioButtons();
            ertelemeAktif.setChecked(newAlarm.isSnooze());
            if (newAlarm.getSnoozeCount()==0)
                tvErtelemeSayisi.setText("Hayır");
            else
                tvErtelemeSayisi.setText(String.valueOf(newAlarm.getSnoozeCount()));

        }
        else
            toolbar.setTitle("Yeni alarm ekle");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddUpdateAlarmActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        expBtnSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutSes.toggle();
            }
        });
        expBtnSonlandirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutSonlandirma.toggle();
            }
        });
        expBtnErteleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutErteleme.toggle();
            }
        });
        expBtnDiger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutDiger.toggle();
            }
        });


        rbKriterYok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDene.setVisibility(View.GONE);
                rgZorluk.setVisibility(View.GONE);
            }
        });
        rbSoru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgZorluk.setVisibility(View.GONE);
                btnDene.setVisibility(View.VISIBLE);
            }
        });
        rbMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgZorluk.setVisibility(View.VISIBLE);
                btnDene.setVisibility(View.VISIBLE);
            }
        });

        tvErtelemeSayisiDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlg = new AlertDialog.Builder(AddUpdateAlarmActivity.this);
                final CharSequence items[] = {"Hayır","1","2","3","5"};
                dlg.setTitle("Erteleme sayısını sınırlandır ");
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvErtelemeSayisi.setText(items[which].toString());
                    }
                };
                dlg.setCancelable(true);
                dlg.setItems(items,listener);
                dlg.show();

            }
        });

        ertelemeAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ertelemeAktif.isChecked()) {
                    tvErtelemeSayisiDialog.setVisibility(View.VISIBLE);
                    tvErtelemeSayisi.setVisibility(View.VISIBLE);
                    tvErtelemeSayisi.setText("1");
                }
                else {
                    tvErtelemeSayisiDialog.setVisibility(View.GONE);
                    tvErtelemeSayisi.setVisibility(View.GONE);
                    tvErtelemeSayisi.setText("0");
                }


            }
        });

        btnDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddUpdateAlarmActivity.this,EndAlarmMathEquationActivity.class);
                intent.putExtra("alarm",-1);
                int difLevel,stopCri;
                if (rbSoru.isChecked()) {
                    difLevel = 0;
                    stopCri = 1;
                }
                else{
                    stopCri=2;
                    if(rbKolay.isChecked())
                        difLevel = 1;
                    else if(rbOrta.isChecked())
                        difLevel=2;
                    else difLevel = 3;
                }

                intent.putExtra("difficultyLevel",difLevel);
                intent.putExtra("alarmStopCriter",stopCri);
                startActivity(intent);

            }
        });

        //kaydet butonuna basılması
        saveNewAlarm();


    }



    public void init() {
        back = (Button)findViewById(R.id.btnBack);
        timePicker = (TimePicker) findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        mon = (ToggleButton) findViewById(R.id.toggleMonday2);
        tue = (ToggleButton) findViewById(R.id.toggleTuesday2);
        wed = (ToggleButton) findViewById(R.id.toggleWednesday2);
        thu = (ToggleButton) findViewById(R.id.toggleTuesday2);
        fri = (ToggleButton) findViewById(R.id.toggleFriday2);
        sat = (ToggleButton) findViewById(R.id.toggleSaturday2);
        sun = (ToggleButton) findViewById(R.id.toggleSunday2);
        kaydet = (Button) findViewById(R.id.save);
        db = new AlarmDbHelper(AddUpdateAlarmActivity.this);
        expandableLayoutSes = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutSes);
        expandableLayoutSonlandirma = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutSonlandirma);
        expandableLayoutErteleme = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutErteleme);
        expandableLayoutDiger = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutDiger);
        expBtnSes = (Button) findViewById(R.id.expandablebtnSes);
        expBtnSonlandirma = (Button) findViewById(R.id.expandablebtnSonlandirma);
        expBtnErteleme = (Button) findViewById(R.id.expandablebtnErteleme);
        expBtnDiger = (Button) findViewById(R.id.expandablebtnDiger);
        rgZorluk = (RadioGroup)findViewById(R.id.rgZorluk);
        rgKriter = (RadioGroup)findViewById(R.id.rgKriter);
        rbKriterYok = (RadioButton)findViewById(R.id.rbYokKriter);
        rbSoru = (RadioButton)findViewById(R.id.rbSoruKriter);
        rbMat = (RadioButton)findViewById(R.id.rbMatKriter);
        rbKolay = (RadioButton)findViewById(R.id.rbKolay);
        rbOrta = (RadioButton)findViewById(R.id.rbOrta);
        rbZor = (RadioButton)findViewById(R.id.rbZor);
        btnDene = (Button)findViewById(R.id.btnDene);
        ertelemeAktif = (CheckBox)findViewById(R.id.cbErtelemeAktif2);
        tvErtelemeSayisiDialog = (TextView)findViewById(R.id.ertelemeSayisiDialog);
        tvErtelemeSayisi = (TextView)findViewById(R.id.tvErtelemeSayisi);
        etAlarmLabel = (EditText)findViewById(R.id.etAlarmLabel);
        titresimAktif = (CheckBox)findViewById(R.id.vibrate_onoff2);
    }

    public void saveNewAlarm() {
        kaydet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Eğer yeni kayıt eklenecekse yani artı işaretine basılmışsa
                if(!islem)
                {
                    newAlarm = new AlarmModel();
                    setAlarmContent();

                    if (db.InsertAttachedAlarm(newAlarm.getHour(), newAlarm.getMinute(),
                            newAlarm.isMonday(), newAlarm.isTuesday(), newAlarm.isWednesday(), newAlarm.isThursday(),
                            newAlarm.isFriday(), newAlarm.isSaturday(), newAlarm.isSunday(), newAlarm.isActive(),newAlarm.getLabel(),
                            newAlarm.getSound(),newAlarm.getVolume(),newAlarm.isVibration(),newAlarm.getStopCriter(),
                            newAlarm.getDifficulty(),newAlarm.isSnooze(),newAlarm.getSnoozeCount())) {
                        Intent intent = new Intent(AddUpdateAlarmActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(AddUpdateAlarmActivity.this, "Alarm eklenemedi", Toast.LENGTH_SHORT);
                }

                //Uzun tıklama ile buraya gelinmiş ise yani güncelleme yapılacaksa
                else
                {
                    alarmOlustur();
                    setAlarmContent();

                    if (db.UpdateAttachedAlarm(newAlarm.getId(), newAlarm.getHour(), newAlarm.getMinute(),
                            newAlarm.isMonday(), newAlarm.isTuesday(), newAlarm.isWednesday(), newAlarm.isThursday(),
                            newAlarm.isFriday(), newAlarm.isSaturday(), newAlarm.isSunday(), newAlarm.isActive(),newAlarm.getLabel(),
                            newAlarm.getSound(),newAlarm.getVolume(),newAlarm.isVibration(),newAlarm.getStopCriter(),
                            newAlarm.getDifficulty(),newAlarm.isSnooze(),newAlarm.getSnoozeCount())) {
                        Intent intent = new Intent(AddUpdateAlarmActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(AddUpdateAlarmActivity.this, "Alarm eklenemedi", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    public void alarmOlustur()
    {
        //İlgili alarmı getirir
        alarmId= intent.getIntExtra("alarmId",0);
        newAlarm=new AlarmModel();
        newAlarm=db.getAlarm(alarmId);
    }

    //alarmın içeriği
    public void setAlarmContent()
    {
        newAlarm.setHour(timePicker.getCurrentHour());
        newAlarm.setMinute(timePicker.getCurrentMinute());
        newAlarm.setIsActive(true);
        newAlarm.setMonday(mon.isChecked());
        newAlarm.setTuesday(tue.isChecked());
        newAlarm.setWednesday(wed.isChecked());
        newAlarm.setThursday(thu.isChecked());
        newAlarm.setFriday(fri.isChecked());
        newAlarm.setSaturday(sat.isChecked());
        newAlarm.setSaturday(sun.isChecked());
        newAlarm.setLabel(etAlarmLabel.getText().toString());
        newAlarm.setSound(null);
        newAlarm.setVolume(null);
        newAlarm.setVibration(titresimAktif.isChecked());
        if (rbKriterYok.isChecked())
        {
            newAlarm.setStopCriter(0);
            newAlarm.setDifficulty(0);
        }
        else if (rbSoru.isChecked())
        {
            newAlarm.setStopCriter(1);
            newAlarm.setDifficulty(0);
        }
        else
        {
            newAlarm.setStopCriter(2);
            if (rbKolay.isChecked())
                newAlarm.setDifficulty(1);
            else if (rbOrta.isChecked())
                newAlarm.setDifficulty(2);
            else
                newAlarm.setDifficulty(3);
        }
        newAlarm.setSnooze(ertelemeAktif.isChecked());
        if (ertelemeAktif.isChecked())
            newAlarm.setSnoozeCount(Integer.parseInt(tvErtelemeSayisi.getText().toString()));
        else
            newAlarm.setSnoozeCount(0);
    }

    public void setRadioButtons()
    {
        if (newAlarm.getStopCriter()==0) {
            rbKriterYok.setChecked(true);
            btnDene.setVisibility(View.GONE);
        }
        else if (newAlarm.getStopCriter()==1) {
            rbSoru.setChecked(true);
            btnDene.setVisibility(View.VISIBLE);
        }
        else {
            rbMat.setChecked(true);
            btnDene.setVisibility(View.VISIBLE);
            if (newAlarm.getDifficulty()==1)
                rbKolay.setChecked(true);
            else if(newAlarm.getDifficulty()==2)
                rbOrta.setChecked(true);
            else
                rbZor.setChecked(true);
        }
    }





}
