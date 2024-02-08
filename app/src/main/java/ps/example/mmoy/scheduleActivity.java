package ps.example.mmoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class scheduleActivity extends AppCompatActivity {


    private ArrayList<Course> coursesList;

    Button btnAddCourse;

    //////////////////////////////
    private RecyclerView recyclerView;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        coursesList = new ArrayList<Course>();

        Intent recievedIntent = getIntent();
        if (recievedIntent != null && recievedIntent.hasExtra("list")) {
//            coursesList = (ArrayList<Course>) getIntent().getSerializableExtra("list");

            coursesList = (ArrayList<Course>) recievedIntent.getSerializableExtra("list");
        }
        setupViews();

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scheduleActivity.this, scheduleForm.class);
                intent.putExtra("title", "Insert A Course");
                intent.putExtra("list", coursesList);
                startActivity(intent);
            }
        });


    }

    private void setupViews() { //just making the hooks
//        requestQueue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerViewCourses);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CourseAdapter(coursesList);
        recyclerView.setAdapter(adapter);


        btnAddCourse = findViewById(R.id.btnAddCourse);   //1
//        buttonFeature2 = findViewById(R.id.button_feature_2);

    }
}