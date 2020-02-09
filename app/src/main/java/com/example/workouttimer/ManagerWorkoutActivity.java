package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        // Initializing list view with the custom adapter
        ArrayList<ItemRoutine> itemList = new ArrayList<ItemRoutine>();
        ItemRoutineArrayAdapter itemArrayAdapter = new ItemRoutineArrayAdapter(this,
                R.layout.row_manager_workout, itemList);
        listView = (ListView) findViewById(R.id.listViewWorkout);
        listView.setAdapter(itemArrayAdapter);

        favoriteRoutineName = dataProvider.getFavoriteRoutineName();

        // Populating list items, in other words loading of all routines
        Iterator<Routine> iterator = listRoutine.iterator();
       while(iterator.hasNext()){
            ItemRoutine itemRoutine = new ItemRoutine(iterator.next().routineName);
            itemList.add(itemRoutine);
        }


    }

}
