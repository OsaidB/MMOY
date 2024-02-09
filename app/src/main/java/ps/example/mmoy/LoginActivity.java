package ps.example.mmoy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String USERS_LIST = "USERS LIST";

    private EditText edtLogEmail;
    private EditText edtLogPassword;
    private CheckBox rmmbrme;

    private RequestQueue requestQueue;

    private Button btnToRegisterAct;
    //////////////////////////////////////////
    public static final String EMAIL = "EMAIL";
    public static final String PASS = "PASS";
    public static final String STD_ID = "STD_ID";

    public static final String FLAG = "FLAG";
    //////////////////////////////////////////

    private boolean flag = false;

    ///////////////////////////////////The Main Two Important Objects To Use "Shared Preferences":
    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write
    ///////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osa_login);
        setupViews();           //just making the hooks

        setupSharedPrefs();             //siting SharedPrefs up (making the app ready to Read/Write)

        checkPrefs();           //Checking if "Remember me" was previously enabled by the user, if yes-> bring the data and fill it

        btnToRegisterAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViews() { //just making the hooks
        edtLogEmail = findViewById(R.id.edtLogEmail);
        edtLogPassword = findViewById(R.id.edtLogPassword);
        rmmbrme = findViewById(R.id.rmmbrme);

        btnToRegisterAct = findViewById(R.id.btnToRegisterAct);

        requestQueue = Volley.newRequestQueue(this);

    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
//        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs = getSharedPreferences(USERS_LIST, 0);
        editor = prefs.edit();  //to Write
    }

    private void checkPrefs() { //Checking if "Remember me" was previously enabled by the user, if yes-> bring the data and fill it
        flag = prefs.getBoolean(FLAG, false);   //if "FLAG" is true => flag=true // other than that=>flag=false by default

        //flag=true means that last user checked "Remember me" while logging in the last time
        //so we need to log him in now without the need from him to reenter his data
        if (flag) {   //if there are data stored ("Remember me" was previously enabled)
            String mail = prefs.getString(EMAIL, "");
            String password = prefs.getString(PASS, "");


            edtLogEmail.setText(mail);
            edtLogPassword.setText(password);
            rmmbrme.setChecked(true);
        }
    }

    ///////////////////////////////////Last Thing: the process of logging in
    public void btnLoginOnClick(View view) {
        String email = edtLogEmail.getText().toString();
        String password = edtLogPassword.getText().toString();

        JSONObject loginPayload = new JSONObject();
        try {
            loginPayload.put("student_email", email);
            loginPayload.put("pass", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:5000/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginPayload,
                response -> {
                    try {
                        boolean isSuccess = response.getBoolean("success");


                        if (isSuccess) {
                            int studentId = response.getInt("student_id"); // Extract the student_id


                            if (rmmbrme.isChecked()) {
                                editor.putBoolean(FLAG, true);
                                editor.putString(EMAIL, email);
                                editor.putString(PASS, password);
                                editor.putInt(STD_ID, studentId); // Save the student_id
                                editor.apply();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(STD_ID, studentId);
                            startActivity(intent);
                            finish();
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error parsing JSON response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error instanceof AuthFailureError) {
                        Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }



    @Override
    protected void onStart() {

//        loadUsers();

        //creating the arrayList of users, and making the needed ArrayAdapter to contain it
//        users = new ArrayList<>();


        super.onStart();
    }

//    private void loadUsers() {
//
////        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//
//        String json = prefs.getString(USERS_LIST, null);
//
//        //getting the type of users arrayList
//        Type type = new TypeToken<ArrayList<User>>() {
//        }.getType();
//
//        //saving data to users arrayList, after converting it from json using gson
//        RegisterActivity.users = gson.fromJson(json, type);//main line
//
//        //checking if DueTasks arrayList is not declared to make one
//        if (RegisterActivity.users == null) {
//            RegisterActivity.users = new ArrayList<>();
//        }
////        DueTasksAdapter.notifyDataSetChanged();
//        Toast.makeText(this, "users arrayList filled from Shared preferences. ", Toast.LENGTH_SHORT).show();
//
//    }
}