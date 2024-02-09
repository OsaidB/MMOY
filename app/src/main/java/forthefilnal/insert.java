package forthefilnal;

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

public class insert extends AppCompatActivity {
    private RequestQueue requestQueue;
    EditText e1;
    EditText e2;
    EditText e3;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaz_insert);
        requestQueue = Volley.newRequestQueue(insert.this);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        b = findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookTitle = e1.getText().toString();
                String bookCat = e2.getText().toString();
                String bookPages = e3.getText().toString();

                try {
                    addBook(bookTitle, bookCat, bookPages);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void addBook(String restname, String dish, String price) throws JSONException {

        String url = "http://192.168.7.208:5000/create";


        JSONObject jsonParams = new JSONObject();

        jsonParams.put("restname", restname);
        jsonParams.put("objectname", dish);
        jsonParams.put("price", price);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(insert.this, str, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insert.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


//    private void setupViews() { //just making the hooks
//
////        edtEnterCat = findViewById(R.id.edtEnterCat);
////        edtTitle = findViewById(R.id.edtTitle);
////        edtPages = findViewById(R.id.edtPages);
////
////        btnAddBook= findViewById(R.id.btnAddBook);
////        movies.setHasFixedSize(true);
////        movies.setLayoutManager(new LinearLayoutManager(this));
////        movieList=new ArrayList<>();
//
//
//
////        fetchMovies();
//    }

}