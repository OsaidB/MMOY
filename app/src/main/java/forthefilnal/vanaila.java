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

public class vanaila extends AppCompatActivity {
Button map3;
    private RequestQueue requestQueue;
    TextView cookies4;
    TextView redvillvt4;
    TextView cupcack4;
    TextView croasonchoclet4;
    TextView emptycroson4;
    TextView bauntycack4;
    TextView chezecack4;
    TextView snecarzcack4;
    TextView amrcano4;
    TextView espreso4;
    TextView icecofee4;
    TextView capacheno4;
    TextView hotnutala4;
    TextView shaylata4;
    TextView milkshake4;
    TextView hotfilft4;
   // TextView icecoffee2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanaila);
        requestQueue = Volley.newRequestQueue(vanaila.this);

        map3=findViewById(R.id.ma);
        cookies4=findViewById(R.id.cookies4);
        redvillvt4=findViewById(R.id.redvillvt4);
        cupcack4=findViewById(R.id.cupcack4);
        croasonchoclet4=findViewById(R.id.croasonchoclet4);
        emptycroson4=findViewById(R.id.emptycroson4);
        bauntycack4=findViewById(R.id.bauntycack4);
        chezecack4=findViewById(R.id.chezecack4);
        snecarzcack4=findViewById(R.id.snecarzcack4);
        amrcano4=findViewById(R.id.amrcano4);
        espreso4=findViewById(R.id.espreso4);
        icecofee4=findViewById(R.id.icecofee4);
        capacheno4=findViewById(R.id.capacheno4);
        hotnutala4=findViewById(R.id.hotnutala4);
        shaylata4=findViewById(R.id.shaylata4);
        milkshake4=findViewById(R.id.milkshake4);
        hotfilft4=findViewById(R.id.hotfilft4);
        map3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirctions();
            }
        });
        String url = "http://192.168.7.208:5000/rest/vanaila";
        System.out.println("testererr");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < 16; i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add(obj.getString("price"));
                        Log.d("yazahsjdudhd",obj.toString());
                    }catch(JSONException exception){
                        Log.d("volley_error", exception.toString());
                    }
                }
                cookies4.setText(todos.get(0));
                redvillvt4.setText(todos.get(1));
                cupcack4.setText(todos.get(2));
                croasonchoclet4.setText(todos.get(3));
                emptycroson4.setText(todos.get(4));
                bauntycack4.setText(todos.get(5));
                chezecack4.setText(todos.get(6));
                snecarzcack4.setText(todos.get(15));
                amrcano4.setText(todos.get(8));
                espreso4.setText(todos.get(9));
                icecofee4.setText(todos.get(10));
                capacheno4.setText(todos.get(11));
                hotnutala4.setText(todos.get(12));
                shaylata4.setText(todos.get(13));
                milkshake4.setText(todos.get(14));
                hotfilft4.setText(todos.get(7));
                // icecoffee2.setText(todos.get(16));
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
//        String url = "http://192.168.7.208:5000/rest/vanaila";
//        System.out.println("testererr");
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                ArrayList<String> todos = new ArrayList<>();
//                for (int i = 0; i < 16; i++) {
//
//                    try {
//                        JSONObject obj = response.getJSONObject(i);
//                        todos.add(obj.getString("price"));
//                        Log.d("yazahsjdudhd",obj.toString());
//                    }catch(JSONException exception){
//                        Log.d("volley_error", exception.toString());
//                    }
//                }
//                cookies4.setText(todos.get(0));
//                redvillvt4.setText(todos.get(1));
//                cupcack4.setText(todos.get(2));
//                croasonchoclet4.setText(todos.get(3));
//                emptycroson4.setText(todos.get(4));
//                bauntycack4.setText(todos.get(5));
//                chezecack4.setText(todos.get(6));
//                snecarzcack4.setText(todos.get(15));
//                amrcano4.setText(todos.get(8));
//                espreso4.setText(todos.get(9));
//                icecofee4.setText(todos.get(10));
//                capacheno4.setText(todos.get(11));
//                hotnutala4.setText(todos.get(12));
//                shaylata4.setText(todos.get(13));
//                milkshake4.setText(todos.get(14));
//                hotfilft4.setText(todos.get(7));
//               // icecoffee2.setText(todos.get(16));
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