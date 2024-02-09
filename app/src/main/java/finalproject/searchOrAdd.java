package finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ps.example.mmoy.R;

public class searchOrAdd extends AppCompatActivity {
    Button btnBooks, btnSearchB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_search_or_add);
        btnSearchB = findViewById(R.id.btnSearchB);
        btnBooks = findViewById(R.id.btnBooks);
        //go to exhange activity
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

    private void onClickedS(View view) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    ;

    private void onClickedB(View view) {
        Intent intent;
        intent = new Intent(this, choose.class);
        startActivity(intent);
    }

    ;
}