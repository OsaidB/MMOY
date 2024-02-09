package forthefilnal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import ps.example.mmoy.R;

public class list_rest extends AppCompatActivity {
    ListView list;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaz_list_rest);
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_rest.this, insert.class);
                startActivity(intent);
            }
        });
        AdapterView.OnItemClickListener item = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(list_rest.this, Faculty_of_Economics_and_Commerce.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(list_rest.this, Pharmacy_Cafeteria.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(list_rest.this, vanaila.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(list_rest.this, science_cafeteria.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(list_rest.this, Literature_cafeteria.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(list_rest.this, Maramia.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(list_rest.this, Mahta.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(list_rest.this, topshawerma.class);
                    startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(list_rest.this, coktallRanush.class);
                    startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(list_rest.this, notella.class);
                    startActivity(intent);
                } else if (position == 10) {
                    Intent intent = new Intent(list_rest.this, Tost.class);
                    startActivity(intent);
                } else if (position == 11) {
                    Intent intent = new Intent(list_rest.this, pastries.class);
                    startActivity(intent);
                }
            }
        };
        list = findViewById(R.id.list);
        list.setOnItemClickListener(item);
    }
}