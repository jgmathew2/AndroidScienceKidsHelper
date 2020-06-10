package com.wet.beastbots.sciencekidshelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class MultipleAnswerProblemActivity extends AppCompatActivity {
    private boolean problemSkipped = false;
    private int selectedAnswer = 0;
    private int numCorrectAnswers = 0;
    private int quizProblemNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.multiple_answer_problem_item);
        final String quizType = getIntent().getStringExtra("QuizType");
        final Button nextButton = (Button) this.findViewById(R.id.next_button);
        final Button revealButton = (Button) this.findViewById(R.id.reveal_button);
        final TextView answerDisplay = (TextView)  this.findViewById(R.id.answer_display_text);
        nextButton.setVisibility(View.GONE);
        final AlertDialog answerMessage = new AlertDialog.Builder(MultipleAnswerProblemActivity.this).create();
        super.onCreate(savedInstanceState);
        MultipleAnswerProblemModel quizSelectedProblem;
        final MultipleAnswerProblemModel selectedProblem;
        final ElementaryAstronomyProblemList elementaryAstroProblemList = ElementaryAstronomyProblemList.getInstance();
        final ElementaryBiologyProblemList elementaryBioProblemList = ElementaryBiologyProblemList.getInstance();
        if(quizType.equals("ElementaryAstronomy")) {
            quizSelectedProblem = (MultipleAnswerProblemModel) elementaryAstroProblemList.getElement(getIntent().getIntExtra("MultipleAnswerItemNumber", 0));
            elementaryAstroProblemList.getElement(getIntent().getIntExtra("MultipleAnswerItemNumber", 0)).isRepeated = true;
            selectedProblem = quizSelectedProblem;
        }
        else if(quizType.equals("ElementaryBiology")){
            quizSelectedProblem = (MultipleAnswerProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("MultipleAnswerItemNumber", 0));
            elementaryBioProblemList.getElement(getIntent().getIntExtra("MultipleAnswerItemNumber", 0)).isRepeated = true;
            selectedProblem = quizSelectedProblem;
        }
        else {
            quizSelectedProblem = (MultipleAnswerProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("MultipleAnswerItemNumber", 0));
            selectedProblem = quizSelectedProblem;
        }
        quizProblemNumber = getIntent().getIntExtra("QuizProblemNumber", 0);
        quizProblemNumber++;

        numCorrectAnswers = getIntent().getIntExtra("NumberOfCorrectAnswers", 0);

        TextView quizProblemDisplay = (TextView) this.findViewById(R.id.quiz_number_display);
        quizProblemDisplay.setText("Problem " + quizProblemNumber + " out of 15");

        TextView problemText = (TextView) this.findViewById(R.id.multiple_answer_problem_text);
        problemText.setText(selectedProblem.problemText);

        ImageView problemImage = (ImageView) this.findViewById(R.id.multiple_answer_problem_image);
        problemImage.setImageResource(selectedProblem.problemImage);

        final CheckBox checkBox1 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_1);
        checkBox1.setText(selectedProblem.listOfOptions[0]);

        final CheckBox checkBox2 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_2);
        checkBox2.setText(selectedProblem.listOfOptions[1]);

        final CheckBox checkBox3 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_3);
        checkBox3.setText(selectedProblem.listOfOptions[2]);

        final CheckBox checkBox4 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_4);
        if(selectedProblem.listOfOptions[3] == null) {
            checkBox4.setVisibility(View.INVISIBLE);
        }
        else {
            checkBox4.setText(selectedProblem.listOfOptions[3]);
        }
        checkBox4.setText(selectedProblem.listOfOptions[3]);

        final CheckBox checkBox5 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_5);
        checkBox5.setText(selectedProblem.listOfOptions[4]);

        final CheckBox checkBox6 = (CheckBox) this.findViewById(R.id.multiple_answer_checkbox_6);
        checkBox6.setText(selectedProblem.listOfOptions[5]);

        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problemSkipped = true;
                int correctAnswer = selectedProblem.getCorrectAnswer();
                String answerDisplayText;
                answerDisplayText = "";

                if(correctAnswer / 100000 >= 1) {
                    correctAnswer -= 100000;
                    answerDisplayText = "Answer 1 is correct";
                }
                if(correctAnswer / 20000 >= 1) {
                    correctAnswer -= 20000;
                    answerDisplayText = answerDisplayText + "\n"  + "Answer 2 is correct";
                }
                if(correctAnswer / 3000 >= 1) {
                    correctAnswer -= 3000;
                    answerDisplayText = answerDisplayText + "\n" + "Answer 3 is correct";
                }
                if(correctAnswer / 400 >= 1) {
                    correctAnswer -= 400;
                    answerDisplayText = answerDisplayText + "\n" + "Answer 4 is correct";
                }
                if(correctAnswer / 50 >= 1) {
                    correctAnswer -= 50;
                    answerDisplayText = answerDisplayText + "\n" + "Answer 5 is correct";
                }
                if(correctAnswer / 6 == 1) {
                    answerDisplayText = answerDisplayText + "\n" + "Answer 6 is correct";
                }

                answerDisplay.setText(answerDisplayText);
            }
        });


        final Button submitButton = (Button) this.findViewById(R.id.multiple_answer_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox1.isChecked()) {
                    selectedAnswer += 100000;
                }
                if (checkBox2.isChecked()) {
                    selectedAnswer = selectedAnswer + 20000;
                }
                if (checkBox3.isChecked()) {
                    selectedAnswer = selectedAnswer + 3000;
                }
                if (checkBox4.isChecked()) {
                    selectedAnswer = selectedAnswer + 400;
                }
                if (checkBox5.isChecked()) {
                    selectedAnswer = selectedAnswer + 50;
                }
                if (checkBox6.isChecked()) {
                    selectedAnswer = selectedAnswer + 6;
                }

                if(selectedAnswer == selectedProblem.getCorrectAnswer() && problemSkipped == false) {
                    answerMessage.setTitle("Correct!");
                    numCorrectAnswers++;
                    nextButton.setVisibility(View.VISIBLE);
                    revealButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                }
                else if(selectedAnswer != selectedProblem.getCorrectAnswer() && problemSkipped == false) {
                    answerMessage.setTitle("Wrong");
                }
                else if(problemSkipped == true) {
                    answerMessage.setTitle("Cheated!");
                    nextButton.setVisibility(View.VISIBLE);
                    revealButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                }
                else {
                    answerMessage.setTitle("Error!");
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
                if(quizType.equals("ElementaryAstronomy")) {
                    ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    while(selectedProblem.isRepeated) {
                        problemData = elementaryAstroProblemList.selectProblem();
                    }

                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }


                        else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                    }
                    else {
                        final AlertDialog quizReview = new AlertDialog.Builder(MultipleAnswerProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + numCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                    intent.putExtra("QuizType", quizType);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                }

                            } });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MultipleAnswerProblemActivity.this, MainActivity.class);
                                MultipleAnswerProblemActivity.this.startActivity(intent);

                            }});

                        quizReview.show();

                    }
                }
                else {
                    ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }


                        else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                        else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleAnswerProblemActivity.this.startActivity(intent);
                        }

                    }
                    else {
                        final AlertDialog quizReview = new AlertDialog.Builder(MultipleAnswerProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + numCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(MultipleAnswerProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", numCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleAnswerProblemActivity.this.startActivity(intent);
                                }

                            } });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MultipleAnswerProblemActivity.this, MainActivity.class);
                                MultipleAnswerProblemActivity.this.startActivity(intent);

                            }});

                        quizReview.show();

                    }
                }
                finish();

            }
        });


    }


}

