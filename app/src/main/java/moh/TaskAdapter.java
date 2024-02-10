package moh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ps.example.mmoy.Course;
import ps.example.mmoy.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> tasksList;

    public TaskAdapter(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTaskTitle;
        public TextView txtDL;
        public TextView txtPriorityDisplay;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
            txtDL = itemView.findViewById(R.id.txtDL);
            txtPriorityDisplay = itemView.findViewById(R.id.txtPriorityDisplay);

        }
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.moh_task_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        Task currTask = tasksList.get(position);
        holder.txtTaskTitle.setText(currTask.getTitle());
        String due = currTask.getTaskDueDate() + "\n" + currTask.getTaskDueDate();
        holder.txtDL.setText(due);//
        holder.txtPriorityDisplay.setText(currTask.getTaskPriority());
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

}
