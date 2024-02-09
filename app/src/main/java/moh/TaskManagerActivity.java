package moh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ps.example.mmoy.Course;
import ps.example.mmoy.R;

public class TaskManagerActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTask;
    private ArrayList<Course> coursesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moh_task_manager);

        Intent intent = getIntent();

        coursesList = new ArrayList<Course>();

        Intent recievedIntent = getIntent();
        if (recievedIntent != null && recievedIntent.hasExtra("list")) {
//            coursesList = (ArrayList<Course>) getIntent().getSerializableExtra("list");

            coursesList = (ArrayList<Course>) recievedIntent.getSerializableExtra("list");
        }

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskManagerActivity.this, AddTaskActivity.class);
                startActivity(intent1);
            }
        });


    }
}