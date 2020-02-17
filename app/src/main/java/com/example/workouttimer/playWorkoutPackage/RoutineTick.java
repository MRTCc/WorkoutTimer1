package com.example.workouttimer.playWorkoutPackage;

import android.content.Context;
import android.widget.Toast;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.entity.Routine;

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

    RoutineTick(){
        routine = new Routine();
    }

    RoutineTick(Routine routine, Context context) {
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

     void tick(){
        if(phaseCountDown >= 0){
            phaseCountDown--;
            totCountDown--;
            if (phaseCountDown == 0 && setsToDo < 1) {
                //exercise finished
                //Toast.makeText(context, "exercise finished", Toast.LENGTH_SHORT).show();
                exerciseState.tickNextExercise();
            } else if (phaseCountDown == 0) {
                //phase finished
                exerciseState.tickNextPhase();
            }
        }
    }

     void tickNextPhase(){
        exerciseState.tickNextPhase();
    }

     void tickPrevPhase(){
        exerciseState.tickPrevPhase();
    }

     void tickNextExercise(){
        exerciseState.tickNextExercise();
    }

     void tickPrevExercise(){
        exerciseState.tickPrevExercise();
    }

    public Routine getRoutine() {
        return routine;
    }

     int getIndex() {
        return index;
    }

     ExerciseState getExerciseState() {
        return exerciseState;
    }

     int getTotCountDown() {
        return totCountDown;
    }

     int getPhaseCountDown() {
        return phaseCountDown;
    }

    public int getSetsToDo() {
        return setsToDo;
    }

    public Context getContext() {
        return context;
    }

     String getStateColor() {
        return stateColor;
    }

     String getFormatTotCountDown() {
        Integer totCountDown = this.totCountDown;
        if(totCountDown < 1){
            return "---";
        }
        return totCountDown.toString();
    }

     String getFormatCurrentExName(){
        return routine.getListExercise().get(index).getExerciseName();
    }

     String getFormatPhaseCountDown() {
        Integer phaseCountDown = this.phaseCountDown;
        if(phaseCountDown < 1){
            return "---";
        }
        else{
            return phaseCountDown.toString();
        }
    }

     String getFormatSetsToDo() {
        Integer setsToDo= this.setsToDo;
        if(setsToDo < 1){
            return "---";
        }
        else{
            return setsToDo.toString();
        }
    }

     String getFormatRepsToDo() {
        Integer repsToDo= this.repsToDo;
        if(repsToDo < 1){
            return "---";
        }
        else{
            return repsToDo.toString();
        }
    }

     void setExerciseState(ExerciseState exerciseState) {
        this.exerciseState = exerciseState;
    }

     void setRoutine(Routine routine) {
        this.routine = routine;
    }

     void setTotCountDown(int totCountDown) {
        this.totCountDown = totCountDown;
    }

     void setIndex(int index) {
        this.index = index;
    }

     void setPhaseCountDown(int phaseCountDown) {
        this.phaseCountDown = phaseCountDown;
    }

     void setSetsToDo(int setsToDo) {
        this.setsToDo = setsToDo;
    }

     void setStateColor(String stateColor) {
        this.stateColor = stateColor;
    }
}
