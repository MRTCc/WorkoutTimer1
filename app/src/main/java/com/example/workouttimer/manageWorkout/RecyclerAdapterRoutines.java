package com.example.workouttimer.manageWorkout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.manageRoutine.ManageRoutineActivity;
import com.example.workouttimer.R;
import com.example.workouttimer.dataAccess.DataInsert;
import com.example.workouttimer.entity.Routine;
import com.example.workouttimer.playWorkoutPackage.PlayWorkoutActivity;

import java.util.ArrayList;

class RecyclerAdapterRoutines extends RecyclerView.Adapter<RecyclerAdapterRoutines.ViewHolder> {
    private ArrayList<Routine> listRoutine;
    private String favoriteRoutineName;
    private Context context;
    private ManagerWorkoutActivity managerWorkoutActivity;

    RecyclerAdapterRoutines(ManagerWorkoutActivity managerWorkoutActivity) {
        this.managerWorkoutActivity = managerWorkoutActivity;
        this.listRoutine = managerWorkoutActivity.getListRoutine();
        this.favoriteRoutineName = managerWorkoutActivity.getFavoriteRoutineName();
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Routine routine = listRoutine.get(position);
        final String routineName = routine.getRoutineName();
        holder.routineName.setText(routineName);
        if(routineName.contentEquals(favoriteRoutineName)){
            holder.routineName.setBackgroundColor(context.getResources().getColor(R.color.Bisque));
        }
        holder.routineName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, " new favorite routine", Toast.LENGTH_SHORT).show();
                DataInsert dataInsert = new DataInsert(context);
                dataInsert.updateFavoriteRoutine(routineName);
                Intent intent = new Intent(context, ManagerWorkoutActivity.class);
                context.startActivity(intent);
                managerWorkoutActivity.finish();
                return true;
            }
        });
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            playRoutine = itemView.findViewById(R.id.btnPlayRoutineManagerWorkout);
            modifyRoutine = itemView.findViewById(R.id.btnModifyRoutineManagerWorkout);
            routineName = itemView.findViewById(R.id.txtRoutineNameManagerWorkout);
        }
    }
}
