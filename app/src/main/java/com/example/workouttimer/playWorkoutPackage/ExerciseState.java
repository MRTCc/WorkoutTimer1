package com.example.workouttimer.playWorkoutPackage;

abstract public class ExerciseState {
    protected RoutineTick routineTick;
    protected final static int CALL_FORWARD = 0;
    protected final static int CALL_BACKWARD = 1;
    protected final static int CALL_IGNORE = 2;

    public ExerciseState(RoutineTick routineTick){
        this.routineTick = routineTick;
    }

    abstract void initStateData(int callDirection);
    abstract void tickNextPhase();
    abstract void tickPrevPhase();
    abstract void tickNextExercise();
    abstract void tickPrevExercise();
}
