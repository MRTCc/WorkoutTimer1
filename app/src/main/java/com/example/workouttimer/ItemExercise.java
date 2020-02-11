package com.example.workouttimer;

import java.io.Serializable;

public class ItemExercise{
    private Exercise exercise;

    public ItemExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getName() {
        return exercise.getExerciseName();
    }

    public void setName(String name) {
        exercise.setExerciseName(name);
    }

    public int getSetsToDo() {
        return exercise.getSetsToDo();
    }

    public void setSetsToDo(int setsToDo) {
        exercise.setSetsToDo(setsToDo);
    }

    public int getRepsToDo() {
        return exercise.getRepsToDo();
    }

    public void setRepsToDo(int repsToDo) {
        exercise.setRepsToDo(repsToDo);
    }

    public int getWorkTime() {
        return exercise.getWorkTime();
    }

    public void setWorkTime(int workTime) {
        exercise.setWorkTime(workTime);
    }

    public int getRestTime() {
        return exercise.getRestTime();
    }

    public void setRestTime(int restTime) {
        exercise.setRestTime(restTime);
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
