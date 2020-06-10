package com.wet.beastbots.sciencekidshelper;

import java.util.List;

/**
 * Created by johan on 6/30/18.
 */

public class MultipleAnswerProblemModel extends ProblemModel {
    private int correctAnswer;
    public String[] listOfOptions;

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    MultipleAnswerProblemModel(String[] listOfOptions, int problemImage, int correctAnswer, String problemText)
    {
        super.problemImage = problemImage;
        super.problemText = problemText;
        this.correctAnswer = correctAnswer;
        this.listOfOptions = listOfOptions;

    }
    /** private boolean answerCheckBox1;
    private boolean answerCheckBox2;
    private boolean answerCheckBox3;
    private boolean answerCheckBox4;
    public String checkAnswer() {
        if(answerCheckBox1 == true && correctAnswer == 1) {
            return "Correct!";
        }
        else if(answerCheckBox2 == true && correctAnswer == 2) {
            return "Correct!";
        }
        else if(answerCheckBox3 == true && correctAnswer == 3) {
            return "Correct!";
        }
        else if(answerCheckBox4 == true && correctAnswer == 4) {
            return "Correct!";
        }
        else if(correctAnswer > 4) {
            return "Error";
        }
        else if(answerCheckBox1 == false && answerCheckBox2 == false && answerCheckBox3 == false && answerCheckBox4 == false) {
            return "You must select an answer.";
        }
        else {
            return "incorrect";
        }
    }
     */
}
