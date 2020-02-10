package com.example.workouttimer;

public class DbUtils {
    //table Exercises
    protected final static String EXERCISE_TABLE = "Exercises";
    protected final static String EXERCISE_NAME = "exerciseName";
    protected final static String SETS_TO_DO = "setsToDo";
    protected final static String REPS_TO_DO = "sepsToDo";
    protected final static String PREPARATION_TIME = "preparationTime";
    protected final static String WORK_TIME = "workTime";
    protected final static String REST_TIME  = "restTime";
    protected final static String COOL_DOWN_TIME = "coolDownTime";

    //table Routines
    protected final static String ROUTINES_TABLE = "Routines";
    protected final static String ROUTINE_NAME  = "routineName";
    protected final static String DATE_OF_CREATION = "dataOfCreation";
    protected final static String N_DONE = "nDone";

    //COncrete Routine
    protected final static String POSITION = "position";

    //table Favorite Routine
    protected final static String FAVORITE_ROUTINE_TABLE = "FavoriteRoutine";

    public DbUtils(){

    }
}
