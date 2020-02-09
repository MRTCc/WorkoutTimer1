package com.example.workouttimer;

import android.content.ContentUris;
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

    public DbManager open(String accessMode) throws SQLException {
        dbHelper = new DbHelper(context);
        if("read".equals(accessMode)){
            database = dbHelper.getReadableDatabase();
            database.execSQL("PRAGMA foreign_keys=ON");
        }
        if("write".equals(accessMode)) {
            database = dbHelper.getWritableDatabase();
            database.execSQL("PRAGMA foreign_keys=ON");
        }
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
        deleteFavoriteRoutine();
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


    /*----------- funciont of update --------------*/
    public void updateRoutine(String oldRoutineName, String newRoutineName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ROUTINE_NAME, newRoutineName);
        //TODO: verify if it is needed a control on the return value of update function
        database.update(dbHelper.ROUTINES_TABLE, contentValues, dbHelper.ROUTINE_NAME
                + " = " + " '" + oldRoutineName + "' ", null);
    }

    public void updateExercise(String oldExerciseName, String newExerciseName, int setsToDo,
                               int repsToDo, int preparationTime, int workTime, int restTime,
                               int coolDownTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.EXERCISE_NAME, newExerciseName);
        contentValues.put(dbHelper.SETS_TO_DO, setsToDo);
        contentValues.put(dbHelper.REPS_TO_DO, repsToDo);
        contentValues.put(dbHelper.PREPARATION_TIME, preparationTime);
        contentValues.put(dbHelper.WORK_TIME, workTime);
        contentValues.put(dbHelper.REST_TIME, restTime);
        contentValues.put(dbHelper.COOL_DOWN_TIME, coolDownTime);
        database.update(dbHelper.EXERCISE_TABLE, contentValues, dbHelper.EXERCISE_NAME +
                " = " + " '" + oldExerciseName + "' ", null);
    }



    /*---------- functions of SELECT -------------*/
    public Cursor fetchAllRoutines(){
        String[] columns = new String[] {dbHelper.ROUTINE_NAME, dbHelper.DATE_OF_CREATION,
                dbHelper.N_DONE};
        Cursor cursor = database.query(dbHelper.ROUTINES_TABLE, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchFavoriteRoutine(){
        String[] columns = new String[] {dbHelper.ROUTINE_NAME};
        Cursor cursor = database.query(dbHelper.FAVORITE_ROUTINE_TABLE, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchExercisesNameFromConcrRoutine(String routineName){
        String[] columns = new String[] {dbHelper.EXERCISE_NAME, dbHelper.SETS_TO_DO, dbHelper.REPS_TO_DO,
                dbHelper.PREPARATION_TIME, dbHelper.WORK_TIME, dbHelper.REST_TIME, dbHelper.COOL_DOWN_TIME,
                dbHelper.POSITION};
        Cursor cursor = database.query(dbHelper.CONCRETE_ROUTINES_TABLE, columns,
                routineName + " = " + dbHelper.ROUTINE_NAME + " and " +
                 dbHelper.CONCRETE_ROUTINES_TABLE + "."+ dbHelper.EXERCISE_NAME + " = " +
                dbHelper.EXERCISE_TABLE + "." + dbHelper.EXERCISE_NAME, null,
                null, dbHelper.POSITION, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


}
