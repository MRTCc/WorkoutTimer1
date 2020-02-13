package com.example.workouttimer;

import android.widget.Toast;

public class PreparationPhase extends ExerciseState {

    public PreparationPhase(RoutineTick routineTick) {
        super(routineTick);
    }

    @Override
    void initStateData(int callDirection) {
        /*
        routineTick.setTotCountDown(routineTick.getRoutine().getRoutineTime());
        int index = routineTick.getIndex();
        routineTick.setIndex(index);
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        routineTick.setPhaseCountDown(exercise.getPreparationTime());
        routineTick.setSetsToDo(exercise.getSetsToDo());
        routineTick.setRepsToDo(exercise.getRepsToDo());
        */
        if(callDirection == CALL_IGNORE){
            //called by workPhase
            Toast.makeText(routineTick.getContext(), "prepTime call_ignore", Toast.LENGTH_SHORT).show();
            routineTick.setStateColor("green");
            int index = routineTick.getIndex();
            Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
            int preparationTime = exercise.getPreparationTime();
            int oldPhaseTime = routineTick.getPhaseCountDown();
            int oldTotTime = routineTick.getTotCountDown();
            routineTick.setPhaseCountDown(preparationTime);
            routineTick.setTotCountDown(oldTotTime + preparationTime + (exercise.getWorkTime() - oldPhaseTime));
        }
        else if(callDirection == CALL_FORWARD){
            //user request to go to next exercise

        }
        else if(callDirection == CALL_BACKWARD){
            //user request to go to prev exercise
        }
        else{
            //something is wrong
        }

    }

    @Override
    void tickNextPhase() {
        ExerciseState exerciseState = new WorkPhase(routineTick);
        routineTick.setExerciseState(exerciseState);
        routineTick.getExerciseState().initStateData(CALL_IGNORE);
    }

    @Override
    void tickPrevPhase() {
        Toast.makeText(routineTick.getContext(), "There are no prev phase!", Toast.LENGTH_SHORT).show();
        int index = routineTick.getIndex();
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        int preparationTime = exercise.getPreparationTime();
        int oldPhaseTime = routineTick.getPhaseCountDown();
        int oldTotTime = routineTick.getTotCountDown();
        routineTick.setPhaseCountDown(preparationTime);
        routineTick.setTotCountDown(oldTotTime + (preparationTime - oldPhaseTime));
    }

    @Override
    void tickNextExercise() {

    }

    @Override
    void tickPrevExercise() {

    }
}
