package com.example.workouttimer.playWorkoutPackage;

abstract class ExerciseState {
    RoutineTick routineTick;
    final static int CALL_FORWARD = 0;
    final static int CALL_BACKWARD = 1;
    final static int CALL_IGNORE = 2;

    ExerciseState(RoutineTick routineTick){
        this.routineTick = routineTick;
    }

    abstract void initStateData(int callDirection);
    abstract void tickNextPhase();
    abstract void tickPrevPhase();
    abstract void tickNextExercise();
    abstract void tickPrevExercise();

    public void throwException(){
        throw new RuntimeException("Invalid CALL_DIRECTION");
    }
}
