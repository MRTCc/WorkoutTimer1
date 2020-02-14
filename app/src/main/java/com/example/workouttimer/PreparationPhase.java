package com.example.workouttimer;

import android.widget.Toast;

public class PreparationPhase extends ExerciseState {

    public PreparationPhase(RoutineTick routineTick) {
        super(routineTick);
    }

    @Override
    void initStateData(int callDirection) {
        routineTick.setStateColor("green");
        int index = routineTick.getIndex();
        Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
        int preparationTime = exercise.getPreparationTime();
        int oldPhaseTime = routineTick.getPhaseCountDown();
        int oldTotTime = routineTick.getTotCountDown();
        if(callDirection == CALL_IGNORE){
            //called by workPhase
            //Toast.makeText(routineTick.getContext(), "prepTime call_ignore", Toast.LENGTH_SHORT).show();
            routineTick.setPhaseCountDown(preparationTime);
            routineTick.setTotCountDown(oldTotTime + preparationTime + (exercise.getWorkTime() - oldPhaseTime));
        }
        else if(callDirection == CALL_FORWARD){
            //request to go to the next exercise
            Toast.makeText(routineTick.getContext(), "prepTime call_forward", Toast.LENGTH_SHORT).show();
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
            index++;
            routineTick.setIndex(index);
            Exercise exercise1 = routineTick.getRoutine().getListExercise().get(index);
            int newPreparationTime = exercise1.getPreparationTime();
            routineTick.setSetsToDo(exercise1.getSetsToDo());
            routineTick.setPhaseCountDown(newPreparationTime);
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
