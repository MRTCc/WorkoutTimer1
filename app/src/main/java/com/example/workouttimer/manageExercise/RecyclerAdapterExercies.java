package com.example.workouttimer.manageExercise;

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

import java.util.ArrayList;

public class RecyclerAdapterExercies extends RecyclerView.Adapter<RecyclerAdapterExercies.ViewHolder>{
    private ArrayList<Exercise> listExercises;
    private Context context;
    private String setsToDoString;
    private String repsToDoString;
    private String workTimeString;
    private String restTimeString;

    public RecyclerAdapterExercies(ArrayList<Exercise> listExercises, Context context) {
        this.listExercises = listExercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_show_exercises, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Exercise exercise = listExercises.get(position);
        setDataToShow(exercise);
        holder.exerciseName.setText(exercise.getExerciseName());
        String dataString = "S: " + setsToDoString + "    R: " + repsToDoString +
                "    W: " + workTimeString + "    RT: " + restTimeString;
        holder.exerciseData.setText(dataString);
        holder.modifyExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageExerciseActivity.class);
                intent.putExtra("modifyThisExercise", exercise);
                context.startActivity(intent);
            }
        });
    }

    private void setDataToShow(Exercise exercise){
        if(exercise == null)
            return;
        int setsToDo = exercise.getSetsToDo();
        int repsToDo = exercise.getRepsToDo();
        int workTime = exercise.getWorkTime();
        int restTime = exercise.getRestTime();
        if(setsToDo < 1){
            setsToDoString = "---";
        }
        else{
            setsToDoString = Integer.toString(setsToDo);
        }
        if(repsToDo < 1){
            repsToDoString = "---";
        }
        else{
            repsToDoString = Integer.toString(repsToDo);
        }
        if(workTime < 1){
            workTimeString = "---";
        }
        else{
            workTimeString = Integer.toString(workTime);
        }
        if(restTime < 1){
            restTimeString = "---";
        }
        else{
            restTimeString = Integer.toString(restTime);
        }
    }

    @Override
    public int getItemCount() {
        return listExercises.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton modifyExercise;
        TextView exerciseName, exerciseData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modifyExercise = itemView.findViewById(R.id.btnModifyExercise);
            exerciseName = itemView.findViewById(R.id.txtExerciseName);
            exerciseData = itemView.findViewById(R.id.txtExerciseData);
        }
    }
}
