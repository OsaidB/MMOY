package moh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import ps.example.mmoy.R;

public class TasksDetailsActivity extends AppCompatActivity {

    private RequestQueue queue;
    private Button btnUpdateTask, btnDeleteTask, btnBack;
    private TextView txtTaskCourseView, txtTaskDesView, txtTaskPriorityView, txtTaskDDView, txtTaskDTView;
    int taskId, courseId, studentId = TaskManagerActivity.studentId;
    String dueTime, dueDate, priority, description, taskType, courseCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_activity_tasks_details);

        queue = Volley.newRequestQueue(this);

        setUpViews();
        fillView();

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksDetailsActivity.this, UpdateTaskActivity.class);
                intent.putExtra("taskId", taskId);
                intent.putExtra("courseId", courseId);
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
                // Show confirmation dialog before deleting
                new AlertDialog.Builder(TasksDetailsActivity.this)
                        .setTitle("Delete Task") // Set Dialog Title
                        .setMessage("Are you sure you want to delete this task?") // Set Dialog Message
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteTask(taskId);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null) // Do nothing on no
                        .show();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillView() {
        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId",-1);
        courseId = intent.getIntExtra("courseId",-1);
        courseCode = intent.getStringExtra("courseCode");
        taskType = intent.getStringExtra("taskType");
        description = intent.getStringExtra("description");
        priority = intent.getStringExtra("priority");
        dueDate = intent.getStringExtra("dueDate");
        dueTime = intent.getStringExtra("dueTime");
        txtTaskCourseView.setText("Task : " + courseCode + " " + taskType);
        txtTaskDesView.setText("Description : " + description);
        txtTaskPriorityView.setText("Priority : " + priority);
        txtTaskDDView.setText("Due Date : " + dueDate);
        txtTaskDTView.setText("Due Time : " + dueTime);
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

    private void deleteTask(int taskId) {
        String url = "http://10.0.2.2:5000/deleteTask/" + taskId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    // Handle response
                    Toast.makeText(getApplicationContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, redirect the user or refresh the data
                },
                error -> {
                    // Handle error
                    Toast.makeText(getApplicationContext(), "Error deleting task", Toast.LENGTH_SHORT).show();
                });

        queue.add(stringRequest);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Ensure taskId is initialized before calling.
//        if (taskId != -1) {
//            fetchTaskDetails(taskId);
//        }
//    }


//    private void fetchTaskDetails(int taskId) {
//        String url = "http://10.0.2.2:5000/getTaskDetails/" + taskId;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                response -> {
//                    Log.d("TaskDetails", "Response: " + response.toString());
//                    try {
//                        courseCode = response.getString("course_code");
//                        taskType = response.getString("task_type");
//                        description = response.getString("description");
//                        priority = response.getString("priority");
//                        dueDate = response.getString("due_date");
//                        dueTime = response.getString("due_time");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(TasksDetailsActivity.this, "Error parsing task details", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> {
//                    Log.e("TaskDetails", "Error: " + error.toString()); // Log errors
//                    Toast.makeText(TasksDetailsActivity.this, "Failed to fetch task details", Toast.LENGTH_SHORT).show();
//                });
//
//        queue.add(jsonObjectRequest);
//    }
}