package finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class choose extends AppCompatActivity {
    Button btnOff,btnRese;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        btnOff = findViewById(R.id.btnOff);
        btnRese = findViewById(R.id.btnRese);
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

    private void onClickedOff(View view){
        Intent intent;
        intent = new Intent(this,viewBooks.class);
        startActivity(intent);
    };
    private void onClickedRese(View view){
        Intent intent;
        intent = new Intent(this,Reserved.class);
        startActivity(intent);
    };
}