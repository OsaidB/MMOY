package finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ps.example.mmoy.R;

public class addBook extends AppCompatActivity {
    EditText edtTitle, edtDes, edtCat, edtPrice;
    Button addBtn;
    private RequestQueue queue;
    public static final String STD_ID = "STD_ID";
    private int studentID;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_add_book);
        edtTitle = findViewById(R.id.edtTitle);
        edtDes = findViewById(R.id.edtDes);
        edtCat = findViewById(R.id.edtCat);
        edtPrice = findViewById(R.id.edtPrice);
        addBtn = findViewById(R.id.addBtn);

        setupSharedPrefs();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(STD_ID)) {
            studentID = intent.getIntExtra(STD_ID, -1);
            editor.putInt(STD_ID, studentID).apply();
        } else {
            studentID = prefs.getInt(STD_ID, -1);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd_Click(view);
            }
        });
    }

    private void addBookToServer(String title, String category, String desc, double price) {
        String url = "http://10.0.2.2:5000/createBook";
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", title);
            jsonParams.put("category", category);
            jsonParams.put("desc", desc);
            jsonParams.put("price", price);
            jsonParams.put("senderID", studentID);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams, response -> {
                try {
                    String resultMessage = response.getString("result");
                    Toast.makeText(getApplicationContext(), resultMessage, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Response parsing error.", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                Toast.makeText(addBook.this, "Request error: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            });

            Volley.newRequestQueue(this).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "JSON creation error.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAdd_Click(View view) {
        String bookTitle = edtTitle.getText().toString();
        String bookCat = edtCat.getText().toString();
        String bookDes = edtDes.getText().toString();
        double bookPrice = Double.parseDouble(edtPrice.getText().toString());

        addBookToServer(bookTitle, bookCat, bookDes, bookPrice);

        Intent intent = new Intent(this, viewBooks.class);
        startActivity(intent);
    }

    private void setupSharedPrefs() {
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();
    }
}
