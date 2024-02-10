package moh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import finalproject.Book;
import finalproject.CaptionedImagesAdapter;
import finalproject.MainActivity;
import ps.example.mmoy.Course;
import ps.example.mmoy.CourseAdapter;
import ps.example.mmoy.R;

public class TaskManagerActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTask;
    private ArrayList<Course> coursesList;
    private ArrayList<Task> tasksList;
    private RecyclerView task_recycler;
    private TaskAdapter adapter;
    static int studentId = 0;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_task_manager);

        queue = Volley.newRequestQueue(this);

        tasksList = new ArrayList<>();

        setupViews();

        Intent intent = getIntent();

        studentId = getIntent().getIntExtra("studentId", -1);
        fetchTasksForStudent(studentId);
        coursesList = new ArrayList<Course>();

        Intent recievedIntent = getIntent();
        if (recievedIntent != null && recievedIntent.hasExtra("list")) {
            coursesList = (ArrayList<Course>) recievedIntent.getSerializableExtra("list");
        }

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskManagerActivity.this, AddTaskActivity.class);
                intent1.putExtra("coursesList",coursesList);
                intent1.putExtra("studentId",studentId);
                startActivity(intent1);
            }
        });
    }

    private void setupViews() {
        fabAddTask = findViewById(R.id.fabAddTask);
        task_recycler = findViewById(R.id.task_recycler);
        task_recycler.setHasFixedSize(true);
        task_recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasksList);
        task_recycler.setAdapter(adapter);
    }

    private void fetchTasksForStudent(int studentId) {
        String url = "http://10.0.2.2:5000/tasks/" + studentId;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Clear existing tasks
                        tasksList.clear();

                        // Parse the JSON response and convert it into Task objects
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject taskObject = response.getJSONObject(i);
                            int taskID = taskObject.getInt("task_id");
                            int stdID = taskObject.getInt("student_id");
                            int courseID = taskObject.getInt("course_id");
                            String taskType = taskObject.getString("task_type");
                            String taskDes = taskObject.getString("description");
                            String taskPriority = taskObject.getString("priority");
                            String taskDueDate = taskObject.getString("due_date");
                            String taskDueTime = taskObject.getString("due_time");

                            Task task = new Task(taskID, stdID, courseID, taskType, taskDes, taskPriority, taskDueDate, taskDueTime);
                            tasksList.add(task);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());
        queue.add(jsonArrayRequest);
    }

}