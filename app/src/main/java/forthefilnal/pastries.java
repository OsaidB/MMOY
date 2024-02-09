package forthefilnal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class pastries extends AppCompatActivity {
    Button mape12;
    TextView chezzeftair13;
    TextView zatar13;
    TextView mosahab13;
    TextView pizza13;
    TextView hotdagftair13;
    TextView sfihaturkia13;
    TextView spanikh13;
    TextView potato13;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaz_pastries);
        chezzeftair13 = findViewById(R.id.chezzeftair13);
        zatar13 = findViewById(R.id.zatar13);
        mosahab13 = findViewById(R.id.mosahab13);
        pizza13 = findViewById(R.id.pizza13);
        hotdagftair13 = findViewById(R.id.hotdagftair13);
        sfihaturkia13 = findViewById(R.id.sfihaturkia13);
        spanikh13 = findViewById(R.id.spanikh13);
        potato13 = findViewById(R.id.potato13);
        mape12 = findViewById(R.id.ma13);
        requestQueue = Volley.newRequestQueue(pastries.this);
        mape12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/pastries";
        System.out.println("testererr");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < 12; i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add(obj.getString("price"));
                        Log.d("yazahsjdudhd", obj.toString());
                    } catch (JSONException exception) {
                        Log.d("volley_error", exception.toString());
                    }
                }
                chezzeftair13.setText(todos.get(0));
                zatar13.setText(todos.get(1));
                mosahab13.setText(todos.get(2));
                pizza13.setText(todos.get(3));
                hotdagftair13.setText(todos.get(4));
                sfihaturkia13.setText(todos.get(5));
                spanikh13.setText(todos.get(6));
                potato13.setText(todos.get(7));
//                System.out.println("ter");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        requestQueue.add(request);

    }

//    public void refrish(View view) {
//        String url = "http://192.168.7.208:5000/rest/pastries";
//        System.out.println("testererr");
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                ArrayList<String> todos = new ArrayList<>();
//                for (int i = 0; i <12; i++) {
//
//                    try {
//                        JSONObject obj = response.getJSONObject(i);
//                        todos.add(obj.getString("price"));
//                        Log.d("yazahsjdudhd",obj.toString());
//                    }catch(JSONException exception){
//                        Log.d("volley_error", exception.toString());
//                    }
//                }
//                chezzeftair13.setText(todos.get(0));
//                zatar13.setText(todos.get(1));
//                mosahab13.setText(todos.get(2));
//                pizza13.setText(todos.get(3));
//                hotdagftair13.setText(todos.get(4));
//               sfihaturkia13.setText(todos.get(5));
//                spanikh13.setText(todos.get(6));
//               potato13.setText(todos.get(7));
////                System.out.println("ter");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("volley_error", error.toString());
//            }
//        });
//
//        requestQueue.add(request);
//    }


    private void getDirctions() {
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/");
            Intent in = new Intent(Intent.ACTION_VIEW, uri);
            in.setPackage("com.google.android.apps.maps");
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent in = new Intent(Intent.ACTION_VIEW, uri);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }
    }


}