package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE_PLAY = "com.example.workouttimer.MESSAGE";
    public final static String EXTRA_MESSAGE_WORKOUT = "com.example.workouttimer.MESSAGE";
    public final static String EXTRA_MESSAGE_ABOUT = "com.example.workouttimer.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDIalog);

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
}
