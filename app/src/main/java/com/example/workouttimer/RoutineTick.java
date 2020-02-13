package com.example.workouttimer;

public class RoutineTick {
    private Routine routine;
    private int totCountDown;
    private int index;
    private int phaseCountDown;
    private int setsToDo;
    private int repsToDo;

    public RoutineTick(){
        routine = new Routine();
    }

    public RoutineTick(Routine routine) {
        this.routine = routine;
        totCountDown = routine.getRoutineTime();
        index = 0;
        Exercise exercise = routine.getListExercise().get(index);
        phaseCountDown = exercise.getPreparationTime();
        setsToDo = exercise.getSetsToDo();
        repsToDo = exercise.getRepsToDo();
    }

    public void tick(){
        phaseCountDown--;
        totCountDown--;
        if(phaseCountDown == 0){
            //phase finished
        }
        else if(totCountDown == 0){
            //exercise finished
        }
    }

    public String getTotCountDown() {
        Integer totCountDown = this.totCountDown;
        return totCountDown.toString();
    }

    public String getActualExName(){
        return routine.getListExercise().get(index).getExerciseName();
    }

    public String getPhaseCountDown() {
        Integer phaseCountDown = this.phaseCountDown;
        if(phaseCountDown < 1){
            return "---";
        }
        else{
            return phaseCountDown.toString();
        }
    }

    public String getSetsToDo() {
        Integer setsToDo= this.setsToDo;
        if(setsToDo < 1){
            return "---";
        }
        else{
            return setsToDo.toString();
        }
    }

    public String getRepssToDo() {
        Integer repsToDo= this.repsToDo;
        if(repsToDo < 1){
            return "---";
        }
        else{
            return repsToDo.toString();
        }
    }
}
