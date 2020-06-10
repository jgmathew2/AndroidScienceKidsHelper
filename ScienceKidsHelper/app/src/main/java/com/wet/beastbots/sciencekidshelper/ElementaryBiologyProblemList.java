package com.wet.beastbots.sciencekidshelper;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 *
 * Created by johan on 6/27/18.
  */

public class ElementaryBiologyProblemList extends quizProblemList {

    private static ElementaryBiologyProblemList instance;

    private ElementaryBiologyProblemList() {
        firstGradeListOfProblems.add(problem1);
        firstGradeListOfProblems.add(problem2);
        firstGradeListOfProblems.add(problem3);
        firstGradeListOfProblems.add(problem4);

        problem1Options[0] = "Dinosaurs";
        problem1Options[1] = "Deer";
        problem1Options[2] = "Humans";
        problem1Options[3] = "Komodo Dragons";
        problem1Options[4] = "Shrew";
        problem1Options[5] = null;
        problem3Options[0] = "Mammals";
        problem3Options[1] = "Birds";
        problem3Options[2] = "Reptiles";
        problem3Options[3] = "Bacteria";




    }


    public static ElementaryBiologyProblemList getInstance() {
        if(instance == null)
            instance = new ElementaryBiologyProblemList();
        return instance;
    }

    private Random rand = new Random();
    private ArrayList<ProblemModel> firstGradeListOfProblems = new ArrayList<ProblemModel>(50);
    String[] problem1Options = new String[6];
    MultipleAnswerProblemModel problem1 = new MultipleAnswerProblemModel(problem1Options, 0,  23050,
            "Which of these animals are mammals?");
    TrueOrFalseProblemModel problem2 = new TrueOrFalseProblemModel(0 ,false ,
            "Humans are most closely related to gorillas on the animal tree");
    String[] problem3Options = new String[4];
    MultipleChoiceProblemModel problem3 = new MultipleChoiceProblemModel(problem3Options , 0, 4,
            "Which of these organisms came to be first?");
    WriteInProblemModel problem4 = new WriteInProblemModel(0, "",
            "What molecule is the genetic code of an organism?");



    public ProblemModel getElement(int problemIndex) {
        return firstGradeListOfProblems.get(problemIndex);
    }



    public ProblemData selectProblem() {
        int selectedProblemInt;
        selectedProblemInt = rand.nextInt(firstGradeListOfProblems.size());
        ProblemData problemData = new ProblemData();
        problemData.problemModel = firstGradeListOfProblems.get(selectedProblemInt);
        problemData.problemIndex = selectedProblemInt;
        return problemData;
    }
    public void removeProblem(int index) {
        firstGradeListOfProblems.remove(index);
    }
    public class ProblemData {
        public ProblemModel problemModel;
        public int problemIndex;
    }

}
