package com.wet.beastbots.sciencekidshelper;

/**
 * Created by johan on 3/14/19.
 */

public class quizProblemList {
    private static quizProblemList instance;

    public static quizProblemList getInstance() {
        if(instance == null)
            instance = new quizProblemList();
        return instance;
    }

    public class ProblemData {
        public ProblemModel problemModel;
        public int problemIndex;
    }


}
