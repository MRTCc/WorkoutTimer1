package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class ManageExerciseActivity extends AppCompatActivity {
    private final static int NEW_EXERCISE_FUNCTION = 0;
    private final static int MODIFY_EXERCISE_FUNCTION = 1;
    private final static int NEW_AND_ADD_EXERCISE_FUNCTION = 2;
    private int activityState;
    private EditText eTxtExerciseName;
    private EditText eTxtPrepTime;
    private EditText eTxtWorkTime;
    private EditText eTxtRestTime;
    private EditText eTxtCoolDownTime;
    private EditText eTxtSetsToDo;
    private EditText eTxtRepsToDo;
    private ImageButton btnCheckExName;
    private Button btnActPrepTime;
    private Button btnActWorkTime;
    private Button btnActRestTime;
    private Button btnActCoolDownTime;
    private Button btnActSetsToDo;
    private Button btnActRepsToDo;
    private Exercise exercise;
    private Exercise entryExercise;
    private DataInserter dataInserter;
    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_exercise);
        initGui();
        dataInserter = new DataInserter(this);
        Intent intent = getIntent();
        if(intent.hasExtra("newExercise")){
            Toast.makeText(this,"newExercise", Toast.LENGTH_SHORT).show();
            eTxtExerciseName.setText(R.string.newExercise);
            exercise = new Exercise();
            entryExercise = new Exercise();
            activityState = NEW_EXERCISE_FUNCTION;
            showExercise(exercise);
        }
        else if(intent.hasExtra("createAndAddExercise")){
            activityState = NEW_AND_ADD_EXERCISE_FUNCTION;
            exercise = new Exercise();
            entryExercise = new Exercise();
            showExercise(exercise);
        }
        else if(intent.hasExtra("modifyThisExercise")){
            Toast.makeText(this,intent.getStringExtra("modifyThisExercise"), Toast.LENGTH_SHORT).show();
            exercise = (Exercise) intent.getExtras().getSerializable("modifyThisExercise");
            entryExercise = new Exercise();
            if(exercise != null){
                clone(entryExercise, exercise);
                showExercise(exercise);
            }
            activityState = MODIFY_EXERCISE_FUNCTION;
        }
        else{
            Toast.makeText(this,"something did'nt work", Toast.LENGTH_SHORT).show();
        }

        eTxtPrepTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtPrepTime.getText().toString();
                    if(!("---".equals(txt))) {
                        Integer prepTime = Integer.valueOf(eTxtPrepTime.getText().toString());
                        exercise.setPreparationTime(prepTime);
                    }
                }

            }
        });
        eTxtWorkTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtWorkTime.getText().toString();
                    if(!("---".equals(txt))) {
                        Integer time = Integer.valueOf(eTxtWorkTime.getText().toString());
                        exercise.setWorkTime(time);
                    }
                }
            }
        });
        eTxtRestTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtRestTime.getText().toString();
                    if(!("---".equals(txt))) {
                        int time = Integer.valueOf(eTxtRestTime.getText().toString());
                        exercise.setRestTime(time);
                    }
                }
            }
        });
        eTxtCoolDownTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtCoolDownTime.getText().toString();
                    if(!("---".equals(txt))) {
                        Integer time = Integer.valueOf(eTxtCoolDownTime.getText().toString());
                        exercise.setCoolDownTime(time);
                    }
                }
            }
        });
        eTxtSetsToDo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtSetsToDo.getText().toString();
                    if(!("---".equals(txt))) {
                        //TODO : when you save control that setsToDo are >= 1
                        Integer n = Integer.valueOf(eTxtSetsToDo.getText().toString());
                        exercise.setSetsToDo(n);
                    }
                }
            }
        });
        eTxtRepsToDo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtRepsToDo.getText().toString();
                    if(!("---".equals(txt))) {
                        Integer n = Integer.valueOf(eTxtRepsToDo.getText().toString());
                        exercise.setRepsToDo(n);
                    }
                }
            }
        });
        eTxtExerciseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    String txt = eTxtExerciseName.getText().toString();
                    exercise.setExerciseName(txt);
                }
            }
        });
    }

    private void showExercise(Exercise exercise){
        eTxtExerciseName.setText(exercise.getExerciseName());
        eTxtPrepTime.setText(Integer.toString(exercise.getPreparationTime()));
        eTxtWorkTime.setText(Integer.toString(exercise.getWorkTime()));
        eTxtRestTime.setText(Integer.toString(exercise.getRestTime()));
        eTxtCoolDownTime.setText(Integer.toString(exercise.getCoolDownTime()));
        eTxtSetsToDo.setText(Integer.toString(exercise.getSetsToDo()));
        eTxtRepsToDo.setText(Integer.toString(exercise.getRepsToDo()));
    }

    private void clone(Exercise exerciseDestionation, Exercise exerciseToCopy) {
        exerciseDestionation.setExerciseName(exerciseToCopy.getExerciseName());
        exerciseDestionation.setSetsToDo(exerciseToCopy.getSetsToDo());
        exerciseDestionation.setRepsToDo(exerciseToCopy.getRepsToDo());
        exerciseDestionation.setPreparationTime(exerciseToCopy.getPreparationTime());
        exerciseDestionation.setWorkTime(exerciseToCopy.getWorkTime());
        exerciseDestionation.setRestTime(exerciseToCopy.getRestTime());
        exerciseDestionation.setCoolDownTime(exerciseToCopy.getCoolDownTime());
    }

    private void initGui(){
        eTxtExerciseName = findViewById(R.id.eTxtExerciseName);
        eTxtPrepTime = findViewById(R.id.eTxtSetPrepTime);
        eTxtWorkTime = findViewById(R.id.eTxtSetWorkTime);
        eTxtRestTime = findViewById(R.id.eTxtSetRestTime);
        eTxtCoolDownTime = findViewById(R.id.eTxtSetCoolDownTime);
        eTxtSetsToDo = findViewById(R.id.eTxtSetSetsToDo);
        eTxtRepsToDo = findViewById(R.id.eTxtSetRepsToDo);
        btnCheckExName = findViewById(R.id.btnCheckExerciseName);
        btnActPrepTime = findViewById(R.id.btnActPrepTime);
        btnActWorkTime = findViewById(R.id.btnActWorkTime);
        btnActRestTime = findViewById(R.id.btnActRestTime);
        btnActCoolDownTime = findViewById(R.id.btnActCoolDownTime);
        btnActSetsToDo =  findViewById(R.id.btnActSetsToDo);
        btnActRepsToDo = findViewById(R.id.btnActRepsToDo);
    }

    public void setTextEditText(@org.jetbrains.annotations.NotNull EditText editText){
        if(editText.equals(eTxtPrepTime)){
            eTxtPrepTime.setText(Integer.toString(exercise.getPreparationTime()));
        }
        else if(editText.equals(eTxtWorkTime)){
            eTxtWorkTime.setText(Integer.toString(exercise.getWorkTime()));

        }
        else if(editText.equals(eTxtRestTime)){
            eTxtRestTime.setText(Integer.toString(exercise.getRestTime()));
        }
        else if(editText.equals(eTxtCoolDownTime)){
            eTxtCoolDownTime.setText(Integer.toString(exercise.getCoolDownTime()));
        }
        else if(editText.equals(eTxtSetsToDo)){
            eTxtSetsToDo.setText(Integer.toString(exercise.getSetsToDo()));
        }
        else if(editText.equals(eTxtRepsToDo)){
            eTxtRepsToDo.setText(Integer.toString(exercise.getRepsToDo()));
        }
    }

    public void changeBtnActState(Button button, EditText editText){
        String txtState = button.getText().toString();
        if(getString(R.string.disableBtn).equals(txtState)){
            button.setText(R.string.enableBtn);
            editText.setText(getString(R.string.disableStateBtn));
            editText.setEnabled(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }
        if(getString(R.string.enableBtn).equals(txtState)){
            button.setText(R.string.disableBtn);
            editText.setEnabled(true);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            setTextEditText(editText);
        }
    }

    public void actRepsToDo(View view) {
        changeBtnActState(btnActRepsToDo, eTxtRepsToDo);
    }

    public void actSetsToDo(View view) {
        final Dialog jokeDialog = new Dialog(this);
        jokeDialog.setContentView(R.layout.dialog_disable_sets);
        jokeDialog.setTitle(getString(R.string.joke));
        jokeDialog.show();
        Button dialogButton = (Button) jokeDialog.findViewById(R.id.btnExitDialogJokeSet);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeDialog.dismiss();
            }
        });
    }

    public void actCoolDownTime(View view) {
        changeBtnActState(btnActCoolDownTime, eTxtCoolDownTime);
    }

    public void actRestTime(View view) {
        changeBtnActState(btnActRestTime, eTxtRestTime);
    }

    public void actWorkTime(View view) {
        changeBtnActState(btnActWorkTime, eTxtWorkTime);
    }

    public void actPrepTime(View view) {
        changeBtnActState(btnActPrepTime, eTxtPrepTime);
    }

    public void checkExerciseName(View view) {
        String message = exercise.getExerciseName();
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dataProvider = new DataProvider(this);
        if(dataProvider.isThereExercise(exercise)){
            Toast.makeText(this, "there is another exercise", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "name available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_manage_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_help_manage_exercise);
        helpDialog.setTitle("Help");
        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDialogManageExercise);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });
        switch(item.getItemId()) {
            case R.id.menuSaveExercise:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                if(activityState == NEW_EXERCISE_FUNCTION){
                    dataInserter.saveNewExercise(exercise);
                }
                else if(activityState == MODIFY_EXERCISE_FUNCTION){
                    dataInserter.updateExercise(exercise, entryExercise);
                }
                else if(activityState == NEW_AND_ADD_EXERCISE_FUNCTION){
                    dataInserter.saveNewExercise(exercise);
                    Intent intent = new Intent(this, ManageRoutineActivity.class);
                    Exercise message = exercise;
                    intent.putExtra("addThisExercise", message);
                    startActivity(intent);
                }
                finish();
                return(true);
            case R.id.menuHelpManageExercise:
                helpDialog.show();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
