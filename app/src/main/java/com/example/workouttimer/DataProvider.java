package com.example.workouttimer;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DataProvider {

    private DbUtils dbUtils;
    private DbManager dbManager;

    public DataProvider(Context context){
        dbManager = new DbManager(context);

        //TODO: remember to close the connection to database
    }

    public ArrayList<Routine> getAllRoutines(){
        ArrayList<Routine> listRoutine = new ArrayList<Routine>();
        dbManager.open("read");
        Cursor cursor = dbManager.fetchAllRoutines();
        assert cursor != null;
        do{
            Routine routine = new Routine();
            routine.setRoutineName(cursor.getString(cursor.getColumnIndex(dbUtils.ROUTINE_NAME)));
            routine.setDateOfCreation(cursor.getString(cursor.getColumnIndex(dbUtils.DATE_OF_CREATION)));
            routine.setnDone(cursor.getInt(cursor.getColumnIndex(dbUtils.N_DONE)));
            listRoutine.add(routine);
        }while(cursor.moveToNext());

        dbManager.close();
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
        dbManager.open("read");
        Cursor cursor = dbManager.fetchFavoriteRoutine();
        if(cursor != null) {
            favoriteRoutineName = cursor.getString(cursor.getColumnIndex(dbUtils.ROUTINE_NAME));
        }
        dbManager.close();
        return favoriteRoutineName;
    }

    //TODO: DA TESTARE
    public Routine getCompleteRoutine(Routine routine){
        dbManager.open("read");
        Cursor cursor = dbManager.fetchExercisesFromConcreteRoutine(routine.getRoutineName());
        if(cursor != null && (cursor.getCount() != 0)) {
            ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
            do{
                Exercise exercise = new Exercise();
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(dbUtils.EXERCISE_NAME)));
                exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.SETS_TO_DO)));
                exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.REPS_TO_DO)));
                exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(dbUtils.PREPARATION_TIME)));
                exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(dbUtils.WORK_TIME)));
                exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(dbUtils.REST_TIME)));
                exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(dbUtils.COOL_DOWN_TIME)));
                exercise.setPosition(cursor.getInt(cursor.getColumnIndex(dbUtils.POSITION)));
                listExercise.add(exercise);
            }while(cursor.moveToNext());
            routine.setListExercise(listExercise);
        }
        dbManager.close();
        return routine;
    }

    public ArrayList<Exercise> getAllExercises(){
        ArrayList<Exercise> listExercises = new ArrayList<Exercise>();
        dbManager.open("read");
        Cursor cursor = dbManager.fetchAllExercises();
        if(cursor == null || (cursor.getCount() == 0)){
            return null;
        }
         do{
            Exercise exercise = new Exercise();
            exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(dbUtils.EXERCISE_NAME)));
            exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.SETS_TO_DO)));
            exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.REPS_TO_DO)));
            exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(dbUtils.PREPARATION_TIME)));
            exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(dbUtils.WORK_TIME)));
            exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(dbUtils.REST_TIME)));
            exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(dbUtils.COOL_DOWN_TIME)));
            listExercises.add(exercise);
        } while(cursor.moveToNext());
        dbManager.close();
        return listExercises;
    }

    public Exercise getExercise(String exerciseName){
        Exercise exercise = new Exercise();
        dbManager.open("read");
        Cursor cursor = dbManager.fetchExercise(exerciseName);
        if(cursor != null && (cursor.getCount() != 0)){
            exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(dbUtils.EXERCISE_NAME)));
            exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.SETS_TO_DO)));
            exercise.setRepsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.REPS_TO_DO)));
            exercise.setPreparationTime(cursor.getInt(cursor.getColumnIndex(dbUtils.PREPARATION_TIME)));
            exercise.setWorkTime(cursor.getInt(cursor.getColumnIndex(dbUtils.WORK_TIME)));
            exercise.setRestTime(cursor.getInt(cursor.getColumnIndex(dbUtils.REST_TIME)));
            exercise.setCoolDownTime(cursor.getInt(cursor.getColumnIndex(dbUtils.COOL_DOWN_TIME)));
        }
        dbManager.close();
        return exercise;
    }

    public boolean isThereExercise(Exercise exercise){
        boolean isThere = false;
        dbManager.open("read");
        Cursor cursor = dbManager.fetchExercise(exercise.getExerciseName());
        if(cursor.moveToFirst()){
            isThere = true;
        }
        dbManager.close();
        return isThere;
    }

    //TODO: DA TESTARE
    public boolean isThereRoutine(Routine routine){
        boolean isThere = false;
        dbManager.open("read");
        Cursor cursor = dbManager.fetchRoutine(routine.getRoutineName());
        if(cursor.moveToFirst()){
            isThere = true;
        }
        dbManager.close();
        return isThere;
    }
}
