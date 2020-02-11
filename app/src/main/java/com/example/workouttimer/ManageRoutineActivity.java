package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ManageRoutineActivity extends AppCompatActivity {
    private Routine routine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_routine);

        Intent intent = getIntent();
        if(intent.hasExtra("newRoutine")) {
            Toast.makeText(this, "newRoutine", Toast.LENGTH_SHORT).show();
            routine = new Routine();
        }
        if(intent.hasExtra("manageThisRoutine")){
            Toast.makeText(this, intent.getStringExtra("manageThisRoutine"), Toast.LENGTH_SHORT).show();
        }
    }
}
