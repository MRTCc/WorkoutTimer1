package com.example.workouttimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "WORKOUTTIMER_DATABASE";
    private final static int DB_VERSION = 1;


    private final static String EXERCISE_TABLE = "Exercises";
    private final static String EXERCISE_NAME = "exerciseName";
    private final static String SETS_TO_DO = "setsToDo";
    private final static String REPS_TO_DO = "sepsToDo";
    private final static String PREPARATION_TIME = "preparationTime";
    private final static String WORK_TIME = "workTime";
    private final static String REST_TIME  = "restTime";
    private final static String COOL_DOWN_TIME = "coolDownTime";
    private final static String CONSTRAINT_SETS = "check (sets >= 1)";

    private final static String CREATE_TABLE_EXERCISES = "CREATE TABLE " + EXERCISE_TABLE + "(" +
            EXERCISE_NAME + "text primary key," + SETS_TO_DO + "int," + REPS_TO_DO + "int," + PREPARATION_TIME + "int," +
            WORK_TIME + "int," + REST_TIME + "int," + COOL_DOWN_TIME + "int," + CONSTRAINT_SETS + ");";


    private final static String ROUTINES_TABLE = "Routines";
    private final static String ROUTINE_NAME  = "routineName";
    private final static String DATE_OF_CREATION = "dataOfCreation";
    private final static String N_DONE = "nDone";

    private final static String CREATE_TABLE_ROUTINES = "create table " + ROUTINES_TABLE + "(" +
            ROUTINE_NAME + "text primary key," + DATE_OF_CREATION + "date," + N_DONE + "int );";


    public DbHelper(Context context) {
            super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_ROUTINES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ROUTINES_TABLE);
        onCreate(db);
    }
}
