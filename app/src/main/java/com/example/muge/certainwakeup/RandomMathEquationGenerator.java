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
            if(num1>num2) {
                answer = num1 - num2;
                equation = num1 + " - " + num2 + " = ";
            }else{
                answer = num2 - num1;
                equation = num2 + " - " + num1 + " = ";
            }
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
                answer = num1+num2+num3;
                equation = num1+" + "+num2+" + "+num3+" = ";
            }
            else if (op1==1&&op2==2)//toplama-çıkarma
            {
                answer = num1+num2-num3;
                if (answer>0)
                    equation = num1+" + "+num2+" - "+num3+" = ";
                else//num3>(num1+num2) ise num3 ve num1 yeri değiştirilsin
                {
                    answer = num3+num2-num1;
                    equation = num3+" + "+num2+" - "+num1+" = ";
                }
            }
            else if (op1==2 && op2==1)//çıkarma-toplama
            {
                answer = num1-num2+num3;
                if (answer>0)
                    equation = num1+" - "+num2+" + "+num3+" = ";
                else//num1<(num2+num3) ise
                {
                    if (num2>num1)
                    {
                        answer = num2-num1+num3;
                        equation = num2+" - "+num1+" + "+num3+" = ";
                    }
                    else if (num3>num1)
                    {
                        answer = num3-num2+num1;
                        equation = num3+" - "+num2+" + "+num1+" = ";
                    }
                    else//tekrar random üretimi yapacak
                    {
                        normalMathEquation(r);
                    }
                }
            }
            else//çıkarma-çıkarma
            {
                answer = num1-num2-num3;
                if (answer>0)
                    equation = num1+" - "+num2+" - "+num3+" = ";
                else//eğer cevap 0 dan küçük gelirse tekrar random üretim yapsın(çok fazla kontrol var)
                {
                    normalMathEquation(r);
                }
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
            num1 = r.nextInt(20);
            num2 = r.nextInt(20);
            num3 = r.nextInt(20);
            answer = num1+num2+num3;
            equation = num1+" + "+num2+" + "+num3+" = ";
        }
        else if (op1==1&&op2==2)//toplama-çıkarma
        {
            num1 = r.nextInt(20);
            num2 = r.nextInt(20);
            answer = num1+num2-num3;
            if (answer>0)
                equation = num1+" + "+num2+" - "+num3+" = ";
            else{
                if (num1<num3)
                {
                    answer=num3+num2-num1;
                    equation = num3+" + "+num2+" - "+num1+" = ";
                }
                else if(num2<num3)
                {
                    answer=num1+num3-num2;
                    equation = num1+" + "+num3+" - "+num2+" = ";
                }
                else
                    normalMathEquation(r);
            }
        }
        else if (op1==1&&op2==3)//toplama-çarpma
        {
            num1 = r.nextInt(20);
            answer = num1+num2*num3;
            equation = num1+" + "+num2+" * "+num3+" = ";
        }
        else if (op1==2 && op2==1)//çıkarma-toplama
        {
            num1 = r.nextInt(20);
            answer = num1-num2+num3;
            if (answer>0)
                equation = num1+" - "+num2+" + "+num3+" = ";
            else if(num2>num1)
            {
                answer = num2-num1+num3;
                equation = num2+" - "+num1+" + "+num3+" = ";
            }
            else if (num3>num1)
            {
                answer = num3-num2+num1;
                equation = num3+" - "+num2+" + "+num1+" = ";
            }
            else
                normalMathEquation(r);
        }
        else if (op1==2 && op2==2)//çıkarma-çıkarma
        {
            num1 = r.nextInt(30);
            num2 = r.nextInt(15);
            answer = num1-num2-num3;
            if (answer>0)
                equation = num1+" - "+num2+" - "+num3+" = ";
            else
                normalMathEquation(r);
        }
        else if (op1==2 && op2==3)//çıkarma-çarpma
        {
            num1 = r.nextInt(30);
            answer = num1-num2*num3;
            if (answer>0)
                equation = num1+" - "+num2+" * "+num3+" = ";
            else
                normalMathEquation(r);
        }
        else if (op1==3 && op2==1)//çarpma-toplama
        {
            num3 = r.nextInt(20);
            answer = num1*num2+num1;
            equation = num1+" * "+num2+" + "+num3+" = ";
        }
        else if (op1==3 && op2==2)//çarpma-çıkarma
        {
            answer = num1*num2-num3;
            if (answer>0)
                equation = num1+" * "+num2+" - "+num3+" = ";
            else
                normalMathEquation(r);
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
