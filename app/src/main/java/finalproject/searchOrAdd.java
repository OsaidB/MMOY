package finalproject;

import static ps.example.mmoy.LoginActivity.STD_ID;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ps.example.mmoy.Course;
import ps.example.mmoy.R;

public class searchOrAdd extends AppCompatActivity {
    Button btnBooks, btnSearchB;
    public static final String STD_ID = "STD_ID";

    static int studentID;


    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_search_or_add);
        btnSearchB = findViewById(R.id.btnSearchB);
        btnBooks = findViewById(R.id.btnBooks);

        setupSharedPrefs();

        //go to exhange activity
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(STD_ID)) {
            int studentId = intent.getIntExtra(STD_ID, -1);
            editor.putInt(STD_ID, studentId);
            editor.apply();
        }

        studentID = prefs.getInt(STD_ID, -1); // -1 is the default value if no id is found



        btnSearchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickedS(view);
            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickedB(view);
            }
        });


    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();  //to Write
    }


    private void onClickedS(View view) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);

        if (studentID != -1) {
            intent.putExtra(STD_ID, studentID);
        } else {
            System.out.println("studentId=-1");
        }

        startActivity(intent);
    }

    ;

    private void onClickedB(View view) {
        Intent intent;
        intent = new Intent(this, choose.class);

        if (studentID != -1) {
            intent.putExtra(STD_ID, studentID);
        } else {
            System.out.println("studentId=-1");
        }

        startActivity(intent);
    }

    ;
}