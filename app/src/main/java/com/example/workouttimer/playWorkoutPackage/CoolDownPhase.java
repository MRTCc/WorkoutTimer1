package com.example.workouttimer.playWorkoutPackage;

import android.widget.Toast;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.entity.Routine;

public class CoolDownPhase extends ExerciseState {

    public CoolDownPhase(RoutineTick routineTick) {
        super(routineTick); }

    @Override
    void initStateData(int callDirection) {
        if(callDirection == CALL_FORWARD){
            //Toast.makeText(routineTick.getContext(), "coolDownTime call_ignore",
            //      Toast.LENGTH_SHORT).show();
            routineTick.setStateColor("blue");
            int index = routineTick.getIndex();
            Exercise exercise = routineTick.getRoutine().getListExercise().get(index);
            int coolDownTime = exercise.getCoolDownTime();
            int oldPhaseTime = routineTick.getPhaseCountDown();
            int oldTotTime = routineTick.getTotCountDown();
            int oldSetsToDo = routineTick.getSetsToDo();
            routineTick.setSetsToDo(oldSetsToDo - 1);
            routineTick.setPhaseCountDown(coolDownTime);
            routineTick.setTotCountDown(oldTotTime - oldPhaseTime);
        }
        else{
            //something is wrong
        }
    }

    @Override
    void tickNextPhase() {
        Toast.makeText(routineTick.getContext(), "there are no next phase",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    void tickPrevPhase() {
        int oldSetsToDo = routineTick.getSetsToDo();
        routineTick.setSetsToDo(oldSetsToDo + 1);
        ExerciseState exerciseState = new WorkPhase(routineTick);
        routineTick.setExerciseState(exerciseState);
        routineTick.getExerciseState().initStateData(CALL_BACKWARD);
    }

    @Override
    void tickNextExercise() {
        int index = routineTick.getIndex();
        Routine routine = routineTick.getRoutine();
        if(index >= (routine.getListExercise().size() - 1)){
            routineTick.setTotCountDown(-1);
            routineTick.setPhaseCountDown(-1);
            return;
        }
        ExerciseState exerciseState = new PreparationPhase(routineTick);
        routineTick.setExerciseState(exerciseState);
        routineTick.getExerciseState().initStateData(CALL_FORWARD);
    }

    @Override
    void tickPrevExercise() {
        int index = routineTick.getIndex();
        Routine routine = routineTick.getRoutine();
        if(index == 0){
            Toast.makeText(routineTick.getContext(), "there are no prev ex", Toast.LENGTH_SHORT).show();
            return;
        }
        tickPrevPhase();
        routineTick.getExerciseState().tickPrevExercise();
    }
}
