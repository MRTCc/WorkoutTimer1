package com.example.workouttimer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ItemRoutine item = getItem(position);
        int itemViewType = getItemViewType(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        try {
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());

                if (itemViewType == FAVORITE_ROUTINE) {
                    convertView = inflater.inflate(R.layout.row_manager_workout_favorite, parent,
                            false);
                    viewHolder.item = (TextView) convertView.findViewById(R.id.txtRoutineNameF);
                    viewHolder.btnPlayRoutine = (ImageButton) convertView.findViewById(R.id.btnPlayRoutineF);
                    viewHolder.btnModifyRoutine = (ImageButton) convertView.findViewById(R.id.btnModifyRoutineF);
                    convertView.setTag(viewHolder); // view lookup cache stored in tag
                } else {
                    convertView = inflater.inflate(R.layout.row_manager_workout, parent,
                            false);
                    viewHolder.item = (TextView) convertView.findViewById(R.id.txtRoutineName);
                    viewHolder.btnPlayRoutine = (ImageButton) convertView.findViewById(R.id.btnPlayRoutine);
                    viewHolder.btnModifyRoutine = (ImageButton) convertView.findViewById(R.id.btnModifyRoutine);
                    convertView.setTag(viewHolder); // view lookup cache stored in tag
                }


            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final TextView txtItem = viewHolder.item;
            final ImageButton btnPlayRoutine = viewHolder.btnPlayRoutine;
            final ImageButton btnModifyRoutine = viewHolder.btnModifyRoutine;

            // Populate the data into the template view using the data object
            txtItem.setText(item.getName());

            //selection of favorite routine
            txtItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(context, "funziona", Toast.LENGTH_SHORT).show();
                    if (getItemViewType(position) == 0) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_change_favorite_routine);
                        dialog.setTitle("Keep attention");

                        Button btnConfirm = dialog.findViewById(R.id.btnConfirmChangeFavorite);
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DbManager dbManager = new DbManager(context);
                                dbManager.open("write");
                                dbManager.insertFavoriteRoutine(txtItem.getText().toString());
                                dbManager.close();
                                Intent intent = new Intent(context, ManagerWorkoutActivity.class);
                                boolean message = false;
                                intent.putExtra("refresh", message);
                                context.startActivity(intent);
                                dialog.dismiss();
                                ((Activity) context).finish();
                            }
                        });

                        Button btnGoBack = dialog.findViewById(R.id.btnUndoChangeFavorite);
                        btnGoBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    return false;
                }
            });

            btnPlayRoutine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayWorkoutActivity.class);
                    Routine message = item.getRoutine();
                    intent.putExtra("playThisRoutine", message);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });

            final ItemRoutine itemToSend = item;
            btnModifyRoutine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ManageRoutineActivity.class);
                    Routine message = itemToSend.getRoutine();
                    intent.putExtra("manageThisRoutine", message);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        TextView item;
        ImageButton btnPlayRoutine;
        ImageButton btnModifyRoutine;
    }


}
