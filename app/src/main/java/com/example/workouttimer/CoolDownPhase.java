package com.example.workouttimer;

public class CoolDownPhase extends ExerciseState {

    public CoolDownPhase(RoutineTick routineTick) {
        super(routineTick); }

    @Override
    void initStateData(int callDirection) {
        if(callDirection == CALL_FORWARD){

        }
        else if(callDirection == CALL_BACKWARD){

        }
        else if(callDirection == CALL_IGNORE){

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
