package moh;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ps.example.mmoy.Course;
import ps.example.mmoy.R;

public class AddTaskActivity extends AppCompatActivity {

    private RequestQueue queue;
    private EditText edtTaskDes, edtDueDate, edtDueTime;
    private Spinner spnTaskType, spnCourseCode;
    private RadioGroup rgPrioritySelector;
    private FloatingActionButton fabDate, fabTime;
    private Button btnAddTask;
    static int studentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_add_task);
        ////////////////////// volley //////////////////////
        queue = Volley.newRequestQueue(this);

        ////////////////////// hocs //////////////////////
        setUpViews();

        ///////////////////////////////////////Get The student Id///////////////////////////////////////////////
        studentId = getIntent().getIntExtra("studentId", -1);
        //////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////// Date And time Picker /////////////////////////
        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        ////////////////////////////////////////////////////////////////////////////
    }

    // Set up the HOCs
    private void setUpViews() {
        spnTaskType = findViewById(R.id.spnTaskType);
        spnCourseCode = findViewById(R.id.spnCourseCode);
        edtTaskDes = findViewById(R.id.edtTaskDes);
        rgPrioritySelector = findViewById(R.id.rgPrioritySelector);
        fabDate = findViewById(R.id.fabDate);
        edtDueDate = findViewById(R.id.edtDueDate);
        fabTime = findViewById(R.id.fabTime);
        edtDueTime = findViewById(R.id.edtDueTime);
        btnAddTask = findViewById(R.id.btnAddTask);
        ArrayList<Course> coursesList = (ArrayList<Course>) getIntent().getSerializableExtra("coursesList");
        populateCourseCodeSpinner(coursesList);
    }

    private void populateCourseCodeSpinner(ArrayList<Course> coursesList) {
        List<String> courseCodes = new ArrayList<>();
        courseCodes.add("TASK FOR");
        for (Course course: coursesList) {
            courseCodes.add(course.getCourseCode());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseCodes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCourseCode.setAdapter(adapter);
    }



    // Method to show Date Picker Dialog
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        edtDueDate.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Method to show Time Picker Dialog
    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        edtDueTime.setText(selectedTime);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private String getSelectedPriority() {
        int radioButtonID = rgPrioritySelector.getCheckedRadioButtonId();
        View radioButton = rgPrioritySelector.findViewById(radioButtonID);
        int idx = rgPrioritySelector.indexOfChild(radioButton);
        String priority = "Low";
        switch (idx) {
            case 0:
                priority = "High";
                break;
            case 1:
                priority = "Medium";
                break;
            case 2:
                priority = "Low";
                break;
        }
        return priority;
    }

    public interface VolleyCallback {
        void onSuccess(int courseId);
    }

    private void getCourseID(String courseCode, final VolleyCallback callback) {
        String url = "http://10.0.2.2:5000/getCourseID/" + courseCode;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                JSONObject course = response.getJSONObject(0);
                                int courseId = course.getInt("course_id");
                                callback.onSuccess(courseId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });

        queue.add(request);
    }

    public void btnAddTaskOnClick(View view) {
        String courseCode = spnCourseCode.getSelectedItem().toString();
        getCourseID(courseCode, new VolleyCallback() {
            @Override
            public void onSuccess(int courseId) {
                // Now that you have the course ID, proceed with adding the task
                String taskType = spnTaskType.getSelectedItem().toString();
                String description = edtTaskDes.getText().toString();
                String priority = getSelectedPriority();
                String dueDate = edtDueDate.getText().toString();
                String dueTime = edtDueTime.getText().toString();
                addTask(studentId, courseId, taskType, description, priority, dueDate, dueTime);
                clearActivityFields();
            }
        });
    }

    private void clearActivityFields() {
        edtTaskDes.setText("");
        edtDueDate.setText("");
        edtDueTime.setText("");
        rgPrioritySelector.clearCheck();
        spnTaskType.setSelection(0);
        spnCourseCode.setSelection(0);
    }

    private void addTask(int student_id, int course_id, String task_type, String description, String priority, String due_date, String due_time){
        String url = "http://10.0.2.2:5000/addTask";
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("student_id", student_id);
            jsonParams.put("course_id", course_id);
            jsonParams.put("task_type", task_type);
            jsonParams.put("description", description);
            jsonParams.put("priority", priority);
            jsonParams.put("due_date", due_date);
            jsonParams.put("due_time", due_time);
            TaskManagerActivity.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = "";
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(AddTaskActivity.this, str,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);
    }
}
