package com.wet.beastbots.sciencekidshelper;

import java.util.List;

/**
 * Created by johan on 6/30/18.
 */

public class WriteInProblemModel extends ProblemModel {
    private String correctAnswer;
    private String guess;

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }



    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    WriteInProblemModel(int problemImage, String correctAnswer, String problemText)
    {
        super.problemImage = problemImage;
        super.problemText = problemText;
        this.correctAnswer = correctAnswer;
    }
}
