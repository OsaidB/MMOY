package moh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import finalproject.Book;
import finalproject.CaptionedImagesAdapter;
import finalproject.MainActivity;
import ps.example.mmoy.Course;
import ps.example.mmoy.CourseAdapter;
import ps.example.mmoy.R;

public class TaskManagerActivity extends AppCompatActivity {
    public static Map<Integer, String> courseIdToCodeMap = new HashMap<>();
    private FloatingActionButton fabAddTask;
    private ArrayList<Course> coursesList;
    private ArrayList<Task> tasksList;
    private RecyclerView task_recycler;
    private TaskAdapter adapter;
    public static int studentId = 0;
    private RequestQueue queue;
    private SearchView svTask;

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
        fetchCoursesForStudent(studentId);
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

        svTask.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void setupViews() {
        svTask = findViewById(R.id.svTask);
        svTask.clearFocus();
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

    private void fetchCoursesForStudent(int studentId) {
        String url = "http://10.0.2.2:5000/student_courses/" + studentId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject courseObject = response.getJSONObject(i);
                            int courseId = courseObject.getInt("course_id");
                            String courseCode = courseObject.getString("course_code");
                            // Store course ID and code mapping
                            courseIdToCodeMap.put(courseId, courseCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("Error", error.toString()));

        queue.add(request);
    }

    private void filterList(String text){
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for(Task task : tasksList){
            String courseCode = getCourseCodeForTask(task);
            if (courseCode != null && courseCode.toLowerCase().contains(text.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        if (filteredTasks.isEmpty()) {
            Toast.makeText(this, "No Tasks found with that course code", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredTasks);
        }
    }
    private String getCourseCodeForTask(Task task) {
        return courseIdToCodeMap.get(task.getCourseID());
    }
}