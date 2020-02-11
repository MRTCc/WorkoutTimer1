package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class ShowExercisesActivity extends AppCompatActivity {
    private ListView listView;
    private DataProvider dataProvider;
    private ArrayList<Exercise> listExercises
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercises);

        dataProvider = new DataProvider(this);
        listExercises = dataProvider.getAllExercises();

        // Initializing list view with the custom adapter
        ArrayList<ItemExercise> itemList = new ArrayList<ItemExercise>();
        ItemExerciseArrayAdapter itemArrayAdapter = new ItemExerciseArrayAdapter(this,
                0, itemList);
        listView = (ListView) findViewById(R.id.listViewExercises);
        listView.setAdapter(itemArrayAdapter);

        // Populating list items, in other words loading of all routines
        Iterator<Exercise> iterator = listExercises.iterator();
        while(iterator.hasNext()){
            Exercise exercise = iterator.next();
            ItemExercise itemExercise = new ItemExercise(exercise);
            itemList.add(itemExercise);
        }
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

        Button dialogButton = (Button) helpDialog.findViewById(R.id.btnExitDialogShowExercise);

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
            case R.id.menuDeleteShowExercise:
                //TODO: implementing delete of exercises functionality
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
