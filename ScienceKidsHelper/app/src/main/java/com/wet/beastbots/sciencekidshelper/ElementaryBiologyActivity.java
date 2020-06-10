package com.wet.beastbots.sciencekidshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ElementaryBiologyActivity extends AppCompatActivity {
    private final ElementaryBiologyProblemList elementaryProblemList = ElementaryBiologyProblemList.getInstance();
    private int quizProblemNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementary_biology);
        Button biologyButton = (Button) this.findViewById(R.id.elementary_biology_start_quiz_button);
        biologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ElementaryBiologyProblemList.ProblemData problemData = elementaryProblemList.selectProblem();
                ProblemModel selectedProblem = problemData.problemModel;
                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                    Intent intent = new Intent(ElementaryBiologyActivity.this, TrueOrFalseProblemActivity.class);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizType", "ElementaryBiology");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryBiologyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof WriteInProblemModel) {
                    Intent intent = new Intent(ElementaryBiologyActivity.this, WriteInProblemActivity.class);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizType", "ElementaryBiology");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryBiologyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                    Intent intent = new Intent(ElementaryBiologyActivity.this, MultipleChoiceProblemActivity.class);
                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("QuizType", "ElementaryBiology");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryBiologyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                    Intent intent = new Intent(ElementaryBiologyActivity.this, MultipleAnswerProblemActivity.class);
                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("QuizType", "ElementaryBiology");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryBiologyActivity.this.startActivity(intent);
                }
            }
        });


    }

}
