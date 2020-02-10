package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ManageExerciseActivity extends AppCompatActivity {
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
    private ImageButton btnSubPrepTime;
    private ImageButton btnSubWorkTime;
    private ImageButton btnSubRestTime;
    private ImageButton btnSubCoolDownTime;
    private ImageButton btnSubSetsToDo;
    private ImageButton btnSubRepsToDo;
    private ImageButton btnAddPrepTime;
    private ImageButton btnAddWorkTime;
    private ImageButton btnAddRestTime;
    private ImageButton btnAddCoolDownTime;
    private ImageButton btnAddSetsToDo;
    private ImageButton btnAddRepsToDo;
    private Exercise exercise;
    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_exercise);

        initGui();
        dataProvider = new DataProvider(this);

        Intent intent = getIntent();
        if(intent.getStringExtra("newExercise") != null){
            Toast.makeText(this,"newExercise", Toast.LENGTH_SHORT).show();
            eTxtExerciseName.setText(R.string.newExercise);
            exercise = new Exercise();
        }
        if(intent.getStringExtra("modifyThisExercise") != null){
            Toast.makeText(this,intent.getStringExtra("modifyThisExercise"), Toast.LENGTH_SHORT).show();

        }

    }

    private void initGui(){
        eTxtExerciseName = findViewById(R.id.eTxtExerciseName);
        eTxtPrepTime = findViewById(R.id.eTxtSetPrepTime);
        eTxtWorkTime = findViewById(R.id.eTxtSetWorkTime);
        eTxtRestTime = findViewById(R.id.eTxtSetRestTime);
        eTxtCoolDownTime = findViewById(R.id.eTxtSetCoolDownTime);
        eTxtSetsToDo = findViewById(R.id.eTxtSetSetsToDo);
        eTxtRepsToDo = findViewById(R.id.eTxtSetRepsToDo);
        btnCheckExName = findViewById(R.id.btnSaveNewExerciseName);
        btnActPrepTime = findViewById(R.id.btnActPrepTime);
        btnActWorkTime = findViewById(R.id.btnActWorkTime);
        btnActRestTime = findViewById(R.id.btnActRestTime);
        btnActCoolDownTime = findViewById(R.id.btnActCoolDownTime);
        btnActSetsToDo =  findViewById(R.id.btnActSetsToDo);
        btnActRepsToDo = findViewById(R.id.btnActRepsToDo);
        btnSubPrepTime = findViewById(R.id.btnSubPrepTime);
        btnSubWorkTime = findViewById(R.id.btnSubWorkTime);
        btnSubRestTime = findViewById(R.id.btnSubRestTime);
        btnSubCoolDownTime = findViewById(R.id.btnSubCoolDownTime);
        btnSubSetsToDo = findViewById(R.id.btnSubSetsToDo);
        btnSubRepsToDo = findViewById(R.id.btnSubRepsToDo);
        btnAddPrepTime = findViewById(R.id.btnAddPrepTime);
        btnAddWorkTime = findViewById(R.id.btnAddWorkTime);
        btnAddRestTime = findViewById(R.id.btnAddRestTime);
        btnAddCoolDownTime = findViewById(R.id.btnAddCoolDownTime);
        btnAddSetsToDo = findViewById(R.id.btnAddSetsToDo);
        btnAddRepsToDo = findViewById(R.id.btnAddRepsToDo);
    }

    public void addRepsToDo(View view) {
    }

    public void subRepsToDo(View view) {
    }

    public void actRepsToDo(View view) {
    }

    public void subSetsToDo(View view) {
    }

    public void addSetsToDo(View view) {
    }

    public void actSetsToDo(View view) {
    }

    public void addCoolDownTime(View view) {
    }

    public void subCoolDownTime(View view) {
    }

    public void actCoolDownTime(View view) {
    }

    public void addRestTime(View view) {
    }

    public void subRestTime(View view) {
    }

    public void actRestTime(View view) {
    }

    public void addWorkTime(View view) {
    }

    public void subWorkTime(View view) {
    }

    public void actWorkTime(View view) {
    }
}
