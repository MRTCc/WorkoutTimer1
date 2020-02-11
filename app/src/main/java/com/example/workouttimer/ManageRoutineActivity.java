package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ManageRoutineActivity extends AppCompatActivity {
    private final static int NEW_ROUTINE_FUNCTION = 0;
    private final static int MODIFY_ROUTINE_FUNCTION = 1;
    private Routine routine;
    private Routine entryRoutine;
    private int activityState;
    private DataProvider dataProvider;
    private EditText eTxtRoutineName;
    private ImageButton btnCheckRoutineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_routine);
        eTxtRoutineName = findViewById(R.id.eTxtRoutineName);
        btnCheckRoutineName = findViewById(R.id.btnCheckRoutineName);
        dataProvider = new DataProvider(this);
        Intent intent = getIntent();
        if(intent.hasExtra("newRoutine")) {
            Toast.makeText(this, "newRoutine", Toast.LENGTH_SHORT).show();
            routine = new Routine();
            entryRoutine = new Routine();
        }
        if(intent.hasExtra("manageThisRoutine")){
            Toast.makeText(this, intent.getStringExtra("manageThisRoutine"), Toast.LENGTH_SHORT).show();
            routine = (Routine) intent.getExtras().getSerializable("manageThisRoutine");
            assert routine != null;
            entryRoutine = clone(routine);
        }
        showRoutineName();
        eTxtRoutineName.addTextChangedListener(new TextWatcher() {
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
                    String txt = eTxtRoutineName.getText().toString();
                    routine.setRoutineName(txt);
                }
            }
        });
    }

    private void showRoutineName() {
        eTxtRoutineName.setText(routine.getRoutineName());
    }

    private Routine clone(Routine routine) {
        Routine cloneRoutine = new Routine();
        cloneRoutine.setRoutineName(routine.getRoutineName());
        cloneRoutine.setDateOfCreation(routine.getDateOfCreation());
        cloneRoutine.setnDone(routine.getnDone());
        cloneRoutine.setTotTime(routine.getTotTime());
        cloneRoutine.setNumberOfExercises(routine.getNumberOfExercises());
        cloneRoutine.setListExercise(routine.getListExercise());
        return cloneRoutine;
    }

    public void checkRoutineName(View view) {
        String name = routine.getRoutineName();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}
