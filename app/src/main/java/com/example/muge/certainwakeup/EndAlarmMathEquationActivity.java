package com.example.muge.certainwakeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EndAlarmMathEquationActivity extends AppCompatActivity {

    private static final String TAG = "EndAlarmMathActivity";

    TextView txtEq;
    EditText answer;
    Button btnEnd;
    Chronometer chronometer;
    Bundle extras;
    AlarmModel alarm;
    int alarmStopCriter,difficultyLevel;
    List<QuestionModel> sorular;
    QuestionModel soru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_alarm_math_equation);

        init();

        extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getInt("alarm")==-1)//dene butonu ile gönderilmiş ise
            {
                alarmStopCriter = extras.getInt("alarmStopCriter");
                difficultyLevel = extras.getInt("difficultyLevel",0);
            }
            else//alarm seçilip gönderilmiş ise
            {
                alarmOlustur(extras.getInt("alarm"));
                alarmStopCriter = alarm.getStopCriter();
                difficultyLevel = alarm.getDifficulty();
            }

            if (alarmStopCriter==1)//soru_cevap
            {
                answer.setInputType(InputType.TYPE_CLASS_TEXT);
                //projeye gömülen json dosyasından string okunacak,
                //sorular parse edilecek
                parseData();
                Log.d(TAG, "onCreate: "+sorular.get(0).getQuestion());
                Random r = new Random();
                int rast = r.nextInt(sorular.size());
                soru = sorular.get(rast);
                soru.setAnswer(soru.getAnswer().toLowerCase());


            }
            else//matematik işlemi
            {
                answer.setInputType(InputType.TYPE_CLASS_NUMBER);
                RandomMathEquationGenerator generator;
                if (difficultyLevel==1)
                    generator = new RandomMathEquationGenerator(DifficultyLevel.EASY);
                else if (difficultyLevel==2)
                    generator = new RandomMathEquationGenerator(DifficultyLevel.NORMAL);
                else
                    generator = new RandomMathEquationGenerator(DifficultyLevel.DIFFICULT);
                soru = generator.generateEquation();
            }

        }
        Log.d(TAG, "yapılan");


        txtEq.setText(soru.getQuestion());


        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().toLowerCase().equals(soru.getAnswer().toString()))//doğru bilindiyse kapatılsın
                {
                    Intent intent=new Intent(EndAlarmMathEquationActivity.this,ActiveAlarmActivity.class);
                    intent.putExtra("isClose",true);
                    startActivity(intent);
                    /*
                    Intent intent=new Intent(EndAlarmMathEquationActivity.this,ActiveAlarmActivity.class);
                    intent.putExtra("SNOOZE_COUNTER", 0);//erteleme için tekrar soru sorma yok, active alarm çalışır
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    (EndAlarmMathEquationActivity.this).startActivity(intent);
                    */

                } else {

                    Toast.makeText(EndAlarmMathEquationActivity.this, "Yanlış cevap verdiniz!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                    answer.setHint("?");
                }
            }
        });


    }


    private void parseData() {
        try {
            JSONObject object = new JSONObject(getData());
            JSONArray kayitlar = object.getJSONArray("questions");
            sorular =new ArrayList<QuestionModel>();

            for (int i= 0;i<kayitlar.length();i++)
            {
                JSONObject kayitObject =  kayitlar.getJSONObject(i);
                QuestionModel kayit = new QuestionModel(kayitObject.getString("question"),kayitObject.getString("answer"));
                sorular.add(kayit);
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private String getData()
    {
        String json = "";
        try{
            InputStream inputStream = getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }


    public void alarmOlustur(int alarmId)
    {
        //İlgili alarmı getirir
        AlarmDbHelper db = new AlarmDbHelper(EndAlarmMathEquationActivity.this);
        alarm=new AlarmModel();
        alarm=db.getAlarm(alarmId);
    }

    public void init()
    {
        txtEq = (TextView)findViewById(R.id.equation_content);
        answer = (EditText)findViewById(R.id.answer);
        btnEnd =(Button)findViewById(R.id.btnAnswerAndStopAlarm);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
    }

}

