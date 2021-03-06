package com.example.workouttimer.manageRoutine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
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

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.R;
import com.example.workouttimer.dataAccess.DataInsert;
import com.example.workouttimer.dataAccess.DataProvider;
import com.example.workouttimer.entity.Routine;
import com.example.workouttimer.manageExercise.ManageExerciseActivity;
import com.example.workouttimer.playWorkoutPackage.PlayWorkoutActivity;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ManageRoutineActivity extends AppCompatActivity {
    private final static int NEW_ROUTINE_FUNCTION = 0;
    private final static int MODIFY_ROUTINE_FUNCTION = 1;
    private Routine routine;
    private Routine entryRoutine;
    private ArrayList<Exercise> listExercises;
    private int activityState;
    private DataProvider dataProvider;
    private DataInsert dataInsert;
    private EditText eTxtRoutineName;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Exercise deletingExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ui and data initialization
        setContentView(R.layout.activity_manage_routine);
        dataProvider = new DataProvider(this);
        dataInsert = new DataInsert(this);
        eTxtRoutineName = findViewById(R.id.eTxtRoutineName);
        ImageButton btnAddExercise = findViewById(R.id.btnAddExercise);

        //intent fetch
        Intent intent = getIntent();
        if(intent.hasExtra("newRoutine")) {
            //Toast.makeText(this, "newRoutine", Toast.LENGTH_SHORT).show();
            routine = new Routine();
            entryRoutine = new Routine();
            activityState = NEW_ROUTINE_FUNCTION;
        }
        if(intent.hasExtra("manageThisRoutine")){
            //Toast.makeText(this, intent.getStringExtra("manageThisRoutine"), Toast.LENGTH_SHORT).show();
            try {
                routine = (Routine) intent.getExtras().getSerializable("manageThisRoutine");
                routine = dataProvider.getCompleteRoutine(routine);
            }catch(Exception e){
                e.printStackTrace();
            }
            assert routine != null;
            entryRoutine = clone(routine);
            activityState = MODIFY_ROUTINE_FUNCTION;
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
        try {
            if(intent.hasExtra("addThisExercise")){
                Exercise exercise = (Exercise) intent.getExtras().getSerializable("addThisExercise");
                routine.getListExercise().add(exercise);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.rvExercises);
        recyclerAdapter = new RecyclerAdapter(routine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        swipeRefreshLayout = findViewById(R.id.srLayoutManageRoutine);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageRoutineActivity.this);
                final ArrayList<String> listItems = new ArrayList<>();
                final ArrayList<Exercise> listAllExercises = dataProvider.getAllExercises();
                for (Exercise exercise : listAllExercises) {
                    if (!listExercises.contains(exercise)) {
                        listItems.add(exercise.getExerciseName());
                    }
                }
                final String[] items = new String[listItems.size()];
                for(int i = 0; i< items.length; i++){
                    items[i] = listItems.get(i);
                }
                // Boolean array for initial selected items
                final boolean[] checkedItems = new boolean[listItems.size()];
                Arrays.fill(checkedItems, Boolean.FALSE);
                // Set multiple choice items for alert dialog
                builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                        String currentItem = listItems.get(which);
                        Toast.makeText(getApplicationContext(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                // Specify the dialog is not cancelable
                builder.setCancelable(false);
                // Set a title for alert dialog
                builder.setTitle("Choose which exercises to add");
                // Set the positive/yes button click listener
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i<checkedItems.length; i++){
                            boolean checked = checkedItems[i];
                            if (checked) {
                                Exercise exercise = dataProvider.getExercise(items[i]);
                                routine.getListExercise().add(exercise);
                            }
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });
                // Set the negative/no button click listener
                builder.setNegativeButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), ManageExerciseActivity.class);
                        String message = "newExercise";
                        intent.putExtra("newExercise", message);
                        startActivity(intent);
                    }
                });
                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh of data
        Iterator<Exercise> iterator = listExercises.iterator();
        ArrayList<Exercise> newList = new ArrayList<>();
        while(iterator.hasNext()){
            Exercise exercise = iterator.next();
            exercise = dataProvider.getExercise(exercise.getExerciseName());
            newList.add(exercise);
        }
        listExercises.clear();
        listExercises.addAll(newList);
        recyclerAdapter.notifyDataSetChanged();
    }


    private void showRoutineName() {
        eTxtRoutineName.setText(routine.getRoutineName());
    }

    private Routine clone(@NotNull Routine routine) {
        Routine cloneRoutine = new Routine();
        cloneRoutine.setRoutineName(routine.getRoutineName());
        cloneRoutine.setDateOfCreation(routine.getDateOfCreation());
        cloneRoutine.setnDone(routine.getnDone());
        cloneRoutine.setTotTime(routine.getTotTime());
        cloneRoutine.setNumberOfExercises(routine.getNumberOfExercises());
        ArrayList<Exercise> newListExercise = new ArrayList<>(routine.getListExercise());
        cloneRoutine.setListExercise(newListExercise);
        return cloneRoutine;
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
        Button dialogButton = helpDialog.findViewById(R.id.btnExitDialogManageRoutine);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });
        switch(item.getItemId()) {
            case R.id.menuSaveRoutine:
                //Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                if(activityState == NEW_ROUTINE_FUNCTION){
                    dataInsert.saveNewRoutine(routine);
                }
                if(activityState == MODIFY_ROUTINE_FUNCTION){
                    dataInsert.updateRoutine(routine, entryRoutine);
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
            ItemTouchHelper.END, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(listExercises, fromPosition, toPosition);
            try {
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.RIGHT){
                deletingExercise = routine.getListExercise().get(position);
                routine.getListExercise().remove(position);
                recyclerAdapter.notifyItemRemoved(position);
                Snackbar.make(recyclerView, deletingExercise.getExerciseName(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                routine.getListExercise().add(position, deletingExercise);
                                recyclerAdapter.notifyItemInserted(position);
                            }
                        }).show();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                                float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(
                            ManageRoutineActivity.this, R.color.IndianRed))
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog alertDialog =
                new androidx.appcompat.app.AlertDialog.Builder(ManageRoutineActivity.this).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage(getResources().getString(R.string.do_you_exit));
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
