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

public class Maramia extends AppCompatActivity {
    Button mape6;

    private RequestQueue requestQueue;
    TextView tee7;
    TextView amrcano7;
    TextView capacheno7;
    TextView zhurat7;
    TextView icecofee7;
    TextView cocacola7;
    TextView xl7;
    TextView bvaria7;

    //    TextView bvaria7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maramia);
        requestQueue = Volley.newRequestQueue(Maramia.this);
        mape6 = findViewById(R.id.ma7);
        tee7 = findViewById(R.id.tee7);
        amrcano7 = findViewById(R.id.amrcano7);
        capacheno7 = findViewById(R.id.capacheno7);
        zhurat7 = findViewById(R.id.zhurat7);
        icecofee7 = findViewById(R.id.icecofee7);
        cocacola7 = findViewById(R.id.cocacola7);
        xl7 = findViewById(R.id.xl7);
        bvaria7 = findViewById(R.id.bvaria7);

        mape6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/Maramia";
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
                tee7.setText(todos.get(0));
                amrcano7.setText(todos.get(1));
                capacheno7.setText(todos.get(2));
                zhurat7.setText(todos.get(3));
                icecofee7.setText(todos.get(4));
                cocacola7.setText(todos.get(5));
                xl7.setText(todos.get(6));
                bvaria7.setText(todos.get(7));

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
//        String url = "http://192.168.7.208:5000/rest/Maramia";
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
//                tee7.setText(todos.get(0));
//                amrcano7.setText(todos.get(1));
//                capacheno7.setText(todos.get(2));
//                zhurat7.setText(todos.get(3));
//                icecofee7.setText(todos.get(4));
//                cocacola7.setText(todos.get(5));
//                xl7.setText(todos.get(6));
//                bvaria7.setText(todos.get(7));
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