package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagerWorkoutActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_workout);

        // Initializing list view with the custom adapter
        ArrayList<ItemRoutine> itemList = new ArrayList<ItemRoutine>();
        ItemRoutineArrayAdapter itemArrayAdapter = new ItemRoutineArrayAdapter(this,
                R.layout.row_manager_workout, itemList);
        listView = (ListView) findViewById(R.id.listViewWorkout);
        listView.setAdapter(itemArrayAdapter);

        // Populating list items
        for(int i=0; i<100; i++) {
            itemList.add(new ItemRoutine("Item " + i));
        }
    }
}
