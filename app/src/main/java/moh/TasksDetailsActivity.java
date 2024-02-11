package moh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ps.example.mmoy.R;

public class TasksDetailsActivity extends AppCompatActivity {

    private Button btnUpdateTask, btnDeleteTask, btnBack;
    private TextView txtTaskCourseView, txtTaskDesView, txtTaskPriorityView, txtTaskDDView, txtTaskDTView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_activity_tasks_details);

        setUpViews();

        Intent intent = getIntent();
        String courseCode = intent.getStringExtra("courseCode");
        String taskType = intent.getStringExtra("taskType");
        String description = intent.getStringExtra("description");
        String priority = intent.getStringExtra("priority");
        String dueDate = intent.getStringExtra("dueDate");
        String dueTime = intent.getStringExtra("dueTime");
        txtTaskCourseView.setText("Task : " + courseCode + " " + taskType);
        txtTaskDesView.setText("Description : " + description);
        txtTaskPriorityView.setText("Priority : " + priority);
        txtTaskDDView.setText("Due Date : " + dueDate);
        txtTaskDTView.setText("Due Time : " + dueTime);

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksDetailsActivity.this, UpdateTaskActivity.class);
                intent.putExtra("courseCode", courseCode);
                intent.putExtra("taskType", taskType);
                intent.putExtra("description", description);
                intent.putExtra("priority", priority);
                intent.putExtra("dueDate", dueDate);
                intent.putExtra("dueTime", dueTime);
                intent.putExtra("studentId", TaskManagerActivity.studentId);
                startActivity(intent);
            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpViews(){
        txtTaskCourseView = findViewById(R.id.txtTaskCourseView);
        txtTaskDesView = findViewById(R.id.txtTaskDesView);
        txtTaskPriorityView = findViewById(R.id.txtTaskPriorityView);
        txtTaskDDView = findViewById(R.id.txtTaskDDView);
        txtTaskDTView = findViewById(R.id.txtTaskDTView);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
        btnBack = findViewById(R.id.btnBack);
    }

//    private void displayTask(){
////        txtTaskCourseView.setText("Task : " + courseCode + " " + taskType);
////        txtTaskDesView.setText("Description : " + description);
////        txtTaskPriorityView.setText("Priority : " + priority);
////        txtTaskDDView.setText("Due Date : " + dueDate);
////        txtTaskDTView.setText("Due Time : " + dueTime);
//    }
}