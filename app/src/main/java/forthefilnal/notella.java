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

public class notella extends AppCompatActivity {
Button mape10;
    TextView wafell11;
    TextView pankaknutalla11;
    TextView nutellapizza11;
    TextView moosnutalla11;
    TextView oriocack11;
    TextView lotescack11;
    TextView kreep11;
    TextView cheezcackntalla11;
    TextView sofla11;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notella);
        mape10=findViewById(R.id.ma11);
        requestQueue = Volley.newRequestQueue(notella.this);

        wafell11=findViewById(R.id.wafell11);
        pankaknutalla11=findViewById(R.id.pankaknutalla11);
        nutellapizza11=findViewById(R.id.nutellapizza11);
        moosnutalla11=findViewById(R.id.moosnutalla11);
        oriocack11=findViewById(R.id.oriocack11);
        lotescack11=findViewById(R.id.lotescack11);
        kreep11=findViewById(R.id.kreep11);
        cheezcackntalla11=findViewById(R.id.cheezcackntalla11);
        sofla11=findViewById(R.id.sofla11);
        mape10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/notella";
        System.out.println("testererr");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < 10; i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add(obj.getString("price"));
                        Log.d("yazahsjdudhd",obj.toString());
                    }catch(JSONException exception){
                        Log.d("volley_error", exception.toString());
                    }
                }
                wafell11.setText(todos.get(0));
                pankaknutalla11.setText(todos.get(1));
                nutellapizza11.setText(todos.get(2));
                moosnutalla11.setText(todos.get(3));
                oriocack11.setText(todos.get(4));
                lotescack11.setText(todos.get(5));
                kreep11.setText(todos.get(6));
                cheezcackntalla11.setText(todos.get(7));
                sofla11.setText(todos.get(8));

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
//        String url = "http://192.168.7.208:5000/rest/notella";
//        System.out.println("testererr");
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                ArrayList<String> todos = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//
//                    try {
//                        JSONObject obj = response.getJSONObject(i);
//                        todos.add(obj.getString("price"));
//                        Log.d("yazahsjdudhd",obj.toString());
//                    }catch(JSONException exception){
//                        Log.d("volley_error", exception.toString());
//                    }
//                }
//                wafell11.setText(todos.get(0));
//             pankaknutalla11.setText(todos.get(1));
//                nutellapizza11.setText(todos.get(2));
//                moosnutalla11.setText(todos.get(3));
//                oriocack11.setText(todos.get(4));
//                lotescack11.setText(todos.get(5));
//                kreep11.setText(todos.get(6));
//                cheezcackntalla11.setText(todos.get(7));
//                sofla11.setText(todos.get(8));
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