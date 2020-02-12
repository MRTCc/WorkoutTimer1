package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private DataProvider dataProvider;
    private Routine routine;
    ScheduledThreadPoolExecutor exec;

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

        dataProvider = new DataProvider(this);
        //set of routine that has to be played
        Intent intent = getIntent();
        if(intent.hasExtra("com.example.workouttimer.MESSAGE")) {
            routine = dataProvider.getFavoriteRoutine();
            //Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        if(intent.hasExtra("playThisRoutine")){
            routine = dataProvider.getCompleteRoutine((Routine) intent.getExtras().getSerializable(
                    "playThisRoutine"));
            assert routine != null;
            //Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        execution();
    }

    public void execution(){
        if(exec != null){
            exec.shutdown();
        }
        if(exec == null){
            exec = new ScheduledThreadPoolExecutor(1);
            exec.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    //Log.i("conto", "prova");
                    tick();
                    render();
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void tick(){

    }

    public void render(){

    }

    public void lockButtons(View view) {
        isClickable = !isClickable;
        btnPlay.setClickable(isClickable);
        btnPrev.setClickable(isClickable);
        btnNext.setClickable(isClickable);
    }

}
