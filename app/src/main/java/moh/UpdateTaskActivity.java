package moh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import ps.example.mmoy.R;

public class UpdateTaskActivity extends AppCompatActivity {

    private RequestQueue queue;
    private EditText edtTypeTaskUpdate, edtTaskDesUpdate, edtDueDateUpdate, edtDueTimeUpdate;
    private RadioGroup rgPrioritySelectorUpdate;
    private FloatingActionButton fabDateUpdate,fabTimeUpdate;
    private Button btnUpdateTask;
    private int student_Id;
    private int task_Id;
    private int course_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_activity_update_task);
        queue = Volley.newRequestQueue(this);
        setUpViews();
        fillDataFromIntent();

        fabDateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        fabTimeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskType = edtTypeTaskUpdate.getText().toString();
                String description = edtTaskDesUpdate.getText().toString();
                String priority = getSelectedPriority();
                String dueDate = edtDueDateUpdate.getText().toString();
                String dueTime = edtDueTimeUpdate.getText().toString();
                updateTask(taskType, description, priority, dueDate, dueTime);
                finish();
            }
        });

    }

    private void setUpViews(){
        edtTypeTaskUpdate = findViewById(R.id.edtTypeTaskUpdate);
        edtTaskDesUpdate = findViewById(R.id.edtTaskDesUpdate);
        edtDueDateUpdate = findViewById(R.id.edtDueDateUpdate);
        edtDueTimeUpdate = findViewById(R.id.edtDueTimeUpdate);
        rgPrioritySelectorUpdate = findViewById(R.id.rgPrioritySelectorUpdate);
        fabDateUpdate = findViewById(R.id.fabDateUpdate);
        fabTimeUpdate = findViewById(R.id.fabTimeUpdate);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
    }

    private void fillDataFromIntent() {
        Intent intent = getIntent();
        String taskType = intent.getStringExtra("taskType");
        String description = intent.getStringExtra("description");
        String priority = intent.getStringExtra("priority");
        String dueDate = intent.getStringExtra("dueDate");
        String dueTime = intent.getStringExtra("dueTime");
        student_Id = intent.getIntExtra("studentId", -1);
        task_Id = intent.getIntExtra("taskId", -1);
        course_Id = intent.getIntExtra("courseId", -1);
        edtTypeTaskUpdate.setText(taskType);
        edtTaskDesUpdate.setText(description);
        edtDueDateUpdate.setText(dueDate);
        edtDueTimeUpdate.setText(dueTime);
        switch (priority) {
            case "High":
                rgPrioritySelectorUpdate.check(R.id.rbHigh);
                break;
            case "Medium":
                rgPrioritySelectorUpdate.check(R.id.rbMedium);
                break;
            case "Low":
                rgPrioritySelectorUpdate.check(R.id.rbLow);
                break;
        }
    }

    private String getSelectedPriority() {
        int radioButtonID = rgPrioritySelectorUpdate.getCheckedRadioButtonId();
        View radioButton = rgPrioritySelectorUpdate.findViewById(radioButtonID);
        int idx = rgPrioritySelectorUpdate.indexOfChild(radioButton);
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
                        edtDueDateUpdate.setText(selectedDate);
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
                        edtDueTimeUpdate.setText(selectedTime);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void updateTask(String task_type, String description, String priority, String due_date, String due_time) {
        String url = "http://10.0.2.2:5000/updateTask/" + task_Id;

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("student_id", student_Id);
            jsonParams.put("course_id", course_Id);
            jsonParams.put("task_type", task_type);
            jsonParams.put("description", description);
            jsonParams.put("priority", priority);
            jsonParams.put("due_date", due_date);
            jsonParams.put("due_time", due_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Correctly use StringRequest for JSON body
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> Toast.makeText(UpdateTaskActivity.this, "Task updated successfully!", Toast.LENGTH_SHORT).show(),
                error -> {
                    // Handle error
                    String message = "Update failed: " + error.toString();
                    Toast.makeText(UpdateTaskActivity.this, message, Toast.LENGTH_LONG).show();
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonParams.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        //RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}