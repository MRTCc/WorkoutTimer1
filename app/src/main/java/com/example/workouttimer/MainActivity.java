package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final static String EXTRA_MESSAGE_PLAY = "playFavoriteRoutine";
    private final static String EXTRA_MESSAGE_WORKOUT = "com.example.workouttimer.MESSAGE";
    private final static String EXTRA_MESSAGE_ABOUT = "com.example.workouttimer.MESSAGE";
    private final static String EXTRA_MESSAGE_EXERCISES = "com.example.workouttimer.MESSAGE";
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // Get preferences file (0 = no option flags set)
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
        if (firstRun) {
            Log.w("activity", "first time");
            DbManager dbManager = new DbManager(this);
            dbManager.open("write");
            dbManager.insertRoutine("new routine", "1-1-2020", 0);
            dbManager.insertFavoriteRoutine("new routine");
            dbManager.insertExercise("new exercise", 4, 8, 50, 60, 70,20);
            dbManager.close();
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.apply(); // Save all changed settings
        }
        //TODO : controllare cosa succede se il db è vuoto
        //TODO: finestre di dialogo quando si lascia un'activity senza aver salvato
    }

    public void playWorkout(View view) {
        Intent playIntent = new Intent(this, PlayWorkoutActivity.class);
        playIntent.putExtra(EXTRA_MESSAGE_PLAY, "routine di prova");
        startActivity(playIntent);
    }

    public void managerWorkout(View view) {
        Intent managerIntent = new Intent(this, ManagerWorkoutActivity.class);
        managerIntent.putExtra(EXTRA_MESSAGE_WORKOUT, "PROVA");
        startActivity(managerIntent);
    }


    public void helpDialog(View view) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_main_help);
        helpDialog.setTitle("Help");

        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDialog);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        helpDialog.show();

    }

    public void aboutActivity(View view) {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        aboutIntent.putExtra(EXTRA_MESSAGE_ABOUT, "PROVA");
        startActivity(aboutIntent);
    }

    public void showExercises(View view) {
        Intent intent = new Intent(this, ShowExercisesActivity.class);
        intent.putExtra(EXTRA_MESSAGE_EXERCISES, "PROVA");
        startActivity(intent);
    }
}
