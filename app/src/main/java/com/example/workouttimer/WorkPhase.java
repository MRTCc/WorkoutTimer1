package com.example.workouttimer;

import android.widget.Toast;

public class WorkPhase extends ExerciseState {


    public WorkPhase(RoutineTick routineTick ) {
        super(routineTick);
    }

    @Override
    void initStateData(int callDirection) {
        routineTick.setStateColor("green");
        int index = routineTick.getIndex();
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        int workTime = exercise.getWorkTime();
        int oldPhaseTime = routineTick.getPhaseCountDown();
        int oldTotTime = routineTick.getTotCountDown();
        if(callDirection == CALL_BACKWARD){
            //Toast.makeText(routineTick.getContext(), "workTime call_backward", Toast.LENGTH_SHORT).show();
            routineTick.setPhaseCountDown(workTime);
            routineTick.setTotCountDown(oldTotTime + workTime + (exercise.getRestTime() - oldPhaseTime));
        }
        else if(callDirection == CALL_FORWARD){
            //Toast.makeText(routineTick.getContext(), "workTime call_forward", Toast.LENGTH_SHORT).show();
            int oldSetsToDo = routineTick.getSetsToDo();
            routineTick.setSetsToDo(oldSetsToDo - 1);
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
            routineTick.setPhaseCountDown(workTime);
        }
        else if(callDirection == CALL_IGNORE){
            //Toast.makeText(routineTick.getContext(), "workTime call_ignore", Toast.LENGTH_SHORT).show();
            routineTick.setPhaseCountDown(workTime);
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
        }
    }

    @Override
    void tickNextPhase() {
        ExerciseState exerciseState;
        int setsToDo = routineTick.getSetsToDo();
        if(setsToDo == 1){
            exerciseState = new CoolDownPhase(routineTick);
            routineTick.setExerciseState(exerciseState);
            routineTick.getExerciseState().initStateData(CALL_FORWARD);
        }
        else{
            exerciseState = new RestPhase(routineTick);
            routineTick.setExerciseState(exerciseState);
            routineTick.getExerciseState().initStateData(CALL_IGNORE);
        }
    }

    @Override
    void tickPrevPhase() {
        ExerciseState exerciseState;
        int setsToDo = routineTick.getSetsToDo();
        int index = routineTick.getIndex();
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        if(setsToDo == exercise.getSetsToDo()){
            exerciseState = new PreparationPhase(routineTick);
            routineTick.setExerciseState(exerciseState);
            routineTick.getExerciseState().initStateData(CALL_IGNORE);
        }
        else{
            exerciseState = new RestPhase(routineTick);
            routineTick.setExerciseState(exerciseState);
            routineTick.getExerciseState().initStateData(CALL_BACKWARD);
        }
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
