package com.example.workouttimer;

public class ItemRoutine {
    private String name;
    private int type;

    public ItemRoutine(String n, int type) {
        name = n;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
