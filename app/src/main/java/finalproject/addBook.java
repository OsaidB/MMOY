package finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    EditText edtId, edtTitle, edtDes, edtCat, edtPrice;
    Button addBtn;

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
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd_Click(view);
            }
        });
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

        addBook2(bookTitle, bookCat, bookDes, bookPrice, bookSenderId);

        Intent intent;
        intent = new Intent(this, viewBooks.class);
        startActivity(intent);
    }


}