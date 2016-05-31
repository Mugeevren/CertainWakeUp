package com.example.muge.certainwakeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class EndAlarmMathEquationActivity extends AppCompatActivity {

    TextView txtEq;
    EditText answer;
    Button btnEnd;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_alarm_math_equation);

        init();

        RandomMathEquationGenerator generator = new RandomMathEquationGenerator(DifficultyLevel.EASY);
        HashMap<String,Integer> h = generator.generateEquation();

        String key;
        int value=0;

        for(Map.Entry<String, Integer> entry : h.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            txtEq.setText(key);
        }
;
        final int fValue = value;
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(answer.getText().toString()) == fValue) {
                    Intent intent=new Intent(EndAlarmMathEquationActivity.this,ActiveAlarmActivity.class);
                    intent.putExtra("SNOOZE_COUNTER", 0);//erteleme için tekrar soru sorma yok, active alarm çalışır
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    (EndAlarmMathEquationActivity.this).startActivity(intent);
                } else {
                    Toast.makeText(EndAlarmMathEquationActivity.this, "Yanlış cevap verdiniz!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                    answer.setHint("?");
                }
            }
        });


    }

    public void init()
    {
        txtEq = (TextView)findViewById(R.id.equation_content);
        answer = (EditText)findViewById(R.id.answer);
        btnEnd =(Button)findViewById(R.id.btnAnswerAndStopAlarm);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
    }

}
