package com.example.workouttimer.dataAccess;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.entity.Routine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class DataInsert {

    private DbUtils dbUtils;
    private DbManager dbManager;
    private Context context;

    public DataInsert(Context context){
        this.context = context;
        this.dbManager = new DbManager(context);
    }

    public void saveNewExercise(Exercise exercise){
        dbManager.open("write");
        dbManager.insertExercise(exercise.getExerciseName(), exercise.getSetsToDo(),
                exercise.getRepsToDo(), exercise.getPreparationTime(), exercise.getWorkTime(),
                exercise.getRestTime(), exercise.getCoolDownTime());
        dbManager.close();
    }

    public void updateExercise(Exercise newExercise, Exercise oldExercise ){
        if(newExercise.getSetsToDo() < 1){
            Toast.makeText(context, "I can't proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        dbManager.open("write");
        dbManager.updateExercise(oldExercise.getExerciseName(), newExercise.getExerciseName(),
                newExercise.getSetsToDo(), newExercise.getRepsToDo(), newExercise.getPreparationTime(),
                newExercise.getWorkTime(), newExercise.getRestTime(), newExercise.getCoolDownTime());
        dbManager.close();
    }

    public void saveNewRoutine(Routine routine){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dbManager.open("write");
        String routineName = routine.getRoutineName();
        dbManager.insertRoutine(routineName, date, routine.getnDone());
        ArrayList<Exercise> listExercises = routine.getListExercise();
        Iterator<Exercise> iterator = listExercises.iterator();
        while(iterator.hasNext()){
            Exercise exercise = iterator.next();
            dbManager.insertConcreteRoutine(routineName, exercise.getExerciseName(), exercise.getPosition());
        }
        dbManager.close();
    }

    public void updateRoutine(Routine newRoutine, Routine oldRoutine){
        String oldRoutineName = oldRoutine.getRoutineName();
        String newRoutineName = newRoutine.getRoutineName();
        dbManager.open("write");
        if(!oldRoutineName.equals(newRoutineName)){
            dbManager.updateRoutine(oldRoutineName, newRoutineName);
        }
        dbManager.deleteConcreteRoutineByRoutineName(newRoutineName);
        ArrayList<Exercise> listExercises = newRoutine.getListExercise();
        for(int i = 0; i < listExercises.size(); i++){
            Exercise exercise = listExercises.get(i);
            dbManager.insertConcreteRoutine(newRoutineName, exercise.getExerciseName(), i);
        }
        dbManager.close();
    }

    public void updateFavoriteRoutine(String favoriteRoutineName){
        dbManager.open("write");
        dbManager.insertFavoriteRoutine(favoriteRoutineName);
        dbManager.close();
    }

    public void deleteRoutine(Routine routine){
        dbManager.open("write");
        String favoriteRoutineName = null;
        String routineName = routine.getRoutineName();
        Cursor cursor = dbManager.fetchFavoriteRoutine();
        dbManager.deleteRoutine(routineName);
        if(cursor != null && (cursor.getCount() != 0)) {
            favoriteRoutineName = cursor.getString(cursor.getColumnIndex(dbUtils.ROUTINE_NAME));
        }
        assert favoriteRoutineName != null;
        if(favoriteRoutineName.contentEquals(routineName)){
            cursor = dbManager.fetchAllRoutines();
            if(cursor != null && (cursor.getCount() != 0)) {
                Routine routineTmp = new Routine();
                routineTmp.setRoutineName(cursor.getString(cursor.getColumnIndex(dbUtils.ROUTINE_NAME)));
                routineTmp.setDateOfCreation(cursor.getString(cursor.getColumnIndex(dbUtils.DATE_OF_CREATION)));
                routineTmp.setnDone(cursor.getInt(cursor.getColumnIndex(dbUtils.N_DONE)));
                dbManager.insertFavoriteRoutine(routineTmp.getRoutineName());
            }
        }
        dbManager.close();
    }

    public void deleteExercise(Exercise exercise){
        dbManager.open("write");
        dbManager.deleteExercise(exercise.getExerciseName());
        dbManager.close();
    }
}
