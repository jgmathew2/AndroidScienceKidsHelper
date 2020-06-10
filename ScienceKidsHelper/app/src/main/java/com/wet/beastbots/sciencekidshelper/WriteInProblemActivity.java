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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class WriteInProblemActivity extends AppCompatActivity {
    private boolean checkedAnswer;
    private int noCorrectAnswers = 0;
    private int quizProblemNumber;
    private boolean problemSkipped;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.write_in_problem_item);
        final Button revealButton = (Button) this.findViewById(R.id.reveal_button);
        final Button nextButton = (Button) this.findViewById(R.id.next_button);
        final TextView revealText = (TextView) this.findViewById(R.id.answer_display_text);
        nextButton.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);

        final WriteInProblemModel selectedProblem;
        final AlertDialog answerMessage = new AlertDialog.Builder(this).create();

        final ElementaryAstronomyProblemList elementaryProblemList = ElementaryAstronomyProblemList.getInstance();
        if(getIntent() == null)
            return;
        int writeInItemNo = getIntent().getIntExtra("WriteInItemNumber", 0);
        ProblemModel problemModel = elementaryProblemList.getElement(writeInItemNo);
        if(!(problemModel instanceof WriteInProblemModel))
            return;
        selectedProblem = (WriteInProblemModel) problemModel;
        elementaryProblemList.getElement(getIntent().getIntExtra("WriteInItemNumber", 0)).isRepeated = true;
        quizProblemNumber = getIntent().getIntExtra("QuizProblemNumber", 0);
        quizProblemNumber++;

        noCorrectAnswers = getIntent().getIntExtra("NumberOfCorrectAnswers", 0);

        TextView quizProblemDisplay = (TextView) this.findViewById(R.id.quiz_number_display);
        quizProblemDisplay.setText("Problem " + quizProblemNumber + " out of 15");


        TextView problemText = (TextView) this.findViewById(R.id.write_in_problem_text);
        problemText.setText(selectedProblem.problemText);

        ImageView problemImage = (ImageView) this.findViewById(R.id.write_in_problem_image);
        problemImage.setImageResource(selectedProblem.problemImage);

        final EditText problemGuess = (EditText) this.findViewById(R.id.guess);

        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problemSkipped = true;
                String correctAnswer = selectedProblem.getCorrectAnswer();
                revealText.setText("The correct answer is \" " + correctAnswer + "\"");
            }
        });



        final Button submitButton = (Button) this.findViewById(R.id.write_in_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedProblem.setGuess(problemGuess.getText().toString().trim());
                if(selectedProblem.getGuess().equals(selectedProblem.getCorrectAnswer()) && problemSkipped == false) {
                    answerMessage.setTitle("Correct!");
                    noCorrectAnswers++;
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    revealButton.setVisibility(View.GONE);
                }
                if(selectedProblem.getGuess().equals(selectedProblem.getCorrectAnswer()) == false && problemSkipped == false) {
                    answerMessage.setTitle("Wrong!");
                }
                if(problemSkipped == true) {
                    answerMessage.setTitle("Cheated!");
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    revealButton.setVisibility(View.GONE);
                }

                answerMessage.show();
                // Hide after some seconds
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (answerMessage.isShowing()) {
                            answerMessage.dismiss();
                        }
                    }
                };

                answerMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        handler.removeCallbacks(runnable);
                    }
                });

                handler.postDelayed(runnable, 2000);

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                ElementaryAstronomyProblemList.ProblemData problemData = elementaryProblemList.selectProblem();
                ProblemModel selectedProblem = problemData.problemModel;
                while(selectedProblem.isRepeated) {
                    problemData = elementaryProblemList.selectProblem();
                }
                returnIntent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                if (quizProblemNumber != 15) {
                    if (selectedProblem instanceof TrueOrFalseProblemModel) {
                        Intent intent = new Intent(WriteInProblemActivity.this, TrueOrFalseProblemActivity.class);
                        intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                        intent.putExtra("QuizProblemNumber", quizProblemNumber);
                        intent.putExtra("QuizType", "ElementaryAstronomy");
                        WriteInProblemActivity.this.startActivity(intent);
                    }


                    else if (selectedProblem instanceof WriteInProblemModel) {
                        Intent intent = new Intent(WriteInProblemActivity.this, WriteInProblemActivity.class);
                        intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                        intent.putExtra("QuizProblemNumber", quizProblemNumber);
                        intent.putExtra("QuizType", "ElementaryAstronomy");
                        WriteInProblemActivity.this.startActivity(intent);
                    }

                    else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                        Intent intent = new Intent(WriteInProblemActivity.this, MultipleChoiceProblemActivity.class);
                        intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                        intent.putExtra("QuizProblemNumber", quizProblemNumber);
                        intent.putExtra("QuizType", "ElementaryAstronomy");
                        WriteInProblemActivity.this.startActivity(intent);
                    }

                    else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                        Intent intent = new Intent(WriteInProblemActivity.this, MultipleAnswerProblemActivity.class);
                        intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                        intent.putExtra("QuizProblemNumber", quizProblemNumber);
                        intent.putExtra("QuizType", "ElementaryAstronomy");
                        WriteInProblemActivity.this.startActivity(intent);
                    }

                }
                else {
                    final AlertDialog quizReview = new AlertDialog.Builder(WriteInProblemActivity.this).create();
                    quizReview.setTitle("Score");
                    quizReview.setMessage("You got " + noCorrectAnswers + " out of 15 questions correct!");

                    quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            quizProblemNumber = 0;
                            ElementaryAstronomyProblemList.ProblemData problemData = elementaryProblemList.selectProblem();
                            ProblemModel selectedProblem = problemData.problemModel;
                            if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                Intent intent = new Intent(WriteInProblemActivity.this, TrueOrFalseProblemActivity.class);
                                intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                intent.putExtra("QuizType", "ElementaryAstronomy");
                                intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                WriteInProblemActivity.this.startActivity(intent);
                            } else if (selectedProblem instanceof WriteInProblemModel) {
                                Intent intent = new Intent(WriteInProblemActivity.this, WriteInProblemActivity.class);
                                intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                intent.putExtra("QuizType", "ElementaryAstronomy");
                                WriteInProblemActivity.this.startActivity(intent);
                            } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                Intent intent = new Intent(WriteInProblemActivity.this, MultipleChoiceProblemActivity.class);
                                intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                intent.putExtra("QuizType", "ElementaryAstronomy");
                                WriteInProblemActivity.this.startActivity(intent);
                            } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                Intent intent = new Intent(WriteInProblemActivity.this, MultipleAnswerProblemActivity.class);
                                intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                intent.putExtra("QuizType", "ElementaryAstronomy");
                                WriteInProblemActivity.this.startActivity(intent);
                            }

                        } });

                    quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(WriteInProblemActivity.this, MainActivity.class);
                            WriteInProblemActivity.this.startActivity(intent);

                        }});

                    quizReview.show();
                }
                finish();
            }
        });

    }
/**
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                boolean checkedAnswer1 = data.getBooleanExtra("checkedAnswer", false);
                if (checkedAnswer1 == true) {
                    finalCheckedAnswer = true;

                } else {
                    finalCheckedAnswer = false;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
                //Write your code if there's no result
            }

        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                boolean checkedAnswer2 = data.getBooleanExtra("checkedAnswer", false);
                if (checkedAnswer2 == true) {
                    finalCheckedAnswer = true;

                } else {
                    finalCheckedAnswer = false;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
                //Write your code if there's no result
            }

        }
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                boolean checkedAnswer3 = data.getBooleanExtra("checkedAnswer", false);
                if (checkedAnswer3 == true) {
                    finalCheckedAnswer = true;

                } else {
                    finalCheckedAnswer = false;
                }
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            finish();
            //Write your code if there's no result
        }


        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                boolean checkedAnswer4 = data.getBooleanExtra("checkedAnswer", false);
                if (checkedAnswer4 == true) {
                    finalCheckedAnswer = true;

                }
                else {
                    finalCheckedAnswer = false;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
                //Write your code if there's no result
            }

        }
    }
    **/

}
