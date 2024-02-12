package finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ps.example.mmoy.R;

public class bookSee extends AppCompatActivity {
    public static String title;
    public static String category;
    public static String description;
    public static double price;
    public static int buyerId = viewBooks.studentID;
    public static ArrayList<Book> bookList = Book.books;
    private RequestQueue queue;
    public static int id;

    TextView lblTitle, lblCat, lblDes, lblPrice;
    Button btnRes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_book_see);
        lblTitle = findViewById(R.id.lblTitle);
        lblCat = findViewById(R.id.lblCat);
        lblDes = findViewById(R.id.lblDes);
        lblPrice = findViewById(R.id.lblPrice);
        btnRes = findViewById(R.id.btnRes);

        lblTitle.setText(title);
        lblCat.setText(category);
        lblDes.setText(description);
        lblPrice.setText(price + "");
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btnResOnClick(view);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void btnResOnClick(View view) throws UnsupportedEncodingException {
        bookList.clear();
        String url = "http://10.0.2.2:5000/getID/" + URLEncoder.encode(title, "UTF-8");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject obj = response.getJSONObject(0); // Get the first object
                        int id = obj.getInt("id"); // Extract the ID
                        Log.d("Fetched ID", String.valueOf(id)); // Log or use the ID as needed

                        // Use the ID as needed here, for example:
                        // fetchBookDetailsById(id);
                    }
                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 = "http://10.0.2.2:5000/update"; // Adjust if necessary

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url2,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        Toast.makeText(bookSee.this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(bookSee.this, "Error updating book: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                params.put("buyerId", String.valueOf(buyerId));

                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(putRequest);
    }

}