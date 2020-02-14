package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
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
    private TableLayout layout;
    private boolean isClickable;
    private boolean isPlaying;
    private DataProvider dataProvider;
    private RoutineTick routineTick;
    ScheduledThreadPoolExecutor exec;
    private Vibrator vibrator;

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
        layout = findViewById(R.id.tlAllView);
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
            routineTick = new RoutineTick(routine, this);
            Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        if(intent.hasExtra("playThisRoutine")){
            routine = dataProvider.getCompleteRoutine((Routine) intent.getExtras().getSerializable(
                    "playThisRoutine"));
            assert routine != null;
            routineTick = new RoutineTick(routine, this);
            Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }

        btnPrev.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "prev ex", Toast.LENGTH_SHORT).show();
                routineTick.tickPrevExercise();
                return true;
            }
        });
        btnNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "next ex", Toast.LENGTH_SHORT).show();
                routineTick.tickNextExercise();
                return true;
            }
        });
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        render();
        execution();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(exec != null) {
            exec.shutdown();
        }
    }

    public void execution(){
        if((exec!= null && !isPlaying) || exec != null){
            exec.shutdown();
            exec = null;
        }
        if(exec == null && isPlaying){
            try {
            exec = new ScheduledThreadPoolExecutor(1);
            exec.scheduleAtFixedRate(new Runnable() {
                private Runnable update = new Runnable() {
                    @Override
                    public void run() {
                        Log.i("conto", "prova");
                        render();
                        tick();
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

    public void deviceVibration(boolean longVibration){
        int millis;
        if(longVibration){
            millis = 1000;
        }
        else{
            millis = 500;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(millis);
        }
    }

    public void tick(){
        if(routineTick.getTotCountDown() < 1){
            //routine finished
            Toast.makeText(this, "routine finished", Toast.LENGTH_SHORT).show();
            exec.shutdown();
            exec = null;
            deviceVibration(true);
        }
        else {
            if(routineTick.getPhaseCountDown() < 4 && routineTick.getPhaseCountDown() > 0){
                deviceVibration(false);
            }
            routineTick.tick();
        }
    }

    public void render(){
        String backgroundColor = routineTick.getStateColor();
        setBackgroundColor(backgroundColor);
        txtCountDown.setText(routineTick.getFormatTotCountDown());
        txtExName.setText(routineTick.getFormatCurrentExName());
        txtPhaseCountDown.setText(routineTick.getFormatPhaseCountDown());
        txtSetsToDo.setText(routineTick.getFormatSetsToDo());
        txtRepsToDo.setText(routineTick.getFormatRepsToDo());
    }

    private void setBackgroundColor(String backgroundColor) {
        int color;
        if(getResources().getString(R.string.prepTimeColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.SpringGreen);
        }
        else if(getResources().getString(R.string.workTimeColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.IndianRed);
        }
        else if(getResources().getString(R.string.restTimeColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.Yellow);
        }
        else if(getResources().getString(R.string.coolDownColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.CadetBlue);
        }
        else{
            color = getResources().getColor(R.color.White);
        }
        layout.setBackgroundColor(color);
        txtCountDown.setBackgroundColor(color);
        txtExName.setBackgroundColor(color);
        txtPhaseCountDown.setBackgroundColor(color);
        txtSetsToDo.setBackgroundColor(color);
        txtRepsToDo.setBackgroundColor(color);
    }

    public void playPauseRoutine(View view){
        isPlaying = !isPlaying;
        execution();
    }

    public void tickPrevPhase(View view){
        //Toast.makeText(this, "prev phase", Toast.LENGTH_SHORT).show();
        routineTick.tickPrevPhase();
        onResume();
    }

    public void tickNextPhase(View view){
        //Toast.makeText(this, "next phase", Toast.LENGTH_SHORT).show();
        routineTick.tickNextPhase();
        onResume();
    }

    public void lockButtons(View view) {
        isClickable = !isClickable;
        btnPlay.setClickable(isClickable);
        btnPrev.setClickable(isClickable);
        btnNext.setClickable(isClickable);
    }

}
