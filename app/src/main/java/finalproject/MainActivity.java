package finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ps.example.mmoy.R;

public class MainActivity extends AppCompatActivity {
    EditText txtSearch;
    Button btnSearch;
    static int studentID;
    public static final String STD_ID = "STD_ID";

    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write

    //public static ArrayList<String> books;
    public static ArrayList<Book> bookList = Book.books;
    private RequestQueue queue;

    public void btnSearch_Click(View view) {
        EditText edtSearch = findViewById(R.id.edtSearch);
        String name = edtSearch.getText().toString();
        bookList.clear();
        String url = "http:/10.0.2.2:5000/getbook/" + name;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        bookList.add(new Book(obj.getString("title"), obj.getString("category"), obj.getString("desc"), Double.parseDouble(obj.getString("price")), R.drawable.diavolo));
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                RecyclerView recycler = findViewById(R.id.pizza_recycler);


                Book[] allBooks = new Book[bookList.size()];
                for (int i = 0; i < bookList.size(); i++) {
                    Book currentBook = bookList.get(i);
                    allBooks[i] = new Book(currentBook.getTitle(), currentBook.getCategory(), currentBook.getDesc(),
                            currentBook.getPrice(), currentBook.getImageID());
                }

                // Set up RecyclerView adapter
                recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(allBooks);
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Handle network or server errors
                if (error.networkResponse != null) {
                    // Server responded with a status code
                    int statusCode = error.networkResponse.statusCode;
                    Toast.makeText(MainActivity.this, "Error: Server responded with status code " + statusCode, Toast.LENGTH_LONG).show();
                } else {
                    // Network error (e.g., no internet connection)
                    Toast.makeText(MainActivity.this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }


//                Toast.makeText(MainActivity.this, error.toString(),
//                        Toast.LENGTH_SHORT).show();
//                Log.d("Error_json", error.toString());
            }
        });
        queue.add(request);

    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();  //to Write
    }


//    public void btnOpen_Click(View view) {
//        Intent intent  = new Intent(this, AddBookActivity.class);
//
//        startActivity(intent);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_main2);

        btnSearch = findViewById(R.id.btnSearch);

        setupSharedPrefs();
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(STD_ID)) {
            int studentId = intent.getIntExtra(STD_ID, -1);
            editor.putInt(STD_ID, studentId);
            editor.apply();
        }
        studentID = prefs.getInt(STD_ID, -1); // -1 is the default value if no id is found

        //go to exhange activity
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearch_Click(view);
            }
        });
        // Initialize RequestQueue
        queue = Volley.newRequestQueue(this);

        // Initialize RecyclerView
        RecyclerView recycler = findViewById(R.id.pizza_recycler);


        String url = "http://10.0.2.2:5000/books";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        bookList.add(new Book(obj.getString("title"), obj.getString("category"), obj.getString("desc"), Double.parseDouble(obj.getString("price")), R.drawable.diavolo));
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                RecyclerView recycler = findViewById(R.id.pizza_recycler);


                Book[] allBooks = new Book[bookList.size()];
                for (int i = 0; i < bookList.size(); i++) {
                    Book currentBook = bookList.get(i);
                    allBooks[i] = new Book(currentBook.getTitle(), currentBook.getCategory(), currentBook.getDesc(),
                            currentBook.getPrice(), currentBook.getImageID());
                }

                // Set up RecyclerView adapter
                recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(allBooks);
                recycler.setAdapter(adapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });
        queue.add(request);

//        Book[] allBooks = new Book[bookList.size()];
//        for (int i = 0; i < bookList.size(); i++) {
//            Book currentBook = bookList.get(i);
//            allBooks[i] = new Book(currentBook.getTitle(), currentBook.getCategory(), currentBook.getDesc(),
//                    currentBook.getPrice(), currentBook.getImageID());
//        }

        // Set up RecyclerView adapter
//        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(allBooks);
//        recycler.setAdapter(adapter);
    }


}