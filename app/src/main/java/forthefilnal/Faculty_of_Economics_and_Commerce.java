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

public class Faculty_of_Economics_and_Commerce extends AppCompatActivity {
Button map1;
   TextView mosahab;
    TextView hotdogsanduch;
    TextView burgir2;
    TextView chikencheezfaela;
    TextView potato2;
    TextView shawerma2;
    TextView labana2;
    TextView tonasanduch2;
    TextView greeksaled2;
    TextView chezzeftier2;
    TextView zatar2;
    TextView hotdogftair2;
    TextView sivenup2;
    TextView cocacolatype2;
    TextView guss2;
    TextView xl2;
    TextView icecoffee2;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_of_economics_and_commerce);
        requestQueue = Volley.newRequestQueue(Faculty_of_Economics_and_Commerce.this);
        map1=findViewById(R.id.m);
        mosahab=findViewById(R.id.mosahab);
        hotdogsanduch=findViewById(R.id.hotdogsanuch);
        burgir2=findViewById(R.id.burgir2);
        chikencheezfaela=findViewById(R.id.mosahab);
        potato2=findViewById(R.id.potato2);
        shawerma2=findViewById(R.id.shawerma2);
        labana2=findViewById(R.id.labana2);
        tonasanduch2=findViewById(R.id.tonasanduch2);
        greeksaled2=findViewById(R.id.greeksaled2);
        chezzeftier2=findViewById(R.id.chezzeftier2);
        zatar2=findViewById(R.id.zatar2);
        hotdogftair2=findViewById(R.id.hotdogftair2);
        sivenup2=findViewById(R.id.sivenup2);
        cocacolatype2=findViewById(R.id.cocacolatype2);
        guss2=findViewById(R.id.guss2);
        xl2=findViewById(R.id.xl2);
        icecoffee2=findViewById(R.id.icecoffee2);


        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "https://jsonplaceholder.typicode.com/photos";
        System.out.println("testererr");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < 30; i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add(obj.getString("title"));
                        Log.d("yazahsjdudhd",obj.toString());
                    }catch(JSONException exception){
                        Log.d("volley_error", exception.toString());
                    }
                }
                mosahab.setText(todos.get(0));
                hotdogsanduch.setText(todos.get(1));
                burgir2.setText(todos.get(2));
                chikencheezfaela.setText(todos.get(3));
                potato2.setText(todos.get(4));
                shawerma2.setText(todos.get(5));
                labana2.setText(todos.get(6));
                tonasanduch2.setText(todos.get(7));
                greeksaled2.setText(todos.get(8));
                chezzeftier2.setText(todos.get(9));
                zatar2.setText(todos.get(10));
                hotdogftair2.setText(todos.get(11));
                sivenup2.setText(todos.get(12));
                cocacolatype2.setText(todos.get(13));
                guss2.setText(todos.get(14));
                xl2.setText(todos.get(15));
                icecoffee2.setText(todos.get(16));
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


    public void refrish(View view) {
//        String url = "https://jsonplaceholder.typicode.com/photos";
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
//                        todos.add(obj.getString("title"));
//                        Log.d("yazahsjdudhd",obj.toString());
//                    }catch(JSONException exception){
//                        Log.d("volley_error", exception.toString());
//                    }
//                }
//                mosahab.setText(todos.get(0));
//                hotdogsanduch.setText(todos.get(1));
//                burgir2.setText(todos.get(2));
//                chikencheezfaela.setText(todos.get(3));
//                potato2.setText(todos.get(4));
//                shawerma2.setText(todos.get(5));
//                labana2.setText(todos.get(6));
//                tonasanduch2.setText(todos.get(7));
//                greeksaled2.setText(todos.get(8));
//                chezzeftier2.setText(todos.get(9));
//                zatar2.setText(todos.get(10));
//                hotdogftair2.setText(todos.get(11));
//                sivenup2.setText(todos.get(12));
//                cocacolatype2.setText(todos.get(13));
//                guss2.setText(todos.get(14));
//                xl2.setText(todos.get(15));
//                icecoffee2.setText(todos.get(16));
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
}