package com.example.workouttimer;

import android.content.Context;
import android.widget.Toast;

public class DataInserter {

    private DbUtils dbUtils;
    private DbManager dbManager;
    private Context context;

    public DataInserter(Context context){
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
}
