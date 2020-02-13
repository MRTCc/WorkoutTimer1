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
    private boolean isPlaying;
    private DataProvider dataProvider;
    private RoutineTick routineTick;
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
        isPlaying = true;
        routineTick = new RoutineTick();
        dataProvider = new DataProvider(this);
        Routine routine;
        //set of routine that has to be played
        Intent intent = getIntent();
        if(intent.hasExtra("playFavoriteRoutine")) {
            routine = dataProvider.getFavoriteRoutine();
            routineTick = new RoutineTick(routine);
            Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        if(intent.hasExtra("playThisRoutine")){
            routine = dataProvider.getCompleteRoutine((Routine) intent.getExtras().getSerializable(
                    "playThisRoutine"));
            assert routine != null;
            routineTick = new RoutineTick(routine);
            Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        render();
        execution();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exec.shutdown();
    }

    public void execution(){
        if(exec != null){
            exec.shutdown();
        }
        if(exec == null){
            try {
            exec = new ScheduledThreadPoolExecutor(1);
            exec.scheduleAtFixedRate(new Runnable() {
                private Runnable update = new Runnable() {
                    @Override
                    public void run() {
                        Log.i("conto", "prova");
                        if(isPlaying){
                            tick();
                            render();
                        }
                    }
                };
                @Override
                public void run() {
                    runOnUiThread(update);
                }
            }, 0, 1, TimeUnit.SECONDS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void tick(){
        routineTick.tick();
    }

    public void render(){
        txtCountDown.setText(routineTick.getTotCountDown());
        txtExName.setText(routineTick.getActualExName());
        txtPhaseCountDown.setText(routineTick.getPhaseCountDown());
        txtSetsToDo.setText(routineTick.getSetsToDo());
        txtRepsToDo.setText(routineTick.getRepssToDo());
    }

    public void playPauseRoutine(View view){
        isPlaying = !isPlaying;
    }

    public void lockButtons(View view) {
        isClickable = !isClickable;
        btnPlay.setClickable(isClickable);
        btnPrev.setClickable(isClickable);
        btnNext.setClickable(isClickable);
    }

}
