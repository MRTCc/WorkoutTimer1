package com.example.workouttimer;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

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

        while(cursor.moveToNext()){
            Routine routine = new Routine();
            routine.setRoutineName(cursor.getString(cursor.getColumnIndex(dbUtils.ROUTINE_NAME)));
            routine.setDateOfCreation(cursor.getString(cursor.getColumnIndex(dbUtils.DATE_OF_CREATION)));
            routine.setnDone(cursor.getInt(cursor.getColumnIndex(dbUtils.N_DONE)));
            listRoutine.add(routine);
        }

        dbManager.close();
        return listRoutine;
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

    public Routine getCompleteRoutine(String routineName){
        Routine routine = new Routine();

        Cursor cursor = dbManager.fetchExercisesNameFromConcrRoutine(routineName);

        if(cursor != null) {
            routine.setRoutineName(routineName);
            ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
            while(cursor.moveToNext()){
                Exercise exercise = new Exercise();
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(dbUtils.EXERCISE_NAME)));
                exercise.setSetsToDo(cursor.getInt(cursor.getColumnIndex(dbUtils.SETS_TO_DO)));

                listExercise.add(exercise);
            }

        }

        dbManager.close();
        return routine;
    }
}
