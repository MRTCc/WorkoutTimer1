package com.example.workouttimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {

    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DbManager(Context c) {
        context = c;
    }

    public DbManager open() throws SQLException {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys=ON");
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /*----------- funcionts of insertion -------------------------*/
    public void insertRoutine(String routineName, String dateOfCreation, int nDone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.ROUTINE_NAME, routineName);
        contentValue.put(dbHelper.DATE_OF_CREATION, dateOfCreation);
        contentValue.put(dbHelper.N_DONE, nDone);
        database.insert(dbHelper.ROUTINES_TABLE, null , contentValue);
    }

    public void insertExercise(String exerciseName, int setsToDo, int repsToDo, int preparationTime,
                               int workTime, int restTime, int coolDownTime){
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.EXERCISE_NAME, exerciseName);
        contentValue.put(dbHelper.SETS_TO_DO, setsToDo);
        contentValue.put(dbHelper.REPS_TO_DO, repsToDo);
        contentValue.put(dbHelper.PREPARATION_TIME, preparationTime);
        contentValue.put(dbHelper.WORK_TIME, workTime);
        contentValue.put(dbHelper.REST_TIME, restTime);
        contentValue.put(dbHelper.COOL_DOWN_TIME, coolDownTime);
        database.insert(dbHelper.EXERCISE_TABLE, null, contentValue);
    }

    public void insertFavoriteRoutine(String routineName){
        //TODO: delete all data in the table and then insert the new favorite routine

        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.ROUTINE_NAME, routineName);
        database.insert(dbHelper.FAVORITE_ROUTINE_TABLE, null, contentValue);
    }

    public void insertConcreteRoutine(String routineName, String exerciseName){
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.ROUTINE_NAME, routineName);
        contentValue.put(dbHelper.EXERCISE_NAME, exerciseName);
        database.insert(dbHelper.CONCRETE_ROUTINES_TABLE, null, contentValue);
    }


    /*----------- funcionts of deleting-------------------------*/
    public void deleteRoutine(String routineName){
        database.delete(dbHelper.ROUTINES_TABLE, dbHelper.ROUTINE_NAME + " = " +
                " '" + routineName + "' ", null);
    }

    public void deleteExercise(String exerciseName){
        database.delete(dbHelper.EXERCISE_TABLE, dbHelper.EXERCISE_NAME + " = " +
                " '" + exerciseName + "' ", null);
    }

    public void deleteConcreteRoutine(String routineName, String exerciseName){
        if(routineName != null){
            database.delete(dbHelper.CONCRETE_ROUTINES_TABLE, dbHelper.EXERCISE_NAME + " = " +
                    " '" + exerciseName + "' ", null);
        }

        if(exerciseName != null){
            database.delete(dbHelper.CONCRETE_ROUTINES_TABLE, dbHelper.ROUTINE_NAME + " = " +
                    " '" + routineName + "' ", null);
        }
    }

    public void deleteFavoriteRoutine(){
        database.delete(dbHelper.FAVORITE_ROUTINE_TABLE, null, null);
    }


    
}
