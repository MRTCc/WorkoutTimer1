package com.example.workouttimer.playWorkoutPackage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workouttimer.dataAccess.DataProvider;
import com.example.workouttimer.R;
import com.example.workouttimer.entity.Routine;
import com.example.workouttimer.manageRoutine.ManageRoutineActivity;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PlayWorkoutActivity extends AppCompatActivity {

    private Button btnPlay;
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
    private RoutineTick routineTick;
    ScheduledThreadPoolExecutor exec;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_workout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        btnPlay = findViewById(R.id.btnPlay);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        txtCountDown = findViewById(R.id.txtCountDown);
        txtExName = findViewById(R.id.txtExName);
        txtPhaseCountDown = findViewById(R.id.txtPhaseCountDown);
        txtSetsToDo = findViewById(R.id.txtSetsToDo);
        txtRepsToDo = findViewById(R.id.txtRepsToDo);
        layout = findViewById(R.id.tlAllView);

        //auto sizing of the texts displayed
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtCountDown, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtSetsToDo, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtRepsToDo, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        txtExName.setTextSize(30);
        txtPhaseCountDown.setTextSize(200);
        isClickable = true;
        isPlaying = true;
        routineTick = new RoutineTick();
        DataProvider dataProvider = new DataProvider(this);
        Routine routine = new Routine();
        //set of routine that has to be played
        Intent intent = getIntent();
        if(intent.hasExtra(getResources().getString(R.string.play_favorite_routine))) {
            routine = dataProvider.getFavoriteRoutine();
            //Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        else if(intent.hasExtra(getResources().getString(R.string.play_this_routine))){
            try {
                Routine tmp = (Routine) intent.getExtras().getSerializable(getResources().getString(R.string.play_this_routine));
                routine = dataProvider.getCompleteRoutine(tmp);
                assert routine != null;
            }catch (Exception e){
                e.printStackTrace();
            }
           //Toast.makeText(this, routine.getRoutineName(), Toast.LENGTH_SHORT).show();
        }
        //check if the routine is not empty
        if(routine.getListExercise().size() < 1){
            emptyRoutineDialog();
        }
        else {
            routineTick = new RoutineTick(routine, this);
            btnPrev.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.prev_ex),
                            Toast.LENGTH_SHORT).show();
                    routineTick.tickPrevExercise();
                    return true;
                }
            });
            btnNext.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.next_ex),
                            Toast.LENGTH_SHORT).show();
                    routineTick.tickNextExercise();
                    return true;
                }
            });
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        execution();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(routineTick.getRoutine().getListExercise().size() < 1){
            emptyRoutineDialog();
        }
        else{
            render();
            execution();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(exec != null) {
            exec.shutdown();
        }
    }

    @Override
    public void onBackPressed() {
        isPlaying = false;
        if(exec != null) {
            exec.shutdown();
        }
        AlertDialog alertDialog =
                new AlertDialog.Builder(PlayWorkoutActivity.this).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("Do you really want to stop the workout and exit the activity?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void emptyRoutineDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(PlayWorkoutActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.empty_routine));
        alertDialog.setMessage(getResources().getString(R.string.empty_routine_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void execution(){
        if(exec != null){
            exec.shutdown();
            exec = null;
        }
        if(isPlaying){
            try {
            exec = new ScheduledThreadPoolExecutor(1);
            exec.scheduleAtFixedRate(new Runnable() {
                private Runnable update = new Runnable() {
                    @Override
                    public void run() {
                        //Log.i("conto", "prova");
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

    private void deviceVibration(boolean longVibration){
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

    private void tick(){
        if(routineTick.getTotCountDown() < 1){
            //routine finished
            Toast.makeText(this, getResources().getString(R.string.routine_finished),
                    Toast.LENGTH_SHORT).show();
            exec.shutdown();
            exec = null;
            deviceVibration(true);
            finishDialog();
        }
        else {
            if(routineTick.getPhaseCountDown() < 4 && routineTick.getPhaseCountDown() > 0){
                deviceVibration(false);
            }
            routineTick.tick();
        }
    }

    private void render(){
        String backgroundColor = routineTick.getStateColor();
        setBackgroundColor(backgroundColor);
        txtCountDown.setText(routineTick.getFormatTotCountDown());
        txtExName.setText(routineTick.getFormatCurrentExName());
        txtPhaseCountDown.setText(routineTick.getFormatPhaseCountDown());
        txtSetsToDo.setText(routineTick.getFormatSetsToDo());
        txtRepsToDo.setText(routineTick.getFormatRepsToDo());
    }

    private void finishDialog(){
        AlertDialog alertDialog =
                new AlertDialog.Builder(PlayWorkoutActivity.this).create();
        alertDialog.setTitle(getString(R.string.motivation));
        alertDialog.setMessage(getResources().getString(R.string.motivation_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void setBackgroundColor(String backgroundColor) {
        int color;
        if(getResources().getString(R.string.prepTimeColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.SpringGreen);
        }
        else if(getResources().getString(R.string.workTimeColor).contentEquals(backgroundColor)){
            color = getResources().getColor(R.color.OrangeRed);
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
        btnPlay.setEnabled(isClickable);
        btnPrev.setEnabled(isClickable);
        btnNext.setEnabled(isClickable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_play_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_play_workout);
        helpDialog.setTitle("Help");
        Button dialogButton = helpDialog.findViewById(R.id.btnExitDialogPlayWorkout);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        switch(item.getItemId()) {
            case R.id.menuHelpPlayWorkout:
                helpDialog.show();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
