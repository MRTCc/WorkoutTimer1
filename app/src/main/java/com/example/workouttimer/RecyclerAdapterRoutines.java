package com.example.workouttimer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterRoutines extends RecyclerView.Adapter<RecyclerAdapterRoutines.ViewHolder> {
    ArrayList<Routine> listRoutine;
    String favoriteRoutineName;
    Context context;

    public RecyclerAdapterRoutines(ArrayList<Routine> listRoutine, String favoriteRoutineName) {
        this.listRoutine = listRoutine;
        this.favoriteRoutineName = favoriteRoutineName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_manager_workout_1, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Routine routine = listRoutine.get(position);
        String routineName = routine.getRoutineName();
        holder.routineName.setText(routineName);
        if(routineName.contentEquals(favoriteRoutineName)){
            holder.routineName.setBackgroundColor(context.getResources().getColor(R.color.Bisque));
        }
        holder.playRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayWorkoutActivity.class);
                intent.putExtra("playThisRoutine", routine);
                context.startActivity(intent);
            }
        });
        holder.modifyRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageRoutineActivity.class);
                intent.putExtra("manageThisRoutine", routine);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRoutine.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton playRoutine, modifyRoutine;
        TextView routineName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playRoutine = itemView.findViewById(R.id.btnPlayRoutineManagerWorkout);
            modifyRoutine = itemView.findViewById(R.id.btnModifyRoutineManagerWorkout);
            routineName = itemView.findViewById(R.id.txtRoutineNameManagerWorkout);
        }
    }
}
