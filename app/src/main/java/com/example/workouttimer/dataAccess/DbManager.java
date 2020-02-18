package com.example.workouttimer.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

class DbManager {

    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

     DbManager(Context c) {
        context = c;
    }

     DbManager open(String accessMode) throws SQLException {
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

     void close() {
        dbHelper.close();
    }

    /*----------- functions of insertion -------------------------*/
     void insertRoutine(String routineName, String dateOfCreation, int nDone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.ROUTINE_NAME, routineName);
        contentValue.put(DbHelper.DATE_OF_CREATION, dateOfCreation);
        contentValue.put(DbHelper.N_DONE, nDone);
        database.insert(DbHelper.ROUTINES_TABLE, null , contentValue);
    }

     void insertExercise(String exerciseName, int setsToDo, int repsToDo, int preparationTime,
                               int workTime, int restTime, int coolDownTime){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.EXERCISE_NAME, exerciseName);
        contentValue.put(DbHelper.SETS_TO_DO, setsToDo);
        contentValue.put(DbHelper.REPS_TO_DO, repsToDo);
        contentValue.put(DbHelper.PREPARATION_TIME, preparationTime);
        contentValue.put(DbHelper.WORK_TIME, workTime);
        contentValue.put(DbHelper.REST_TIME, restTime);
        contentValue.put(DbHelper.COOL_DOWN_TIME, coolDownTime);
        database.insert(DbHelper.EXERCISE_TABLE, null, contentValue);
    }

     void insertFavoriteRoutine(String routineName){
        deleteFavoriteRoutine();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.ROUTINE_NAME, routineName);
        database.insert(DbHelper.FAVORITE_ROUTINE_TABLE, null, contentValue);
    }

     void insertConcreteRoutine(String routineName, String exerciseName, int position){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.ROUTINE_NAME, routineName);
        contentValue.put(DbHelper.EXERCISE_NAME, exerciseName);
        contentValue.put(DbHelper.POSITION, position);
        database.insert(DbHelper.CONCRETE_ROUTINES_TABLE, null, contentValue);
    }

    /*----------- functions of deleting-------------------------*/
     void deleteRoutine(String routineName){
        database.delete(DbHelper.ROUTINES_TABLE, DbHelper.ROUTINE_NAME + " = " +
                " '" + routineName + "' ", null);
    }

     void deleteExercise(String exerciseName){
        database.delete(DbHelper.EXERCISE_TABLE, DbHelper.EXERCISE_NAME + " = " +
                " '" + exerciseName + "' ", null);
    }

     void deleteConcreteRoutineByRoutineName(String routineName){
        if(routineName != null){
            database.delete(DbHelper.CONCRETE_ROUTINES_TABLE, DbHelper.ROUTINE_NAME +
                    " = ? ", new String[]{routineName});
        }
    }

    private void deleteFavoriteRoutine(){
        database.delete(DbHelper.FAVORITE_ROUTINE_TABLE, null, null);
    }


    /*----------- functions of update --------------*/
     void updateRoutine(String oldRoutineName, String newRoutineName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ROUTINE_NAME, newRoutineName);
        database.update(DbHelper.ROUTINES_TABLE, contentValues, DbHelper.ROUTINE_NAME
                + " = ? ", new String[] {oldRoutineName});
    }


     void updateExercise(String oldExerciseName, String newExerciseName, int setsToDo,
                               int repsToDo, int preparationTime, int workTime, int restTime,
                               int coolDownTime){
        String whereClause = DbHelper.EXERCISE_NAME + " = ? ";
        String[] whereArgs = new String[]{oldExerciseName};
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.EXERCISE_NAME, newExerciseName);
        contentValues.put(DbHelper.SETS_TO_DO, setsToDo);
        contentValues.put(DbHelper.REPS_TO_DO, repsToDo);
        contentValues.put(DbHelper.PREPARATION_TIME, preparationTime);
        contentValues.put(DbHelper.WORK_TIME, workTime);
        contentValues.put(DbHelper.REST_TIME, restTime);
        contentValues.put(DbHelper.COOL_DOWN_TIME, coolDownTime);
        database.update(DbHelper.EXERCISE_TABLE, contentValues, whereClause, whereArgs);
    }

    /*---------- functions of SELECT -------------*/
     Cursor fetchAllRoutines(){
        String[] columns = new String[] {DbHelper.ROUTINE_NAME, DbHelper.DATE_OF_CREATION,
                DbHelper.N_DONE};
        Cursor cursor = database.query(DbHelper.ROUTINES_TABLE, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

     Cursor fetchFavoriteRoutine(){
        String[] columns = new String[] {DbHelper.ROUTINE_NAME};
        Cursor cursor = database.query(DbHelper.FAVORITE_ROUTINE_TABLE, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

     Cursor fetchExercisesFromConcreteRoutine(String routineName){
        String query = "select " + DbHelper.EXERCISE_TABLE + "." + DbHelper.EXERCISE_NAME + " , " +
                DbHelper.SETS_TO_DO + " , " + DbHelper.REPS_TO_DO + " , " + DbHelper.PREPARATION_TIME +
                "," + DbHelper.WORK_TIME + " , " + DbHelper.REST_TIME + " , " + DbHelper.COOL_DOWN_TIME +
                " , " + DbHelper.POSITION + " from " + DbHelper.CONCRETE_ROUTINES_TABLE + " , " +
                DbHelper.EXERCISE_TABLE + " where " +  DbHelper.EXERCISE_TABLE + "." +
                DbHelper.EXERCISE_NAME + " = " + DbHelper.CONCRETE_ROUTINES_TABLE + "." +
                DbHelper.EXERCISE_NAME + " and " + DbHelper.CONCRETE_ROUTINES_TABLE + "." +
                DbHelper.ROUTINE_NAME + " = " + " '" + routineName + "' " + " order by " +
                DbHelper.POSITION;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

     Cursor fetchAllExercises(){
        String[] columns = new String[] {DbHelper.EXERCISE_NAME, DbHelper.SETS_TO_DO,
                DbHelper.REPS_TO_DO, DbHelper.PREPARATION_TIME, DbHelper.WORK_TIME,
                DbHelper.REST_TIME, DbHelper.COOL_DOWN_TIME,};
        Cursor cursor = database.query(DbHelper.EXERCISE_TABLE, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

     Cursor fetchExercise(String exerciseName){
        String[] columns = new String[] {DbHelper.EXERCISE_NAME, DbHelper.SETS_TO_DO,
                DbHelper.REPS_TO_DO, DbHelper.PREPARATION_TIME, DbHelper.WORK_TIME,
                DbHelper.REST_TIME, DbHelper.COOL_DOWN_TIME,};
        String whereClause = DbHelper.EXERCISE_NAME + " = ? ";
        String[] argsWhereClause = new String[]{exerciseName};
        Cursor cursor = database.query(DbHelper.EXERCISE_TABLE, columns, whereClause,
                argsWhereClause, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
