package com.example.workouttimer;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class RoutineTick {
    private Context context;
    private Routine routine;
    private int totCountDown;
    private int index;
    private int phaseCountDown;
    private int setsToDo;
    private int repsToDo;
    private String stateColor;
    private ExerciseState exerciseState;

    public RoutineTick(){
        routine = new Routine();
    }

    public RoutineTick(Routine routine) {
        this.routine = routine;
        exerciseState = new PreparationPhase(this);
        exerciseState.initStateData(ExerciseState.CALL_IGNORE);
    }

    public RoutineTick(Routine routine, Context context) {
        this.routine = routine;
        this.context = context;
        index = 0;
        totCountDown = routine.getRoutineTime();
        Exercise exercise = routine.getListExercise().get(index);
        phaseCountDown = exercise.getPreparationTime();
        setsToDo = exercise.getSetsToDo();
        repsToDo = exercise.getRepsToDo();
        stateColor = "green";
        exerciseState = new PreparationPhase(this);


    }

    public void tick(){
        if(phaseCountDown < 0){
            //nothing, the system await the user command to go on
        }
        else {
            phaseCountDown--;
            totCountDown--;
            if (phaseCountDown == 0 && setsToDo < 1) {
                //exercise finished
                Toast.makeText(context, "exercise finished", Toast.LENGTH_SHORT).show();
                exerciseState.tickNextExercise();
            } else if (phaseCountDown == 0) {
                //phase finished
                exerciseState.tickNextPhase();
            }
        }
    }

    public void tickNextPhase(){
        exerciseState.tickNextPhase();
    }

    public void tickPrevPhase(){
        exerciseState.tickPrevPhase();
    }

    public void tickNextExercise(){
        exerciseState.tickNextExercise();
    }

    public void tickPrevExercise(){
        exerciseState.tickPrevExercise();
    }

    public Routine getRoutine() {
        return routine;
    }

    public int getIndex() {
        return index;
    }

    public ExerciseState getExerciseState() {
        return exerciseState;
    }

    public int getTotCountDown() {
        return totCountDown;
    }

    public int getPhaseCountDown() {
        return phaseCountDown;
    }

    public int getSetsToDo() {
        return setsToDo;
    }

    public int getRepsToDo() {
        return repsToDo;
    }

    public Context getContext() {
        return context;
    }

    public String getStateColor() {
        return stateColor;
    }

    public String getFormatTotCountDown() {
        Integer totCountDown = this.totCountDown;
        if(totCountDown < 1){
            return "---";
        }
        return totCountDown.toString();
    }

    public String getFormatCurrentExName(){
        return routine.getListExercise().get(index).getExerciseName();
    }

    public String getFormatPhaseCountDown() {
        Integer phaseCountDown = this.phaseCountDown;
        if(phaseCountDown < 1){
            return "---";
        }
        else{
            return phaseCountDown.toString();
        }
    }

    public String getFormatSetsToDo() {
        Integer setsToDo= this.setsToDo;
        if(setsToDo < 1){
            return "---";
        }
        else{
            return setsToDo.toString();
        }
    }

    public String getFormatRepsToDo() {
        Integer repsToDo= this.repsToDo;
        if(repsToDo < 1){
            return "---";
        }
        else{
            return repsToDo.toString();
        }
    }

    public void setExerciseState(ExerciseState exerciseState) {
        this.exerciseState = exerciseState;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public void setTotCountDown(int totCountDown) {
        this.totCountDown = totCountDown;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPhaseCountDown(int phaseCountDown) {
        this.phaseCountDown = phaseCountDown;
    }

    public void setSetsToDo(int setsToDo) {
        this.setsToDo = setsToDo;
    }

    public void setRepsToDo(int repsToDo) {
        this.repsToDo = repsToDo;
    }

    public void setStateColor(String stateColor) {
        this.stateColor = stateColor;
    }
}
