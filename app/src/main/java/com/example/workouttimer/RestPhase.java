package com.example.workouttimer;

import android.widget.Toast;

public class RestPhase extends ExerciseState {

    public RestPhase(RoutineTick routineTick) {
        super(routineTick);
    }

    @Override
    void initStateData(int callDirection) {
        routineTick.setStateColor("green");
        int index = routineTick.getIndex();
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        int workTime = exercise.getWorkTime();
        int restTime = exercise.getRestTime();
        int setsToDo = routineTick.getSetsToDo();
        int oldPhaseTime = routineTick.getPhaseCountDown();
        int oldTotTime = routineTick.getTotCountDown();
        //call_forward isn't needed
        if(callDirection == CALL_IGNORE){
            //Toast.makeText(routineTick.getContext(), "restTime call_ignore", Toast.LENGTH_SHORT).show();
            routineTick.setPhaseCountDown(restTime);
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
        }
        else if(callDirection == CALL_BACKWARD){
            //Toast.makeText(routineTick.getContext(), "restTime call_backward", Toast.LENGTH_SHORT).show();
            routineTick.setSetsToDo(setsToDo + 1);
            routineTick.setTotCountDown(oldTotTime + restTime + (workTime - oldPhaseTime));
        }
        else{
            //something is wrong
        }
    }

    @Override
    void tickNextPhase() {
        ExerciseState exerciseState;
        exerciseState = new WorkPhase(routineTick);
        routineTick.setExerciseState(exerciseState);
        routineTick.getExerciseState().initStateData(CALL_FORWARD);
    }

    @Override
    void tickPrevPhase() {
        ExerciseState exerciseState;
        exerciseState = new WorkPhase(routineTick);
        routineTick.setExerciseState(exerciseState);
        routineTick.getExerciseState().initStateData(CALL_BACKWARD);
    }

    @Override
    void tickNextExercise() {
        int index = routineTick.getIndex();
        Routine routine = routineTick.getRoutine();
        if(index >= (routine.getListExercise().size() - 1)){
            Toast.makeText(routineTick.getContext(), "there are no next ex", Toast.LENGTH_SHORT).show();
            return;
        }
        tickNextPhase();
        routineTick.getExerciseState().tickNextExercise();
    }

    @Override
    void tickPrevExercise() {

    }
}