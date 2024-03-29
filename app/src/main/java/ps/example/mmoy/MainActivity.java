package ps.example.mmoy;

import static ps.example.mmoy.LoginActivity.STD_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import moh.TaskManagerActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSchedule;
    Button btnBooks;
    Button button_Tasks;
    private RequestQueue requestQueue;

    private ArrayList<Course> coursesList;


    //private ArrayList<Task> tasksList;
    ///////////////////////////////////////////////
    static SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osa_main);
        coursesList = new ArrayList<Course>();
        //tasksList = new ArrayList<Task>();

        setupViews();

        setupSharedPrefs();             //siting SharedPrefs up (making the app ready to Read/Write)

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(STD_ID)) {
            int studentId = intent.getIntExtra(STD_ID, -1);
            editor.putInt("studentId", studentId);
            editor.apply();
        }

        int studentId = prefs.getInt("studentId", -1); // -1 is the default value if no id is found
        if (studentId != -1) {
            fetchCoursesForStudent(studentId);
            //fetchTasksForStudent(studentId);

        } else {
            System.out.println("studentId=-1");
        }


//        fetchCoursesFromDatabase();
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Activity for Feature 1

//                String title = "Insert A Course";
                if (coursesList.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, scheduleForm.class);
//                    title = "Insert Your First Course";
                    intent.putExtra("title", "Insert Your First Course");
                    intent.putExtra("list", coursesList);
                    intent.putExtra(STD_ID, studentId);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, scheduleActivity.class);
                    intent.putExtra("list", coursesList);
                    startActivity(intent);
                }


            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, finalproject.searchOrAdd.class);
                intent.putExtra(STD_ID, studentId);
                startActivity(intent);
            }
        });

        button_Tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskManagerActivity.class);
                intent.putExtra("list", coursesList);
                //intent.putExtra("listTasks", tasksList);
                intent.putExtra("studentId", studentId);
                startActivity(intent);
            }
        });
    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();  //to Write
    }

    private void setupViews() { //just making the hooks
        requestQueue = Volley.newRequestQueue(this);
//        requestQueue = Volley.newRequestQueue(this);
        //WHAT
        btnSchedule = findViewById(R.id.btnSchedule);   //1
//        edtTxt_courseTitle = findViewById(R.id.edtTxt_courseTitle); //2
        btnBooks = findViewById(R.id.btnBooks);
        button_Tasks = findViewById(R.id.button_Tasks);

    }

    private void fetchCoursesFromDatabase() {
        // Assuming you have a function to make the HTTP request
        // For example with Volley:
        String url = "http:/10.0.2.2:5000/courses";

        // Prepare the request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject courseObject = response.getJSONObject(i);

                        String courseCode = courseObject.getString("course_code");
                        String courseTitle = courseObject.getString("course_title");

                        String instructor = courseObject.getString("instructor");

                        String classNum = courseObject.getString("class_num");

                        String courseDays = courseObject.getString("course_days");
                        String timeFrom = courseObject.getString("time_from");
                        String timeTo = courseObject.getString("time_to");

                        String faculty = courseObject.getString("faculty");
                        String roomNum = courseObject.getString("room_num");

                        // Add to enteredCourses list
                        coursesList.add(new Course(courseCode, courseTitle, instructor, classNum,
                                courseDays, timeFrom, timeTo, faculty, roomNum));

                        System.out.println(coursesList);

                    } catch (JSONException e) {
                        Log.d("Error", e.toString());
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Error_json", error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }

    private void fetchCoursesForStudent(int studentId) {


        String url = "http://10.0.2.2:5000/student_courses/" + studentId;

        // Prepare the request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject courseObject = response.getJSONObject(i);

                            String courseCode = courseObject.getString("course_code");
                            String courseTitle = courseObject.getString("course_title");

                            String instructor = courseObject.getString("instructor");

                            String classNum = courseObject.getString("class_num");

                            String courseDays = courseObject.getString("course_days");
                            String timeFrom = courseObject.getString("time_from");
                            String timeTo = courseObject.getString("time_to");

                            String faculty = courseObject.getString("faculty");
                            String roomNum = courseObject.getString("room_num");

                            // Add to enteredCourses list
                            coursesList.add(new Course(courseCode, courseTitle, instructor, classNum,
                                    courseDays, timeFrom, timeTo, faculty, roomNum));

                            System.out.println(coursesList);

                        } catch (JSONException e) {
                            Log.d("Error", e.toString());
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                });

        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }

//    private void fetchTasksForStudent(int studentId) {
//        String url = "http://10.0.2.2:5000/tasks/" + studentId;
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
//                response -> {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject taskObject = response.getJSONObject(i);
//                            int task_id = taskObject.getInt("task_id");
//                            int course_id = taskObject.getInt("course_id");
//                            String task_type = taskObject.getString("task_type");
//                            String description = taskObject.getString("description");
//                            String priority = taskObject.getString("priority");
//                            String due_date = taskObject.getString("due_date");
//                            String due_Time = taskObject.getString("due_Time");
//
//                            Task task = new Task(task_id, studentId, course_id, task_type, description, priority, due_date, due_Time);
//                            tasksList.add(task);
//
//                            //System.out.println(tasksList);
//
//                        } catch (JSONException e) {
//                            Log.d("Error", e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//
//                },
//                error -> {
//                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("Error_json", error.toString());
//                });
//        requestQueue.add(request);
//    }


}