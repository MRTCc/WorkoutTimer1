package com.example.workouttimer.manageRoutine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.entity.Exercise;
import com.example.workouttimer.R;
import com.example.workouttimer.entity.Routine;
import com.example.workouttimer.manageExercise.ManageExerciseActivity;

import java.util.ArrayList;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Routine routine;
    private ArrayList<Exercise> listExercises;
    private Context context;

     RecyclerAdapter(Routine routine) {
        this.routine = routine;
        listExercises = routine.getListExercise();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_manage_routine, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Exercise exercise = listExercises.get(position);
        exercise.setPosition(position);
        holder.exerciseName.setText(exercise.getExerciseName());
        String showData = "S: " + exercise.getSetsToDo() + "    R: " + exercise.getRepsToDo() +
                "    W: " + exercise.getWorkTime() + "    RT: " + exercise.getRestTime();
        holder.exerciseData.setText(showData);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageExerciseActivity.class);
                intent.putExtra("modifyThisExercise", exercise);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routine.getListExercise().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton imageButton;
        TextView exerciseName, exerciseData;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.btnModifyExercise);
            exerciseName = itemView.findViewById(R.id.txtExerciseNameManageRoutine);
            exerciseData = itemView.findViewById(R.id.txtExerciseDataManageRoutine);


        }
    }
}
