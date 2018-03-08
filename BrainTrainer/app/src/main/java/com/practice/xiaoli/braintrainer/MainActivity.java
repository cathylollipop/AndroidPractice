package com.practice.xiaoli.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int correctPos;
    TextView resultTextView;
    TextView pointsTextView;
    Button playAgainButton;
    int score = 0;
    int numberOfQuestions = 0;
    TextView sumTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    CountDownTimer countDownTimer;
    TextView timerTextView;
    RelativeLayout gameRelativeLayout;

    public void reset(View view){
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateNewQuestion();

        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your score is: " + Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(correctPos))){
            resultTextView.setText("Correct!");
            score++;

        }else
            resultTextView.setText("Wrong!");

        pointsTextView.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
        numberOfQuestions++;
        generateNewQuestion();
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        reset(findViewById(R.id.playAgainButton));
    }

    public void generateNewQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        int correctAnswer = a + b;
        correctPos = rand.nextInt(4);

        answers.clear();

        for(int i = 0; i < 4; i++){
            if(i == correctPos)
                answers.add(correctAnswer);
            else{
                int incorrectAnswer = rand.nextInt(41);
                Set<Integer> set = new HashSet<>();
                while(incorrectAnswer == correctAnswer || !set.add(incorrectAnswer))
                    incorrectAnswer = rand.nextInt(41);

                answers.add(incorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);

        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
    }
}
