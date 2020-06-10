package com.wet.beastbots.sciencekidshelper;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 *
 * Created by johan on 6/27/18.
  */

public class ElementaryAstronomyProblemList {

    private static ElementaryAstronomyProblemList instance;

    private ElementaryAstronomyProblemList() {
        firstGradeListOfProblems.add(problem1);
        firstGradeListOfProblems.add(problem2);
        firstGradeListOfProblems.add(problem3);
        firstGradeListOfProblems.add(problem4);
        firstGradeListOfProblems.add(problem5);
        firstGradeListOfProblems.add(problem6);
        firstGradeListOfProblems.add(problem7);
        firstGradeListOfProblems.add(problem8);
        firstGradeListOfProblems.add(problem9);
        firstGradeListOfProblems.add(problem10);
        firstGradeListOfProblems.add(problem11);
        firstGradeListOfProblems.add(problem12);
        firstGradeListOfProblems.add(problem13);
        firstGradeListOfProblems.add(problem14);
        firstGradeListOfProblems.add(problem15);
        firstGradeListOfProblems.add(problem16);

        problem3Options[0] = "Earth";
        problem3Options[1] = "Mercury";
        problem3Options[2] = "Venus";
        problem3Options[3] = "Saturn";
        problem3Options[4] = null;
        problem5Options[0] = "Mars";
        problem5Options[1] = "Neptune";
        problem5Options[2] = "Jupiter";
        problem5Options[3] = "Pluto";
        problem5Options[4] = "Venus";
        problem5Options[5] = "Mercury";
        problem6Options[0] = "Yes";
        problem6Options[1] = "No";
        problem6Options[2] = null;
        problem6Options[3] = null;
        problem6Options[4] = null;
        problem8Options[0] = "Venus";
        problem8Options[1] = "Mars";
        problem8Options[2] = "Pluto";
        problem8Options[3] = "Neptune";
        problem8Options[4] = "Uranus";
        problem8Options[5] = "Earth";
        problem9Options[0] = "The Asteroid Belt";
        problem9Options[1] = "Jupiter";
        problem12Options[0] = "Oxygen";
        problem12Options[1] = "Nitrogen";
        problem12Options[2] = "Hydrogen";
        problem12Options[3] = "Helium";
        problem12Options[4] = null;
        problem12Options[5] = null;
        problem14Options[0] = "Asteroid Belt";
        problem14Options[1] = "Kuiper Belt";
        problem14Options[2] = "Oort Cloud";
        problem14Options[3] = "Eris Group";
        problem14Options[4] = "Haley's Group";
        problem14Options[5] = null;

    }


    public static ElementaryAstronomyProblemList getInstance() {
        if(instance == null)
            instance = new ElementaryAstronomyProblemList();
        return instance;
    }

    private Random rand = new Random();
    private ArrayList<ProblemModel> firstGradeListOfProblems = new ArrayList<ProblemModel>(50);
    TrueOrFalseProblemModel problem1 = new TrueOrFalseProblemModel(R.drawable.earth_photo, true, "True or false, Earth is the third planet from the sun");
    WriteInProblemModel problem2 = new WriteInProblemModel(0, "Jupiter", "Which planet is the largest in the solar system?");
    String[] problem3Options = new String[5];
    MultipleChoiceProblemModel problem3 = new MultipleChoiceProblemModel(problem3Options, R.drawable.venus_photo,3, "Which planet is this?");
    WriteInProblemModel problem4 = new WriteInProblemModel(0,"Neptune", "Which planet is farthest from the sun?");
    String[] problem5Options = new String[6];
    MultipleAnswerProblemModel problem5 = new MultipleAnswerProblemModel(problem5Options,0, 23000, "Which planets are gas giants?");
    String[] problem6Options = new String[5];
    MultipleChoiceProblemModel problem6 = new MultipleChoiceProblemModel(problem6Options, R.drawable.pluto_photo,2, "Is Pluto a planet?");
    TrueOrFalseProblemModel problem7 = new TrueOrFalseProblemModel(R.drawable.mercury_photo, false, "True or false, Mercury is the hottest planet in the solar system.");
    String[] problem8Options = new String[6];
    MultipleAnswerProblemModel problem8 = new MultipleAnswerProblemModel(problem8Options, R.drawable.titan_photo, 23450, "Which of these objects have at least 2 moons?");
    String[] problem9Options = new String[5];
    MultipleChoiceProblemModel problem9 = new MultipleChoiceProblemModel(problem9Options, R.drawable.jupiter_photo, 1, "Which is closer to the sun, the Asteroid Belt or Jupiter?");
    TrueOrFalseProblemModel problem10 = new TrueOrFalseProblemModel(R.drawable.saturn_photo, false, "True or false, Saturn has the highest amount of moons in the solar system.");
    WriteInProblemModel problem11 = new WriteInProblemModel(0, "Ceres", "What is the largest dwarf planet located in the asteroid belt?");
    String[] problem12Options = new String[6];
    MultipleAnswerProblemModel problem12 = new MultipleAnswerProblemModel(problem12Options, R.drawable.the_sun, 3400, "Which gases make up most of the sun, and fuse to create energy?");
    TrueOrFalseProblemModel problem13 = new TrueOrFalseProblemModel(R.drawable.venus_photo, false, "Venus has an iron rich surface, which can be pulled up into massive dust storms. ");
    String[] problem14Options = new String[6];
    MultipleChoiceProblemModel problem14 = new MultipleChoiceProblemModel(problem14Options, 0, 2, "Pluto is located in which group of space debris and dwarf planets?");
    WriteInProblemModel problem15 = new WriteInProblemModel(0, "Uranus", "Which gas giant has a drastic and unusual tilt?");
    TrueOrFalseProblemModel problem16 = new TrueOrFalseProblemModel(R.drawable.pluto_photo, true, "Pluto was recently visited by a probe called New Horizons, wwhich is the first probe to visit it.");


    public ProblemModel getElement(int problemIndex) {
        return firstGradeListOfProblems.get(problemIndex);
    }
    public class ProblemData {
        public ProblemModel problemModel;
        public int problemIndex;
    }




    public ProblemData selectProblem() {
        Random rand2 = new Random();
        int selectedProblemInt = rand2.nextInt(firstGradeListOfProblems.size());
        ProblemData problemData = new ProblemData();
        problemData.problemIndex = selectedProblemInt;
        problemData.problemModel = firstGradeListOfProblems.get(problemData.problemIndex);
        return problemData;
    }
    public void removeProblem(int index) {
        firstGradeListOfProblems.remove(index);
    }


}
