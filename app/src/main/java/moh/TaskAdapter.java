package moh;

import static moh.TaskManagerActivity.courseIdToCodeMap;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ps.example.mmoy.Course;
import ps.example.mmoy.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> tasksList;
    public void setFilteredList(ArrayList<Task> filteredList){
        this.tasksList = filteredList;
        notifyDataSetChanged();
    }

    public TaskAdapter(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTaskTitle;
        public TextView txtDL;
        public TextView txtPriorityDisplay;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
            txtDL = itemView.findViewById(R.id.txtDL);
            txtPriorityDisplay = itemView.findViewById(R.id.txtPriorityDisplay);
            cardView = (CardView) itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

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
        int taskId = currTask.getTaskID();
        int courseId = currTask.getCourseID();
        String courseCode = courseIdToCodeMap.get(currTask.getCourseID());
        String taskTitle = courseCode + "\n" + currTask.getTaskType();
        holder.txtTaskTitle.setText(taskTitle);
        String due = "Dead Line \n" + currTask.getTaskDueDate() + "\n" + currTask.getTaskDueTime();
        holder.txtDL.setText(due);//
        String tp = "Priority \n" + currTask.getTaskPriority();
        holder.txtPriorityDisplay.setText(tp);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TasksDetailsActivity.class);
            intent.putExtra("courseCode", courseCode);
            intent.putExtra("taskId", taskId);
            intent.putExtra("courseId", courseId);
            intent.putExtra("taskType", currTask.getTaskType());
            intent.putExtra("description", currTask.getTaskDes());
            intent.putExtra("priority", currTask.getTaskPriority());
            intent.putExtra("dueDate", currTask.getTaskDueDate());
            intent.putExtra("dueTime", currTask.getTaskDueTime());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

}
