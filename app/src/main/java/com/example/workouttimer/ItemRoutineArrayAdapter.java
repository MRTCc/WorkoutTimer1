package com.example.workouttimer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemRoutineArrayAdapter extends ArrayAdapter<ItemRoutine> {

    Context context;
    private int listItemLayout;
    private String favoriteRoutineName;

    public ItemRoutineArrayAdapter(Context context, int layoutId, ArrayList<ItemRoutine> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemRoutine item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(listItemLayout, parent, false);

            viewHolder.item = (TextView) convertView.findViewById(R.id.txtRoutineName);
            convertView.setTag(viewHolder); // view lookup cache stored in tag
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final TextView txtItem = viewHolder.item;

        // Populate the data into the template view using the data object
        txtItem.setText(item.getName());



        //selection of favorite routine
        txtItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public  boolean onLongClick(View v) {
                //Toast.makeText(context, "funziona", Toast.LENGTH_SHORT).show();
                txtItem.setBackgroundColor(Color.BLUE);
                return false;
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        TextView item;
    }


}