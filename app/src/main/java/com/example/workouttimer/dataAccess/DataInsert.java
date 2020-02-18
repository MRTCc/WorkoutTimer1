package com.example.workouttimer.dataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.entity.Routine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataInsert {
    private DbManager dbManager;
    private Context context;

    public DataInsert(Context context){
        this.context = context;
        this.dbManager = new DbManager(context);
    }

    public void saveNewExercise(Exercise exercise){
        try {
            dbManager.open("write");
            dbManager.insertExercise(exercise.getExerciseName(), exercise.getSetsToDo(),
                    exercise.getRepsToDo(), exercise.getPreparationTime(), exercise.getWorkTime(),
                    exercise.getRestTime(), exercise.getCoolDownTime());
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateExercise(Exercise newExercise, Exercise oldExercise ){
        if(newExercise.getSetsToDo() < 1){
            Toast.makeText(context, "I can't proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
        dbManager.open("write");
        dbManager.updateExercise(oldExercise.getExerciseName(), newExercise.getExerciseName(),
                newExercise.getSetsToDo(), newExercise.getRepsToDo(), newExercise.getPreparationTime(),
                newExercise.getWorkTime(), newExercise.getRestTime(), newExercise.getCoolDownTime());
        dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveNewRoutine(Routine routine){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        try{
            dbManager.open("write");
            String routineName = routine.getRoutineName();
            dbManager.insertRoutine(routineName, date, routine.getnDone());
            ArrayList<Exercise> listExercises = routine.getListExercise();
            for (Exercise exercise : listExercises) {
                dbManager.insertConcreteRoutine(routineName, exercise.getExerciseName(), exercise.getPosition());
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateRoutine(Routine newRoutine, Routine oldRoutine){
        String oldRoutineName = oldRoutine.getRoutineName();
        String newRoutineName = newRoutine.getRoutineName();
        try{
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
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateFavoriteRoutine(String favoriteRoutineName){
        try{
            dbManager.open("write");
            dbManager.insertFavoriteRoutine(favoriteRoutineName);
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteRoutine(Routine routine){
        try {
            dbManager.open("write");
            String favoriteRoutineName = null;
            String routineName = routine.getRoutineName();
            Cursor cursor = dbManager.fetchFavoriteRoutine();
            dbManager.deleteRoutine(routineName);
            if (cursor != null && (cursor.getCount() != 0)) {
                favoriteRoutineName = cursor.getString(cursor.getColumnIndex(DbUtils.ROUTINE_NAME));
            }
            assert favoriteRoutineName != null;
            if (favoriteRoutineName.contentEquals(routineName)) {
                cursor = dbManager.fetchAllRoutines();
                if (cursor != null && (cursor.getCount() != 0)) {
                    Routine routineTmp = new Routine();
                    routineTmp.setRoutineName(cursor.getString(cursor.getColumnIndex(DbUtils.ROUTINE_NAME)));
                    routineTmp.setDateOfCreation(cursor.getString(cursor.getColumnIndex(
                            DbUtils.DATE_OF_CREATION)));
                    routineTmp.setnDone(cursor.getInt(cursor.getColumnIndex(DbUtils.N_DONE)));
                    dbManager.insertFavoriteRoutine(routineTmp.getRoutineName());
                }
            }
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteExercise(Exercise exercise){
        try {
            dbManager.open("write");
            dbManager.deleteExercise(exercise.getExerciseName());
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void firstInsertion(){
        try {
            dbManager.open("write");
            dbManager.insertRoutine("new routine", "1-1-2020", 0);
            dbManager.insertFavoriteRoutine("new routine");
            dbManager.insertExercise("new exercise", 4, 8, 50, 60, 70, 20);
            dbManager.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
