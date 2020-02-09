package com.example.workouttimer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemRoutineArrayAdapter extends ArrayAdapter<ItemRoutine> {
    private final  static int FAVORITE_ROUTINE = 1;

    Context context;
    private ArrayList<ItemRoutine> itemList;

    public ItemRoutineArrayAdapter(Context context, int layoutId, ArrayList<ItemRoutine> itemList) {
        super(context, layoutId, itemList);

        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        ItemRoutine itemRoutine = itemList.get(position);
        return itemRoutine.getType();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemRoutine item = getItem(position);
        int itemViewType = getItemViewType(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            if(itemViewType == FAVORITE_ROUTINE) {
                convertView = inflater.inflate(R.layout.row_manager_workout_favorite, parent,
                        false);
                viewHolder.item = (TextView) convertView.findViewById(R.id.txtRoutineNameF);
                convertView.setTag(viewHolder); // view lookup cache stored in tag
            }
            else{
                convertView = inflater.inflate(R.layout.row_manager_workout, parent,
                        false);
                viewHolder.item = (TextView) convertView.findViewById(R.id.txtRoutineName);
                convertView.setTag(viewHolder); // view lookup cache stored in tag
            }


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
