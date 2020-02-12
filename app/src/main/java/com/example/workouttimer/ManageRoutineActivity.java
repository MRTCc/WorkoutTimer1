package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ManageRoutineActivity extends AppCompatActivity {
    private final static int NEW_ROUTINE_FUNCTION = 0;
    private final static int MODIFY_ROUTINE_FUNCTION = 1;
    private Routine routine;
    private Routine entryRoutine;
    private ArrayList<Exercise> listExercises;
    private int activityState;
    private DataProvider dataProvider;
    private DataInserter dataInserter;
    private EditText eTxtRoutineName;
    private ImageButton btnAddExercise;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_routine);
        dataProvider = new DataProvider(this);
        dataInserter = new DataInserter(this);
        eTxtRoutineName = findViewById(R.id.eTxtRoutineName);
        btnAddExercise = findViewById(R.id.btnAddExercise);
        Intent intent = getIntent();
        if(intent.hasExtra("newRoutine")) {
            Toast.makeText(this, "newRoutine", Toast.LENGTH_SHORT).show();
            routine = new Routine();
            entryRoutine = new Routine();
        }
        if(intent.hasExtra("manageThisRoutine")){
            Toast.makeText(this, intent.getStringExtra("manageThisRoutine"), Toast.LENGTH_SHORT).show();
            routine = (Routine) intent.getExtras().getSerializable("manageThisRoutine");
            routine = dataProvider.getCompleteRoutine(routine);
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


        listExercises = routine.getListExercise();
        recyclerView = findViewById(R.id.rvExercises);
        recyclerAdapter = new RecyclerAdapter(routine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        Intent intent = new Intent(this, ManageRoutineActivity.class);
        intent.putExtra("manageThisRoutine", routine);
        startActivity(intent);
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

    public void addExercise(View view) {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_manage_routine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_help_manage_routine);
        helpDialog.setTitle("Help");

        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDialogManageRoutine);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        switch(item.getItemId()) {
            case R.id.menuSaveRoutine:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                if(activityState == NEW_ROUTINE_FUNCTION){
                    dataInserter.saveNewRoutine(routine);
                }
                if(activityState == MODIFY_ROUTINE_FUNCTION){

                }
                finish();
                return(true);
            case R.id.menuPlayRoutine:
                //Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PlayWorkoutActivity.class);
                Routine message = routine;
                intent.putExtra("playThisRoutine", message);
                startActivity(intent);
                finish();
                //TODO : check to finish() the activity when you don't need them anymore
                return(true);
            case R.id.menuHelpManageRoutine:
                Toast.makeText(this, "INFO", Toast.LENGTH_SHORT).show();
                helpDialog.show();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START |
            ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(listExercises, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);


            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}
