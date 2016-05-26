package com.example.muge.certainwakeup;

import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by muge on 8.5.2016.
 */

public class RandomMathEquationGenerator {
    private DifficultyLevel state;

    public DifficultyLevel getState() {
        return state;
    }

    public void setState(DifficultyLevel state) {
        this.state = state;
    }
    public RandomMathEquationGenerator(DifficultyLevel state)
    {
        this.state=state;
    }

    public HashMap<String,Integer> generateEquation()
    {

        HashMap<String,Integer> h;
        Random r = new Random();
        if (getState().equals(DifficultyLevel.EASY))
        {
            h = easyMathEquation(r);
        }
        else if (getState().equals(DifficultyLevel.NORMAL))
        {
            h = normalMathEquation(r);
        }
        else
        {
            h = difficultMathEquation(r);
        }
        return h;
    }

    private HashMap<String,Integer> easyMathEquation(Random r)
    {
        int num1,num2,num3,op;
        String equation = "";
        int answer;
        HashMap<String,Integer> h = new HashMap<>(1);
        num1 = r.nextInt(10);
        num2 = r.nextInt(10);
        op = r.nextInt(3);
        if (op==1)//toplama
        {
            answer = num1+num2;
            equation = num1+" + "+num2+" = ";
        }
        else if (op==2)//çıkarma
        {
            answer = num1-num2;
            equation = num1+" - "+num2+" = ";
        }
        else//çarpma
        {
            answer = num1*num2;
            equation = num1+" * "+num2+" = ";
        }
        h.put(equation,answer);
        return h;
    }

    private HashMap<String,Integer> normalMathEquation(Random r)
    {
        int num1,num2,num3,op1,op2;
        String equation = "";
        int answer;
        HashMap<String,Integer> h = new HashMap<>(1);
        num1 = r.nextInt(10);
        num2 = r.nextInt(10);
        num3 = r.nextInt(10);
        op1 = r.nextInt(3);
        op2 = r.nextInt(3);
        if (op1==3||op2==3)//eğer çarpma varsa num3 dahil edilmesin
        {
            answer = num1*num2;
            equation = num1+" * "+num2+" = ";
        }
        else{
            if (op1==1&&op2==1)//toplama-toplama
            {
                answer = num1+num2;
                equation = num1+" + "+num2+" + "+num3+" = ";
            }
            else if (op1==1&&op2==2)//toplama-çıkarma
            {
                answer = num1-num2;
                equation = num1+" + "+num2+" - "+num3+" = ";
            }
            else if (op1==2 && op2==1)//çıkarma-toplama
            {
                answer = num1*num2;
                equation = num1+" - "+num2+" + "+num3+" = ";
            }
            else//çıkarma-çıkarma
            {
                answer = num1-num2-num3;
                equation = num1+" - "+num2+" - "+num3+" = ";
            }
        }
        h.put(equation,answer);
        return h;
    }

    private HashMap<String,Integer> difficultMathEquation(Random r)
    {
        int num1,num2,num3,op1,op2;
        String equation = "";
        int answer;
        HashMap<String,Integer> h = new HashMap<>(1);
        num1 = r.nextInt(10);
        num2 = r.nextInt(10);
        num3 = r.nextInt(10);
        op1 = r.nextInt(3);
        op2 = r.nextInt(3);

        if (op1==1&&op2==1)//toplama-toplama
        {
            answer = num1+num2+num3;
            equation = num1+" + "+num2+" + "+num3+" = ";
        }
        else if (op1==1&&op2==2)//toplama-çıkarma
        {
            answer = num1+num2-num3;
            equation = num1+" + "+num2+" - "+num3+" = ";
        }
        else if (op1==1&&op2==3)//toplama-çarpma
        {
            answer = num1+num2*num3;
            equation = num1+" + "+num2+" * "+num3+" = ";
        }
        else if (op1==2 && op2==1)//çıkarma-toplama
        {
            answer = num1-num2+num3;
            equation = num1+" - "+num2+" + "+num3+" = ";
        }
        else if (op1==2 && op2==2)//çıkarma-çıkarma
        {
            answer = num1-num2-num3;
            equation = num1+" - "+num2+" - "+num3+" = ";
        }
        else if (op1==2 && op2==3)//çıkarma-çarpma
        {
            answer = num1-num2*num3;
            equation = num1+" - "+num2+" * "+num3+" = ";
        }
        else if (op1==3 && op2==1)//çarpma-toplama
        {
            answer = num1*num2+num1;
            equation = num1+" * "+num2+" + "+num3+" = ";
        }
        else if (op1==3 && op2==2)//çarpma-çıkarma
        {
            answer = num1*num2-num3;
            equation = num1+" * "+num2+" - "+num3+" = ";
        }
        else//çarpma-çarpma
        {
            answer = num1*num2*num3;
            equation = num1+" * "+num2+" * "+num3+" = ";
        }
        h.put(equation,answer);
        return h;
    }

}
