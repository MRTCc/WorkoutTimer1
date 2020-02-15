package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ManagerWorkoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapterRoutines recyclerAdapter;
    private DataProvider dataProvider;
    private DataInsert dataInsert;
    private ArrayList<Routine> listRoutine;
    private String favoriteRoutineName;
    private Routine deletingRoutine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_workout_1);

        dataProvider = new DataProvider(this);
        dataInsert = new DataInsert(this);
        listRoutine = dataProvider.getAllRoutines();
        favoriteRoutineName = dataProvider.getFavoriteRoutineName();
        deletingRoutine = null;
        recyclerView = findViewById(R.id.rvRoutines);
        recyclerAdapter = new RecyclerAdapterRoutines(this);
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
        /*
        finish();
        Intent intent = new Intent(this, ManagerWorkoutActivity.class);
        startActivity(intent);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_routine_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Dialog helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.dialog_help_manager_workout);
        helpDialog.setTitle("Help");

        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDialogManagerWorkout);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        switch(item.getItemId()) {
            case R.id.menuNewRoutine:
                Intent intent = new Intent(this, ManageRoutineActivity.class);
                String message = "newRoutine";
                intent.putExtra("newRoutine", message);
                startActivity(intent);
                return(true);
            case R.id.menuHelpManagerRoutine:
                helpDialog.show();
                return(true);
            case R.id.menuDeleteManagerRoutine:
                //TODO: implementing delete of routines functionality
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.RIGHT){
                deletingRoutine = listRoutine.get(position);
                listRoutine.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
                Snackbar.make(recyclerView, deletingRoutine.getRoutineName(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listRoutine.add(position, deletingRoutine);
                                recyclerAdapter.notifyItemInserted(position);
                            }
                        }).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                        if(deletingRoutine != null){
                            dataInsert.deleteRoutine(deletingRoutine);
                        }
                    }
                }, 5000);
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(
                            ManagerWorkoutActivity.this, R.color.IndianRed))
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void notifyNewFavoriteRoutine(int position){
        recyclerAdapter.notifyItemChanged(position);
    }

    public String getFavoriteRoutineName() {
        return favoriteRoutineName;
    }

    public ArrayList<Routine> getListRoutine() {
        return listRoutine;
    }

    public void setFavoriteRoutineName(String favoriteRoutineName) {
        this.favoriteRoutineName = favoriteRoutineName;
    }
}
