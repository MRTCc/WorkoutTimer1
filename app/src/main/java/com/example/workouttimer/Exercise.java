package com.example.workouttimer;

public class Exercise {
    String exerciseName;
    int setsToDo;
    int repsToDo;
    int preparationTime;
    int workTime;
    int restTime;
    int coolDownTime;

    public Exercise(String exerciseName, int setsToDo, int repsToDo, int preparationTime,
                    int workTime, int restTime, int coolDownTime){
        this.exerciseName = exerciseName;
        this.setsToDo = setsToDo;
        this.repsToDo = repsToDo;
        this.preparationTime = preparationTime;
        this.workTime = workTime;
        this.restTime = restTime;
        this.coolDownTime = coolDownTime;
    }
}
