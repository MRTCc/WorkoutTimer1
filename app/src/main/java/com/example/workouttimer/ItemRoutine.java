package com.example.workouttimer;

public class ItemRoutine {
    private String name;
    private int type;
    private Routine routine;

    public ItemRoutine(Routine routine, int type){
        this.routine = routine;
        this.type = type;
    }

    public ItemRoutine(String n, int type) {
        name = n;
        this.type = type;
    }
    public String getName() {
        return routine.getRoutineName();
    }

    public void setName(String name) {
        routine.setRoutineName(name);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
