package com.wet.beastbots.sciencekidshelper;

import java.util.List;

/**
 * Created by johan on 6/30/18.
 */

public class TrueOrFalseProblemModel extends ProblemModel {
    boolean correctAnswer;
    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    TrueOrFalseProblemModel(int problemImage, boolean correctAnswer, String problemText)
    {
        super.problemImage = problemImage;
        super.problemText = problemText;
        this.correctAnswer = correctAnswer;
    }

}
