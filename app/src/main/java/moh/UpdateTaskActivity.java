package moh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import ps.example.mmoy.R;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText edtTypeTaskUpdate, edtTaskDesUpdate, edtDueDateUpdate, edtDueTimeUpdate;
    private RadioGroup rgPrioritySelectorUpdate;
    private FloatingActionButton fabDateUpdate,fabTimeUpdate;
    private Button btnUpdateTask;
    private int studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_activity_update_task);

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
        studentId = intent.getIntExtra("studentId", -1);

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


}