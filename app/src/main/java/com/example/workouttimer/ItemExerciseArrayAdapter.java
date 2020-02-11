package com.example.workouttimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemExerciseArrayAdapter extends ArrayAdapter {
    Context context;
    private ArrayList<ItemExercise> itemList;

    public ItemExerciseArrayAdapter(Context context, int layoutId, ArrayList<ItemExercise> itemList){
        super(context, layoutId, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemExercise item = (ItemExercise) getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_show_exercises, parent,
                    false);
            viewHolder.item = (TextView) convertView.findViewById(R.id.txtExerciseName);
            viewHolder.dataItem = (TextView) convertView.findViewById(R.id.txtExerciseData);
            viewHolder.btnModifyExercise = (ImageButton) convertView.findViewById(R.id.btnModifyExercise);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final TextView txtItem = viewHolder.item;
        final TextView txtDataItem = viewHolder.dataItem;
        final ImageButton btnModifyExercise = viewHolder.btnModifyExercise;

        txtItem.setText(item.getName());
        String showData = "S: " + item.getSetsToDo() + "    R: " + item.getRepsToDo() + "    W: " +
                item.getWorkTime() + "    RT: " + item.getRestTime();
        txtDataItem.setText(showData);

        final ItemExercise itemToSend = item;
        btnModifyExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageExerciseActivity.class);
                Exercise message = itemToSend.getExercise();
                intent.putExtra("modifyThisExercise", message);
                context.startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }



    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        TextView item;
        TextView dataItem;
        ImageButton btnModifyExercise;
    }

}
