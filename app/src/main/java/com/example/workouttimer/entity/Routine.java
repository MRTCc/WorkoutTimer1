package com.example.workouttimer.entity;

import com.example.workouttimer.entity.Exercise;

import java.io.Serializable;
import java.util.ArrayList;

public class Routine implements Serializable{
    private String routineName;
    private String dateOfCreation;
    private int nDone;
    private int totTime;
    private int numberOfExercises;
    private ArrayList<Exercise> listExercise;

    public Routine(String routineName, String dateOfCreation, int nDone, int numberOfExercises,
                   ArrayList<Exercise> listExercises){
        this.routineName = routineName;
        this.dateOfCreation = dateOfCreation;
        this.nDone = nDone;
        this.numberOfExercises = numberOfExercises;
        this.listExercise = listExercises;
        this.totTime = getRoutineTime();
    }

    public Routine(){
        routineName = "New Routine";
        dateOfCreation = "";
        nDone = 0;
        totTime = 0;
        numberOfExercises = 0;
        listExercise = new ArrayList<Exercise>();
    }

    public int getRoutineTime(){
        int totTime = 0;
        for (Exercise exercise : listExercise) {
            totTime += exercise.getExerciseTime();
        }
        return totTime;
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

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public int getnDone() {
        return nDone;
    }

    public int getTotTime() {
        return totTime;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }

    public ArrayList<Exercise> getListExercise() {
        return listExercise;
    }
}
