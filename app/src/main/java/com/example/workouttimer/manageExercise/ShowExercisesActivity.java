package com.example.workouttimer.manageExercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.R;
import com.example.workouttimer.dataAccess.DataInsert;
import com.example.workouttimer.dataAccess.DataProvider;
import com.example.workouttimer.manageWorkout.ManagerWorkoutActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ShowExercisesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapterExercise recyclerAdapter;
    private DataInsert dataInsert;
    private ArrayList<Exercise> listExercises;
    private Exercise deletingExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercises);

        DataProvider dataProvider = new DataProvider(this);
        dataInsert = new DataInsert(this);
        listExercises = dataProvider.getAllExercises();
        if(listExercises == null || listExercises.size() < 1){
            AlertDialog alertDialog =
                    new AlertDialog.Builder(ShowExercisesActivity.this).create();
            alertDialog.setTitle("Ops");
            alertDialog.setMessage("There are no exercises to be shown!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CLOSE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
        else {
            recyclerView = findViewById(R.id.rvShowExercises);
            recyclerAdapter = new RecyclerAdapterExercise(listExercises, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recyclerAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        Intent intent = new Intent(this, ShowExercisesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_help_show_exercise);
        helpDialog.setTitle("Help");

        Button dialogButton = helpDialog.findViewById(R.id.btnExitDialogShowExercise);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        switch(item.getItemId()) {
            case R.id.menuNewExercise:
                Intent intent = new Intent(this, ManageExerciseActivity.class);
                String message = "newExercise";
                intent.putExtra("newExercise", message);
                startActivity(intent);
                return(true);
            case R.id.menuHelpShowExercise:
                helpDialog.show();
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.RIGHT){
                deletingExercise = listExercises.get(position);
                listExercises.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
                Snackbar.make(recyclerView, deletingExercise.getExerciseName(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                listExercises.add(position, deletingExercise);
                                recyclerAdapter.notifyItemInserted(position);
                            }
                        }).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                        if(deletingExercise != null){
                            dataInsert.deleteExercise(deletingExercise);
                        }
                    }
                }, 4000);
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                                float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(
                            ShowExercisesActivity.this, R.color.IndianRed))
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}
