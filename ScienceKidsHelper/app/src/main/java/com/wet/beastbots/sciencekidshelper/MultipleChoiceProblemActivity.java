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


public class MultipleChoiceProblemActivity extends AppCompatActivity {
    private int quizProblemNumber;
    private boolean problemSkipped;
    private boolean checkedAnswer;
    private int correctAnswer;
    private int noCorrectAnswers = 0;
    private static boolean finalCheckedAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.multiple_choice_problem_item);
        MultipleChoiceProblemModel quizSelectedProblem;
        final Button nextButton = (Button) this.findViewById(R.id.next_button);
        final String quizType = getIntent().getStringExtra("QuizType");
        final Button revealButton = (Button) this.findViewById(R.id.reveal_button);
        final Button submitButton = (Button) this.findViewById(R.id.multiple_choice_submit_button);
        final TextView answerDisplay = (TextView)  this.findViewById(R.id.answer_display_text);
        nextButton.setVisibility(View.GONE);
        final AlertDialog answerMessage = new AlertDialog.Builder(MultipleChoiceProblemActivity.this).create();
        super.onCreate(savedInstanceState);
        final MultipleChoiceProblemModel selectedProblem;

        final ElementaryAstronomyProblemList elementaryAstroProblemList = ElementaryAstronomyProblemList.getInstance();
        final ElementaryBiologyProblemList elementaryBioProblemList = ElementaryBiologyProblemList.getInstance();
        if(quizType.equals("ElementaryAstronomy")) {
            quizSelectedProblem = (MultipleChoiceProblemModel) elementaryAstroProblemList.getElement(getIntent().getIntExtra("MultipleChoiceItemNumber", 0));
            elementaryAstroProblemList.getElement(getIntent().getIntExtra("MultipleChoiceItemNumber", 0)).isRepeated = true;
            selectedProblem = quizSelectedProblem;
        }
        else if(quizType.equals("ElementaryBiology")){
            quizSelectedProblem = (MultipleChoiceProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("MultipleChoiceItemNumber", 0));
            selectedProblem = quizSelectedProblem;
        }
        else {
            quizSelectedProblem = (MultipleChoiceProblemModel) elementaryBioProblemList.getElement(getIntent().getIntExtra("MultipleChoiceItemNumber", 0));
            selectedProblem = quizSelectedProblem;
        }
        selectedProblem.isRepeated = true;
        quizProblemNumber = getIntent().getIntExtra("QuizProblemNumber", 0);
        quizProblemNumber++;

        noCorrectAnswers = getIntent().getIntExtra("NumberOfCorrectAnswers", 0);

        TextView quizProblemDisplay = (TextView) this.findViewById(R.id.quiz_number_display);
        quizProblemDisplay.setText("Problem " + quizProblemNumber + " out of 15");

        TextView problemText = (TextView) this.findViewById(R.id.multiple_choice_problem_text);
        problemText.setText(selectedProblem.getProblemText());

        ImageView problemImage = (ImageView) this.findViewById(R.id.multiple_choice_problem_image);
        problemImage.setImageResource(selectedProblem.getProblemImage());

        final RadioButton radioButton1 = (RadioButton) this.findViewById(R.id.multiple_choice_radio_button_1);
        radioButton1.setText(selectedProblem.listOfOptions[0]);

        final RadioButton radioButton2 = (RadioButton) this.findViewById(R.id.multiple_choice_radio_button_2);
        radioButton2.setText(selectedProblem.listOfOptions[1]);

        final RadioButton radioButton3 = (RadioButton) this.findViewById(R.id.multiple_choice_radio_button_3);
        if(selectedProblem.listOfOptions[2] == null) {
            radioButton3.setVisibility(View.INVISIBLE);
        }
        else {
            radioButton3.setText(selectedProblem.listOfOptions[2]);
        }

        final RadioButton radioButton4 = (RadioButton) this.findViewById(R.id.multiple_choice_radio_button_4);
        if(selectedProblem.listOfOptions[3] == null) {
            radioButton4.setVisibility(View.INVISIBLE);
        }
        else {
            radioButton4.setText(selectedProblem.listOfOptions[3]);
        }

        final RadioButton radioButton5 = (RadioButton) this.findViewById(R.id.multiple_choice_radio_button_5);
        if(selectedProblem.listOfOptions[4] == null) {
            radioButton5.setVisibility(View.INVISIBLE);
        }
        else {
            radioButton5.setText(selectedProblem.listOfOptions[4]);
        }

        correctAnswer = selectedProblem.getCorrectAnswer();

        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problemSkipped = true;
                int correctAnswer = selectedProblem.getCorrectAnswer();
                String answerDisplayText = "";
                if(correctAnswer == 1) {
                    answerDisplayText = "The correct answer is option 1";
                }
                else if(correctAnswer == 2) {
                    answerDisplayText = "The correct answer is option 2";
                }
                else if(correctAnswer == 3) {
                    answerDisplayText = "The correct answer is option 3";
                }
                else if(correctAnswer == 4) {
                    answerDisplayText = "The correct answer is option 4";
                }
                else if(correctAnswer == 5) {
                    answerDisplayText = "The correct answer is option 5";
                }
                else {
                    answerDisplayText = "Error";
                }
                answerDisplay.setText(answerDisplayText);
            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButton1.isChecked() && correctAnswer == 1
                        || radioButton2.isChecked() && correctAnswer == 2
                        || radioButton3.isChecked() && correctAnswer == 3
                        || radioButton4.isChecked() && correctAnswer == 4
                        || radioButton5.isChecked() && correctAnswer == 5)
                {
                    checkedAnswer = true;
                }
                else {
                    checkedAnswer = false;
                }
                if(checkedAnswer == true && problemSkipped == false) {
                    answerMessage.setTitle("Correct!");
                    noCorrectAnswers++;
                    nextButton.setVisibility(View.VISIBLE);
                    revealButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                }
                if(checkedAnswer == false && problemSkipped == false) {
                    answerMessage.setTitle("Wrong!");
                }
                if(problemSkipped == true) {
                    answerMessage.setTitle("Cheated!");
                    nextButton.setVisibility(View.VISIBLE);
                    revealButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
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
                if(quizType.equals("ElementaryAstronomy")) {
                    ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    while(selectedProblem.isRepeated) {
                        problemData = elementaryAstroProblemList.selectProblem();
                    }

                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        }

                    } else {
                        final AlertDialog quizReview = new AlertDialog.Builder(MultipleChoiceProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + noCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryAstronomyProblemList.ProblemData problemData = elementaryAstroProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                    intent.putExtra("QuizType", quizType);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                }

                            }
                        });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MultipleChoiceProblemActivity.this, MainActivity.class);
                                MultipleChoiceProblemActivity.this.startActivity(intent);

                            }
                        });

                        quizReview.show();

                    }
                }
                else {
                    ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                    ProblemModel selectedProblem = problemData.problemModel;
                    elementaryBioProblemList.removeProblem(problemData.problemIndex);
                    if (quizProblemNumber != 15) {
                        if (selectedProblem instanceof TrueOrFalseProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, TrueOrFalseProblemActivity.class);
                            intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof WriteInProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, WriteInProblemActivity.class);
                            intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleChoiceProblemActivity.class);
                            intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                            Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleAnswerProblemActivity.class);
                            intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                            intent.putExtra("QuizProblemNumber", quizProblemNumber);
                            intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                            intent.putExtra("QuizType", quizType);
                            MultipleChoiceProblemActivity.this.startActivity(intent);
                        }

                    } else {
                        final AlertDialog quizReview = new AlertDialog.Builder(MultipleChoiceProblemActivity.this).create();
                        quizReview.setTitle("Score");
                        quizReview.setMessage("You got " + noCorrectAnswers + " out of 15 questions correct!");

                        quizReview.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                quizProblemNumber = 0;
                                ElementaryBiologyProblemList.ProblemData problemData = elementaryBioProblemList.selectProblem();
                                ProblemModel selectedProblem = problemData.problemModel;
                                if (selectedProblem instanceof TrueOrFalseProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, TrueOrFalseProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("TrueOrFalseItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof WriteInProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, WriteInProblemActivity.class);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("WriteInItemNumber", problemData.problemIndex);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleChoiceProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleChoiceProblemActivity.class);
                                    intent.putExtra("MultipleChoiceItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                } else if (selectedProblem instanceof MultipleAnswerProblemModel) {
                                    Intent intent = new Intent(MultipleChoiceProblemActivity.this, MultipleAnswerProblemActivity.class);
                                    intent.putExtra("MultipleAnswerItemNumber", problemData.problemIndex);
                                    intent.putExtra("QuizProblemNumber", quizProblemNumber);
                                    intent.putExtra("NumberOfCorrectAnswers", noCorrectAnswers);
                                    intent.putExtra("QuizType", quizType);
                                    MultipleChoiceProblemActivity.this.startActivity(intent);
                                }

                            }
                        });

                        quizReview.setButton(AlertDialog.BUTTON_NEGATIVE, "HOME", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MultipleChoiceProblemActivity.this, MainActivity.class);
                                MultipleChoiceProblemActivity.this.startActivity(intent);

                            }
                        });

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

