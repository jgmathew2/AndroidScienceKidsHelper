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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


public class TrueOrFalseProblemActivity extends AppCompatActivity {
    private int quizProblemNumber = 0;
    private int noCorrectAnswers = 0;
    private boolean selectedAnswer;
    private boolean checkedAnswer;
    private boolean problemSkipped = false;
    private static boolean finalCheckedAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.true_or_false_problem_item);
        final Button revealButton = (Button) this.findViewById(R.id.reveal_button);
        final Button nextButton = (Button) this.findViewById(R.id.next_button);
        final TextView revealText = (TextView) this.findViewById(R.id.answer_display_text);
        nextButton.setVisibility(View.GONE);
        final AlertDialog answerMessage = new AlertDialog.Builder(TrueOrFalseProblemActivity.this).create();
        final TrueOrFalseProblemModel selectedProblem;
        final String quizType = getIntent().getStringExtra("QuizType");
        TrueOrFalseProblemModel quizSelectedProblem;
        super.onCreate(savedInstanceState);


        final ElementaryAstronomyProblemList elementaryAstroProblemList = ElementaryAstronomyProblemList.getInstance();
        final ElementaryBiologyProblemList elementaryBioProblemList = ElementaryBiologyProblemList.getInstance();
        if(quizType.equals("ElementaryAstronomy")) {
            quizSelectedProblem = (TrueOrFalseProblemModel) elementaryAstroProblemList.getElement(getIntent().getIntExtra("TrueOrFalseItemNumber", 0));
            elementaryAstroProblemList.getElement(getIntent().getIntExtra("TrueOrFalseItemNumber", 0)).isRepeated = true;
            selectedProblem = quizSelectedProblem;
        }
        else if(quizType.equals("ElementaryBiology")){
            quizSelectedProblem = (TrueOrFalseProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("TrueOrFalseItemNumber", 0));
            selectedProblem = quizSelectedProblem;
        }
        else {
            quizSelectedProblem = (TrueOrFalseProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("TrueOrFalseItemNumber", 0));
            selectedProblem = quizSelectedProblem;
        }
        selectedProblem.isRepeated = true;
        noCorrectAnswers = getIntent().getIntExtra("NumberOfCorrectAnswers", 0);
        quizProblemNumber = getIntent().getIntExtra("QuizProblemNumber", 0);
        quizProblemNumber++;

        TextView quizProblemDisplay = (TextView) this.findViewById(R.id.quiz_number_display);
        quizProblemDisplay.setText("Problem " + quizProblemNumber + " out of 15");

        TextView problemText = (TextView) this.findViewById(R.id.true_or_false_problem_text);
        problemText.setText(selectedProblem.problemText);

        ImageView problemImage = (ImageView) this.findViewById(R.id.true_or_false_problem_image);
        problemImage.setImageResource(selectedProblem.problemImage);

        final RadioButton trueButton = (RadioButton) this.findViewById(R.id.true_button);
        final RadioButton falseButton = (RadioButton) this.findViewById(R.id.false_button);


        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problemSkipped = true;
                boolean correctAnswer = selectedProblem.getCorrectAnswer();
                if(correctAnswer) {
                    revealText.setText("The correct answer is true");
                }
                else {
                    revealText.setText("The correct answer is false");
                }

            }
        });

        final Button submitButton = (Button) this.findViewById(R.id.true_or_false_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trueButton.isChecked())  {
                    selectedAnswer = true;
                }
                if(falseButton.isChecked()) {
                    selectedAnswer = false;
                }
                if(selectedAnswer == selectedProblem.getCorrectAnswer() && problemSkipped == false) {
                    noCorrectAnswers++;
                    answerMessage.setTitle("Correct!");
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    revealButton.setVisibility(View.GONE);
                }
                else if(selectedAnswer != selectedProblem.getCorrectAnswer() && problemSkipped == false) {
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
                returnIntent.putExtra("checkedAnswer", checkedAnswer);
                returnIntent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                setResult(Activity.RESULT_OK, returnIntent);
                if(quizType.equals("ElementaryAstronomy")) {
                    ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    while(selectedProblem.isRepeated) {
                        problemData = elementaryAstroProblemList.selectProblem();
                    }
                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryAstronomy");
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }


                        else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryAstronomy");
                            TrueOrFalseProblemActivity.this.startActivity(intent);

                        }

                        else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryAstronomy");
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }

                    }
                    else {
                        final AlertDialog quizReview = new AlertDialog.Builder(TrueOrFalseProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + noCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizType", "ElementaryAstronomy");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizType", "ElementaryAstronomy");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("QuizType", "ElementaryAstronomy");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("QuizType", "ElementaryAstronomy");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                }

                            } });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(TrueOrFalseProblemActivity.this, MainActivity.class);
                                TrueOrFalseProblemActivity.this.startActivity(intent);

                            }});
                        quizReview.show();

                    }
                }
                else {
                    ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryBiology");
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }


                        else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryBiology");
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryBiology");
                            TrueOrFalseProblemActivity.this.startActivity(intent);

                        }

                        else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("QuizType", "ElementaryBiology");
                            TrueOrFalseProblemActivity.this.startActivity(intent);
                        }

                    }
                    else {
                        final AlertDialog quizReview = new AlertDialog.Builder(TrueOrFalseProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + noCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizType", "ElementaryBiology");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizType", "ElementaryBiology");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("QuizType", "ElementaryBiology");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(TrueOrFalseProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("QuizType", "ElementaryBiology");
                                    TrueOrFalseProblemActivity.this.startActivity(intent);
                                }

                            } });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(TrueOrFalseProblemActivity.this, MainActivity.class);
                                TrueOrFalseProblemActivity.this.startActivity(intent);

                            }});
                        quizReview.show();

                    }
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