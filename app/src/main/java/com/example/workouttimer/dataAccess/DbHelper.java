package com.example.workouttimer.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 class DbHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "WORKOUTTIMER_DATABASE";
    private final static int DB_VERSION = 1;

    //table Exercises
     final static String EXERCISE_TABLE = "Exercises";
     final static String EXERCISE_NAME = "exerciseName";
     final static String SETS_TO_DO = "setsToDo";
     final static String REPS_TO_DO = "sepsToDo";
     final static String PREPARATION_TIME = "preparationTime";
     final static String WORK_TIME = "workTime";
     final static String REST_TIME  = "restTime";
     final static String COOL_DOWN_TIME = "coolDownTime";
    private final static String CONSTRAINT_SETS = "check (setsToDo >= 1)";

    private final static String CREATE_TABLE_EXERCISES = "CREATE TABLE " + EXERCISE_TABLE + "(" +
            EXERCISE_NAME + " text primary key, " + SETS_TO_DO + " int, " + REPS_TO_DO + " int, " + PREPARATION_TIME + " int, " +
            WORK_TIME + " int, " + REST_TIME + " int, " + COOL_DOWN_TIME + " int, " + CONSTRAINT_SETS + ");";


    //table Routines
     final static String ROUTINES_TABLE = "Routines";
     final static String ROUTINE_NAME  = "routineName";
     final static String DATE_OF_CREATION = "dataOfCreation";
     final static String N_DONE = "nDone";

    private final static String CREATE_TABLE_ROUTINES = "create table " + ROUTINES_TABLE + "(" +
            ROUTINE_NAME + " text primary key, " + DATE_OF_CREATION + " date, " + N_DONE + " int );";


    //table ConcreteRoutines
     final static String CONCRETE_ROUTINES_TABLE = "ConcreteRoutines";
     final static String POSITION = "position";
    private final static String CONSTRAINT_1_CONCRETE_ROUTINES = "primary key(RoutineName, ExerciseName)";
    private final static String CONSTRAINT_2_CONCRETE_ROUTINES = "foreign key(RoutineName) references Routines on delete cascade";
    private final static String CONSTRAINT_3_CONCRETE_ROUTINES = "foreign key(ExerciseName) references Exercises on delete cascade";
    private final static String CONSTRAINT_4_CONCRETE_ROUTINES = "foreign key(RoutineName) references Routines on update cascade";
    private final static String CONSTRAINT_5_CONCRETE_ROUTINES = "foreign key(ExerciseName) references Exercises on update cascade";

    private final static String CREATE_TABLE_CONCRETE_ROUTINES = "create table " + CONCRETE_ROUTINES_TABLE + "(" +
            ROUTINE_NAME + " text, " + EXERCISE_NAME + " text, " + POSITION + " int, " +
            CONSTRAINT_1_CONCRETE_ROUTINES + " , " +
            CONSTRAINT_2_CONCRETE_ROUTINES + " , " + CONSTRAINT_3_CONCRETE_ROUTINES + " , " +
            CONSTRAINT_4_CONCRETE_ROUTINES + " , " + CONSTRAINT_5_CONCRETE_ROUTINES + " ); ";


    //table FavoriteRoutine
     final static String FAVORITE_ROUTINE_TABLE = "FavoriteRoutine";
    private final static String CONSTRAINT_1_FAVORITE_ROUTINE = "foreign key(RoutineName) references Routines on delete cascade";
    private final static String CONSTRAINT_2_FAVORITE_ROUTINE = "foreign key(RoutineName) references Routines on update cascade";


    private final static String CREATE_TABLE_FAVORITE_ROUTINE = "create table " + FAVORITE_ROUTINE_TABLE + " ( " +
            ROUTINE_NAME + " text primary key, " + CONSTRAINT_1_FAVORITE_ROUTINE + " , " +
            CONSTRAINT_2_FAVORITE_ROUTINE + " ); ";

     DbHelper(Context context) {
            super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_ROUTINES);
        db.execSQL(CREATE_TABLE_CONCRETE_ROUTINES);
        db.execSQL(CREATE_TABLE_FAVORITE_ROUTINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ROUTINES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_ROUTINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONCRETE_ROUTINES_TABLE);
        onCreate(db);
    }
}
