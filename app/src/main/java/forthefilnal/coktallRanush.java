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

public class coktallRanush extends AppCompatActivity {
Button mape9;
    TextView milkshake10;
    TextView melkshakorio10;
    TextView frotgusess10;
    TextView melkshacklotes10;
    TextView pnanacoctal10;
    TextView orengguse10;
    TextView avocado10;
    TextView lemonade10;
    TextView appleguse10;
    TextView caretgusee10;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coktallranush);
        mape9=findViewById(R.id.ma10);
        requestQueue = Volley.newRequestQueue(coktallRanush.this);

        milkshake10=findViewById(R.id.milkshake10);
        melkshakorio10=findViewById(R.id.melkshakorio10);
        frotgusess10=findViewById(R.id.frotgusess10);
        melkshacklotes10=findViewById(R.id.melkshacklotes10);
        pnanacoctal10=findViewById(R.id.pnanacoctal10);
        orengguse10=findViewById(R.id.orengguse10);
        avocado10=findViewById(R.id.avocado10);
        lemonade10=findViewById(R.id.lemonade10);
        appleguse10=findViewById(R.id.appleguse10);
        caretgusee10=findViewById(R.id.caretgusee10);
        mape9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/coktallRanush";
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
                        Log.d("yazahsjdudhd",obj.toString());
                    }catch(JSONException exception){
                        Log.d("volley_error", exception.toString());
                    }
                }
                milkshake10.setText(todos.get(0));
                melkshakorio10.setText(todos.get(1));
                frotgusess10.setText(todos.get(2));
                melkshacklotes10.setText(todos.get(3));
                pnanacoctal10.setText(todos.get(4));
                orengguse10.setText(todos.get(5));
                avocado10.setText(todos.get(6));
                lemonade10.setText(todos.get(7));
                appleguse10.setText(todos.get(8));
                caretgusee10.setText(todos.get(9));

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
//        String url = "http://192.168.7.208:5000/rest/coktallRanush";
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
//                milkshake10.setText(todos.get(0));
//                melkshakorio10.setText(todos.get(1));
//                frotgusess10.setText(todos.get(2));
//                melkshacklotes10.setText(todos.get(3));
//                pnanacoctal10.setText(todos.get(4));
//                orengguse10.setText(todos.get(5));
//                avocado10.setText(todos.get(6));
//                lemonade10.setText(todos.get(7));
//                appleguse10.setText(todos.get(8));
//                caretgusee10.setText(todos.get(9));
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
    private void getDirctions(){
        try{
            Uri uri= Uri.parse("https://www.google.com/maps/dir/");
            Intent in=new Intent(Intent.ACTION_VIEW,uri);
            in.setPackage("com.google.android.apps.maps");
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }catch (ActivityNotFoundException exception){
            Uri uri= Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent in=new Intent(Intent.ACTION_VIEW,uri);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }
    }
}