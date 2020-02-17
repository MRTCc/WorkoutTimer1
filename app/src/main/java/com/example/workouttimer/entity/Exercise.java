package com.example.workouttimer.entity;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String exerciseName;
    private int setsToDo;
    private int repsToDo;
    private int preparationTime;
    private int workTime;
    private int restTime;
    private int coolDownTime;
    private int position;

    public Exercise(String exerciseName, int setsToDo, int repsToDo, int preparationTime,
                    int workTime, int restTime, int coolDownTime){
        this.exerciseName = exerciseName;
        this.setsToDo = setsToDo;
        this.repsToDo = repsToDo;
        this.preparationTime = preparationTime;
        this.workTime = workTime;
        this.restTime = restTime;
        this.coolDownTime = coolDownTime;
        this.position = 0;
    }

    public Exercise(){
        this.exerciseName = "New Exercise";
        this.setsToDo = 1;
        this.repsToDo = 0;
        this.preparationTime = 0;
        this.workTime = 0;
        this.restTime = 0;
        this.coolDownTime = 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Exercise))
            return false;
        Exercise exercise = (Exercise) obj;
        return (exercise.getExerciseName().contentEquals(getExerciseName()));

    }

    public int getExerciseTime(){
        int totTime = 0;
        if(preparationTime >= 1){
            totTime += preparationTime;
        }
        if(workTime >= 1){
            totTime += (setsToDo * workTime);
        }
        if(restTime >= 1){
            totTime += ((setsToDo - 1) * restTime);
        }
        if(coolDownTime >= 1){
            totTime += coolDownTime;
        }
        return totTime;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSetsToDo() {
        return setsToDo;
    }

    public void setSetsToDo(int setsToDo) {
        this.setsToDo = setsToDo;
    }

    public int getRepsToDo() {
        return repsToDo;
    }

    public void setRepsToDo(int repsToDo) {
        this.repsToDo = repsToDo;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}


