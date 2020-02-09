package com.example.workouttimer;

import java.util.ArrayList;

public class Routine {
    String routineName;
    String dateOfCreation;
    int nDone;
    int totTime;
    int numberOfExercises;
    ArrayList<Exercise> listExercise;

    public Routine(String routineName, String dateOfCreation, int nDone, int totTime, int numberOfExercises,
                   ArrayList<Exercise> listExercises){
        this.routineName = routineName;
        this.dateOfCreation = dateOfCreation;
        this.totTime = totTime;
        this.nDone = nDone;
        this.numberOfExercises = numberOfExercises;
        this.listExercise = listExercises;
    }

    public Routine(){

    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setnDone(int nDone) {
        this.nDone = nDone;
    }

    public void setTotTime(int totTime) {
        this.totTime = totTime;
    }

    public void setNumberOfExercises(int numberOfExercises) {
        this.numberOfExercises = numberOfExercises;
    }

    public void setListExercise(ArrayList<Exercise> listExercise) {
        this.listExercise = listExercise;
    }

    public String getRoutineName() {
        return routineName;
    }
}
