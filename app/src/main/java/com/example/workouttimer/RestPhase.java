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
            //TODO: test
            Toast.makeText(routineTick.getContext(), "restTime call_ignore", Toast.LENGTH_SHORT).show();
            routineTick.setPhaseCountDown(restTime);
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
        }
        else if(callDirection == CALL_BACKWARD){
            //TODO: test
            Toast.makeText(routineTick.getContext(), "restTime call_backward", Toast.LENGTH_SHORT).show();
            routineTick.setSetsToDo(setsToDo + 1);
            routineTick.setTotCountDown(oldTotTime + restTime + (workTime - oldPhaseTime));
        }
        else{
            //something is wrong
        }
    }

    @Override
    void tickNextPhase() {

    }

    @Override
    void tickPrevPhase() {

    }

    @Override
    void tickNextExercise() {

    }

    @Override
    void tickPrevExercise() {

    }
}
