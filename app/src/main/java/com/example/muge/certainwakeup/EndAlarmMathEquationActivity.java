package com.example.muge.certainwakeup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

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

        for(Map.Entry<String, Integer> entry : h.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            // do what you have to do here
            // In your case, an other loop.
            txtEq.setText(key);
        }
        //txtEq.setText(h[0]);

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (Integer.parseInt(answer.getText().toString())==h.get())
            }
        });


    }

    public void init()
    {
        txtEq = (TextView)findViewById(R.id.equation_content);
        answer = (EditText)findViewById(R.id.answer);
        btnEnd =(Button)findViewById(R.id.btnEnd);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
    }

}
