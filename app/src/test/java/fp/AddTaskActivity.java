package fp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtCourseCode, edtTaskDes, edtDueDate, edtDueTime;
    private Spinner spnTaskType;
    private RadioGroup rgPrioritySelector;
    private FloatingActionButton fabDate, fabTime;
    private Button btnAddTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setUpViews();

        edtDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        edtDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showTimePickerDialog(); }
        });
    }

    private void setUpViews() {
        edtCourseCode = findViewById(R.id.edtCourseCode);
        spnTaskType = findViewById(R.id.spnTaskType);
        edtTaskDes = findViewById(R.id.edtTaskDes);
        rgPrioritySelector = findViewById(R.id.rgPrioritySelector);
        fabDate = findViewById(R.id.fabDate);
        edtDueDate = findViewById(R.id.edtDueDate);
        fabTime = findViewById(R.id.fabTime);
        edtDueTime = findViewById(R.id.edtDueTime);
        btnAddTask = findViewById(R.id.btnAddTask);
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


    public void btnAddTaskOnClick(){

    }

    private void addTask(int student_id, int course_id, String task_type, String description){

    }
}
