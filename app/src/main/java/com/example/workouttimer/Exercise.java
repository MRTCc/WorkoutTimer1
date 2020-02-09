package com.example.workouttimer;

public class Exercise {
    String exerciseName;
    int setsToDo;
    int repsToDo;
    int preparationTime;
    int workTime;
    int restTime;
    int coolDownTime;
    int position;
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


