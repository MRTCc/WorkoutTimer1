package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayWorkoutActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnLock;
    private Button btnPrev;
    private Button btnNext;
    private TextView txtCountDown;
    private TextView txtExName;
    private TextView txtPhaseCountDown;
    private TextView txtSetsToDo;
    private TextView txtRepsToDo;
    private boolean isClickable;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_workout);

        btnPlay = findViewById(R.id.btnPlay);
        btnLock = findViewById(R.id.btnLock);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        txtCountDown = findViewById(R.id.txtCountDown);
        txtExName = findViewById(R.id.txtExName);
        txtPhaseCountDown = findViewById(R.id.txtPhaseCountDown);
        txtSetsToDo = findViewById(R.id.txtSetsToDo);
        txtRepsToDo = findViewById(R.id.txtRepsToDo);
        //autosizing of the texts displayed

        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtCountDown, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        //TextViewCompat.setAutoSizeTextTypeWithDefaults(txtExName, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        //TextViewCompat.setAutoSizeTextTypeWithDefaults(txtPhaseCountDown, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtSetsToDo, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtRepsToDo, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        txtExName.setTextSize(30);
        txtPhaseCountDown.setTextSize(200);

        isClickable = true;

        //set of routine that has to be played
        Intent intent = getIntent();
        if(intent.getStringExtra("com.example.workouttimer.MESSAGE") != null) {
            Toast.makeText(this, "favorite fourtine", Toast.LENGTH_SHORT).show();
        }
        if(intent.getStringExtra("playThisRoutine") != null){
            Toast.makeText(this, "routine from manager rotuine", Toast.LENGTH_SHORT).show();
        }
    }


    public void lockButtons(View view) {
        isClickable = !isClickable;

        btnPlay.setClickable(isClickable);
        btnPrev.setClickable(isClickable);
        btnNext.setClickable(isClickable);
    }

}
