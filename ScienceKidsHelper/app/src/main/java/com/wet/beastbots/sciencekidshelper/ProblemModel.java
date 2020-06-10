package com.wet.beastbots.sciencekidshelper;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by johan on 6/27/18.
 */

public class ProblemModel {
    protected int problemImage;
    protected String problemText;
    public boolean isRepeated = false;
    ProblemModel() {}

    public int getProblemImage() {
        return problemImage;
    }

    public void setProblemImage(int problemImage) {
        this.problemImage = problemImage;
    }

    public String getProblemText() {
        return problemText;
    }

    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }

    ProblemModel(String problemText, int problemImage) {
        this.problemImage = problemImage;
        this.problemText = problemText;
    }
}
