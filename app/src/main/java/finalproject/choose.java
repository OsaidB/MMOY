package finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ps.example.mmoy.R;

public class choose extends AppCompatActivity {
    Button btnOff, btnRese;
    public static final String STD_ID = "STD_ID";
    static int studentID;

    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_choose);
        btnOff = findViewById(R.id.btnOff);
        btnRese = findViewById(R.id.btnRese);

        setupSharedPrefs();


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(STD_ID)) {
            int studentId = intent.getIntExtra(STD_ID, -1);
            editor.putInt(STD_ID, studentId);
            editor.apply();
        }
        studentID = prefs.getInt(STD_ID, -1); // -1 is the default value if no id is found

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickedOff(view);
            }
        });

        btnRese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickedRese(view);
            }
        });
    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();  //to Write
    }


    private void onClickedOff(View view) {//offered
        Intent intent;
        intent = new Intent(this, viewBooks.class);

        if (studentID != -1) {
            intent.putExtra(STD_ID, studentID);
        } else {
            System.out.println("studentId=-1");
        }

        startActivity(intent);
    }

    ;

    private void onClickedRese(View view) {//reserved
        Intent intent;
        intent = new Intent(this, Reserved.class);

        if (studentID != -1) {
            intent.putExtra(STD_ID, studentID);
        } else {
            System.out.println("studentId=-1");
        }

        startActivity(intent);
    }

    ;
}