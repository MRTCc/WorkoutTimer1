package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ManageRoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_routine);

        Intent intent = getIntent();
        if(intent.getStringExtra("newRoutine") != null) {
            Toast.makeText(this, "newRoutine", Toast.LENGTH_SHORT).show();
        }
        if(intent.getStringExtra("manageThisRoutine") != null){
            Toast.makeText(this, intent.getStringExtra("manageThisRoutine"), Toast.LENGTH_SHORT).show();
        }
    }
}
