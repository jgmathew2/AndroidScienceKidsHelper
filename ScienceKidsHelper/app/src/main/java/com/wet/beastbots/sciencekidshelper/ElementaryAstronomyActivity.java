package com.wet.beastbots.sciencekidshelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ElementaryAstronomyActivity extends AppCompatActivity {
    private final ElementaryAstronomyProblemList elementaryProblemList = ElementaryAstronomyProblemList.getInstance();
    private int quizProblemNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_elementary_astronomy);
        Button astronomyButton = (Button) this.findViewById(R.id.elementary_start_quiz_button);

        astronomyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ElementaryAstronomyProblemList.ProblemData problemData = elementaryProblemList.selectProblem();
                ProblemModel selectedProblem = problemData.problemModel;
                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                    Intent intent = new Intent(ElementaryAstronomyActivity.this, TrueOrFalseProblemActivity.class);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("ItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizType", "ElementaryAstronomy");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryAstronomyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof WriteInProblemModel) {
                    Intent intent = new Intent(ElementaryAstronomyActivity.this, WriteInProblemActivity.class);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizType", "ElementaryAstronomy");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryAstronomyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                    Intent intent = new Intent(ElementaryAstronomyActivity.this, MultipleChoiceProblemActivity.class);
                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("QuizType", "ElementaryAstronomy");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryAstronomyActivity.this.startActivity(intent);
                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                    Intent intent = new Intent(ElementaryAstronomyActivity.this, MultipleAnswerProblemActivity.class);
                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                    intent.putExtra("QuizType", "ElementaryAstronomy");
                    intent.putExtra("NumberOfCorrectAnswers", 0);
                    ElementaryAstronomyActivity.this.startActivity(intent);
                }
            }
        });

        Button resourcesButton = (Button) this.findViewById(R.id.elementary_resources_button);
        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElementaryAstronomyActivity.this, ElementaryAstronomyResourcesActivity.class);
                ElementaryAstronomyActivity.this.startActivity(intent);
            }
        });
    }
}


