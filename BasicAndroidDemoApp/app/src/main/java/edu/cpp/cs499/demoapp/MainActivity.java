package edu.cpp.cs499.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.cpp.cs499.demoapp.model.QuestionSet;

public class MainActivity extends AppCompatActivity {

    private QuestionSet questionSet;
    private int currentQuestionIndex;

    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TEST", "onCreate");

        initQuestionSet();

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionSet.getQuestion(currentQuestionIndex).getTitle());

        trueButton = findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(true);
            }
        });

        falseButton = findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(false);
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionSet.getSize();
                questionTextView.setText(questionSet.getQuestion(currentQuestionIndex).getTitle());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TEST", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("TEST", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TEST", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TEST", "onDestory");
    }

    private void checkCorrectAnswer(boolean chosenAnswer) {
        if (questionSet.getQuestion(currentQuestionIndex).isCorrectAnswer() == chosenAnswer) {
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initQuestionSet() {
        questionSet = new QuestionSet();
        questionSet.addQuestion("CPP has the largest campus in the CSU system.", false);
        questionSet.addQuestion("NYC is the largest city in the US.", true);
        questionSet.addQuestion("CS is the hottest major currently.", true);
        questionSet.addQuestion("CS499 is not the best course in CS @ CPP.", false);
        questionSet.addQuestion("Everyone in CS499 will publish an app in Google Play", true);
        questionSet.addQuestion("Everyone in CS480 will publish an app in Google Play", false);
        currentQuestionIndex = 0;
    }

}
