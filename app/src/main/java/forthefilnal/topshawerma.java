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

public class topshawerma extends AppCompatActivity {
    Button mape8;
    TextView shwermalafe9;
    TextView shwermaarabi9;
    TextView sahenshawerma9;
    TextView bashka9;
    TextView potato9;
    TextView cocacola9;
    TextView xl9;
    TextView sivenup9;
    //    TextView greeksaled2;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaz_topshawerma);
        requestQueue = Volley.newRequestQueue(topshawerma.this);

        mape8 = findViewById(R.id.ma9);
        shwermalafe9 = findViewById(R.id.shwermalafe9);
        shwermaarabi9 = findViewById(R.id.shwermaarabi9);
        sahenshawerma9 = findViewById(R.id.sahenshawerma9);
        bashka9 = findViewById(R.id.bashka9);
        potato9 = findViewById(R.id.potato9);
        cocacola9 = findViewById(R.id.cocacola9);
        xl9 = findViewById(R.id.xl9);
        sivenup9 = findViewById(R.id.sivenup9);
        mape8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/topshawerma";
        System.out.println("testererr");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < 30; i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add(obj.getString("price"));
                        Log.d("yazahsjdudhd", obj.toString());
                    } catch (JSONException exception) {
                        Log.d("volley_error", exception.toString());
                    }
                }
                shwermalafe9.setText(todos.get(0));
                shwermaarabi9.setText(todos.get(1));
                sahenshawerma9.setText(todos.get(2));
                bashka9.setText(todos.get(3));
                potato9.setText(todos.get(4));
                cocacola9.setText(todos.get(5));
                xl9.setText(todos.get(6));
                sivenup9.setText(todos.get(7));

                System.out.println("ter");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        requestQueue.add(request);
    }

    public void refrish(View view) {
//        String url = "http://192.168.7.208:5000/rest/topshawerma";
//        System.out.println("testererr");
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                ArrayList<String> todos = new ArrayList<>();
//                for (int i = 0; i < 30; i++) {
//
//                    try {
//                        JSONObject obj = response.getJSONObject(i);
//                        todos.add(obj.getString("price"));
//                        Log.d("yazahsjdudhd",obj.toString());
//                    }catch(JSONException exception){
//                        Log.d("volley_error", exception.toString());
//                    }
//                }
//                shwermalafe9.setText(todos.get(0));
//                shwermaarabi9.setText(todos.get(1));
//                sahenshawerma9.setText(todos.get(2));
//                bashka9.setText(todos.get(3));
//                potato9.setText(todos.get(4));
//                cocacola9.setText(todos.get(5));
//                xl9.setText(todos.get(6));
//                sivenup9.setText(todos.get(7));
//
//                System.out.println("ter");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("volley_error", error.toString());
//            }
//        });
//
//        requestQueue.add(request);
    }


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