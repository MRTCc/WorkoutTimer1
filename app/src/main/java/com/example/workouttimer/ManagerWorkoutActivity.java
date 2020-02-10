package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class ManagerWorkoutActivity extends AppCompatActivity {

    private ListView listView;
    private DataProvider dataProvider;
    private ArrayList<Routine> listRoutine;
    private String favoriteRoutineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_workout);

        dataProvider = new DataProvider(this);
        listRoutine = dataProvider.getAllRoutines();
        favoriteRoutineName = dataProvider.getFavoriteRoutineName();

        // Initializing list view with the custom adapter
        ArrayList<ItemRoutine> itemList = new ArrayList<ItemRoutine>();
        ItemRoutineArrayAdapter itemArrayAdapter = new ItemRoutineArrayAdapter(this,
                0, itemList);
        listView = (ListView) findViewById(R.id.listViewWorkout);
        listView.setAdapter(itemArrayAdapter);

        // Populating list items, in other words loading of all routines
        Iterator<Routine> iterator = listRoutine.iterator();
       while(iterator.hasNext()){
            String name = iterator.next().getRoutineName();
            int type = 0;

            if(name.equals(favoriteRoutineName)){
                type = 1;
            }
            ItemRoutine itemRoutine = new ItemRoutine(name, type);
            itemList.add(itemRoutine);
        }
    }

    @SuppressLint("ResourceType")
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

}
