package com.example.workouttimer;

public class ItemExercise {
    private String name;
    private int setsToDo;
    private int repsToDo;
    private int workTime;
    private int restTime;

    public ItemExercise(String name) {
        this.name = name;
    }

    public ItemExercise(String name, int setsToDo, int repsToDo, int workTime, int restTime) {
        this.name = name;
        this.setsToDo = setsToDo;
        this.repsToDo = repsToDo;
        this.workTime = workTime;
        this.restTime = restTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
