package finalproject;

//import static ps.example.mmoy.MainActivity.prefs;

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
import ps.example.mmoy.scheduleForm;

public class addBook extends AppCompatActivity {
    EditText edtId, edtTitle, edtDes, edtCat, edtPrice;
    Button addBtn;


    public static final String STD_ID = "STD_ID";
    static int studentID;

    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_add_book);
        edtId = findViewById(R.id.edtId);
        edtTitle = findViewById(R.id.edtTitle);
        edtDes = findViewById(R.id.edtDes);
        edtCat = findViewById(R.id.edtCat);
        edtPrice = findViewById(R.id.edtPrice);
        addBtn = findViewById(R.id.addBtn);

        setupSharedPrefs();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(STD_ID)) {
            int studentId = intent.getIntExtra(STD_ID, -1);
            editor.putInt(STD_ID, studentId);
            editor.apply();
        }

        studentID = prefs.getInt(STD_ID, -1); // -1 is the default value if no id is found






        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd_Click(view);
            }
        });
    }
    private void addBook3(String title, String category, String desc, double price, int senderID) {

//        int studentId = prefs.getInt("studentId", -1); // -1 is the default value if no id is found

        String url = "http://10.0.2.2:5000/create";

        RequestQueue queue = Volley.newRequestQueue(addBook.this);


        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("title", title);
            jsonParams.put("category", category);
            jsonParams.put("desc", desc);
            jsonParams.put("price", price);
            jsonParams.put("senderID", senderID);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParams,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String resultMessage = "";
                            try {
                                resultMessage = response.getString("result");


                                String finalResultMessage = resultMessage;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the result message to the user using a Toast
                                        Toast.makeText(getApplicationContext(), finalResultMessage, Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            Toast.makeText(addBook.this, resultMessage, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(addBook.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating JSON params.", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }

    private void addBook2(String title, String category, String desc, double price, int senderID) {
        String url = "http://10.0.2.2:5000/create";

        RequestQueue queue = Volley.newRequestQueue(addBook.this);


        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", title);
            jsonParams.put("category", category);
            jsonParams.put("desc", desc);
            jsonParams.put("price", price);
            jsonParams.put("senderID", senderID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create a JsonObjectRequest with POST method
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = "";
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(addBook.this, str,
                                Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        // below line is to make
        // a json object request.
        queue.add(request);


    }

    public void btnAdd_Click(View view) {
        String bookTitle = edtTitle.getText().toString();
        String bookCat = edtCat.getText().toString();
        String bookDes = edtDes.getText().toString();

        String bookP = edtPrice.getText().toString();
        double bookPrice = Double.parseDouble(bookP);

        String bookId = edtId.getText().toString();
        int bookSenderId = Integer.parseInt(bookId);

//        addBook2(bookTitle, bookCat, bookDes, bookPrice, studentID);
        addBook3(bookTitle, bookCat, bookDes, bookPrice, studentID);

        Intent intent;
        intent = new Intent(this, viewBooks.class);
        startActivity(intent);
    }


    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor = prefs.edit();  //to Write
    }
}