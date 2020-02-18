package com.example.workouttimer.dataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.entity.Routine;

import java.util.ArrayList;

public class DataProvider {
    private DbManager dbManager;

    public DataProvider(Context context){
        dbManager = new DbManager(context);
    }

    public ArrayList<Routine> getAllRoutines(){
        ArrayList<Routine> listRoutine = new ArrayList<>();
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchAllRoutines();
            if (cursor != null && (cursor.getCount() != 0)) {
                do {
                    Routine routine = new Routine();
                    routine.setRoutineName(cursor.getString(cursor.getColumnIndex(DbUtils.ROUTINE_NAME)));
                    routine.setDateOfCreation(cursor.getString(cursor.getColumnIndex(DbUtils.DATE_OF_CREATION)));
                    routine.setnDone(cursor.getInt(cursor.getColumnIndex(DbUtils.N_DONE)));
                    listRoutine.add(routine);
                } while (cursor.moveToNext());
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listRoutine;
    }

    public Routine getFavoriteRoutine(){
        String favoriteRoutineName = getFavoriteRoutineName();
        Routine routine = new Routine();
        routine.setRoutineName(favoriteRoutineName);
        routine = getCompleteRoutine(routine);
        return routine;
    }

    public String getFavoriteRoutineName(){
        String favoriteRoutineName = "";
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchFavoriteRoutine();
            if (cursor != null && (cursor.getCount() != 0)) {
                favoriteRoutineName = cursor.getString(cursor.getColumnIndex(DbUtils.ROUTINE_NAME));
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return favoriteRoutineName;
    }

    public Routine getCompleteRoutine(Routine routine){
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchExercisesFromConcreteRoutine(routine.getRoutineName());
            if (cursor != null && (cursor.getCount() != 0)) {
                ArrayList<Exercise> listExercise = new ArrayList<>();
                do {
                    Exercise exercise = new Exercise();
                    exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(DbUtils.EXERCISE_NAME)));
                    exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.SETS_TO_DO)));
                    exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.REPS_TO_DO)));
                    exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(DbUtils.PREPARATION_TIME)));
                    exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(DbUtils.WORK_TIME)));
                    exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(DbUtils.REST_TIME)));
                    exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(DbUtils.COOL_DOWN_TIME)));
                    exercise.setPosition(cursor.getInt(cursor.getColumnIndex(DbUtils.POSITION)));
                    listExercise.add(exercise);
                } while (cursor.moveToNext());
                routine.setListExercise(listExercise);
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return routine;
    }

    public ArrayList<Exercise> getAllExercises(){
        ArrayList<Exercise> listExercises = new ArrayList<>();
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchAllExercises();
            if (cursor == null || (cursor.getCount() == 0)) {
                return null;
            }
            do {
                Exercise exercise = new Exercise();
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(DbUtils.EXERCISE_NAME)));
                exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.SETS_TO_DO)));
                exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.REPS_TO_DO)));
                exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(DbUtils.PREPARATION_TIME)));
                exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(DbUtils.WORK_TIME)));
                exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(DbUtils.REST_TIME)));
                exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(DbUtils.COOL_DOWN_TIME)));
                listExercises.add(exercise);
            } while (cursor.moveToNext());
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listExercises;
    }

    public Exercise getExercise(String exerciseName){
        Exercise exercise = new Exercise();
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchExercise(exerciseName);
            if (cursor != null && (cursor.getCount() != 0)) {
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(DbUtils.EXERCISE_NAME)));
                exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.SETS_TO_DO)));
                exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(DbUtils.REPS_TO_DO)));
                exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(DbUtils.PREPARATION_TIME)));
                exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(DbUtils.WORK_TIME)));
                exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(DbUtils.REST_TIME)));
                exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(DbUtils.COOL_DOWN_TIME)));
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return exercise;
    }

    public boolean isThereExercise(Exercise exercise){
        boolean isThere = false;
        try {
            dbManager.open("read");
            Cursor cursor = dbManager.fetchExercise(exercise.getExerciseName());
            if (cursor.moveToFirst()) {
                isThere = true;
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return isThere;
    }
}
