package ps.example.mmoy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    static ArrayList<User> users;


    private EditText edtRegName;
    private EditText edtRegEmail;
    private Button btnDateOfBirth;
    private EditText edtPhone;
    private EditText edtRegPassword;

    private RadioGroup radioGroupGender;
    //=================================================================
    private RequestQueue requestQueue;
    private Button btnCancel;
    ///////////////////////////////////The Main Two Important Objects To Use "Shared Preferences":
    private SharedPreferences prefs;            //To Read
    private SharedPreferences.Editor editor;    //To Write
    ///////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();           //just making the hooks

        setupSharedPrefs();             //siting SharedPrefs up (making the app ready to Read/Write)


        btnDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                resetAll();


                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });
    }

    private void showDatePickerDialog() {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        String selectedDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                        btnDateOfBirth.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void setupViews() { //just making the hooks
        requestQueue = Volley.newRequestQueue(this);

        edtRegName = findViewById(R.id.edtRegName);
        edtRegEmail = findViewById(R.id.edtRegEmail);
        btnDateOfBirth = findViewById(R.id.btnDateOfBirth);
        edtPhone = findViewById(R.id.edtPhone);

        edtRegPassword = findViewById(R.id.edtRegPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
        prefs = getSharedPreferences(LoginActivity.USERS_LIST, 0);
        editor = prefs.edit();  //to Write
    }

    public void btnRegisterOnClick(View view) {
        String name = edtRegName.getText().toString();              //1
        String mail = edtRegEmail.getText().toString();             //2

        String dateOfBirth = btnDateOfBirth.getText().toString();   //3
        String phone = edtPhone.getText().toString();               //4
        String password = edtRegPassword.getText().toString();      //5

        RadioButton chkdBTN = findViewById(radioGroupGender.getCheckedRadioButtonId());
        String gender = chkdBTN.getText().toString();               //6


        if (name.length() > 1 && mail.length() > 1
                && dateOfBirth.length() > 1 && phone.length() > 1
                && password.length() > 1
        ) {

//            User user = new User(mail, dateOfBirth, phone, password, gender);

            JSONObject reg = new JSONObject();
            try {
                reg.put("student_name", name);
                reg.put("student_email", mail);

                reg.put("date_of_birth", dateOfBirth);
                reg.put("phone_number", phone);
                reg.put("pass", password);

                reg.put("gender", gender);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Error with registration.", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:5000/register";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    reg,
                    response -> {
                        try {
                            boolean isSuccess = response.getBoolean("success");
                            if (isSuccess) {
                                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                intent.putExtra(STD_ID, studentId);
                                startActivity(intent);
                                finish();

                            } else {
                                String message = response.getString("message");
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Error parsing JSON response.", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        if (error.networkResponse != null && error.networkResponse.statusCode == 409) {
                            // Email already in use
                            Toast.makeText(RegisterActivity.this, "Email already registered", Toast.LENGTH_LONG).show();
                        } else {
                            // Other errors
                            Toast.makeText(RegisterActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                        }
                    }
            );
            requestQueue.add(jsonObjectRequest);

        } else {
            Toast.makeText(this, "Email and password are required fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void resetAll() {
        edtRegName.setText("");
        edtRegEmail.setText("");
        btnDateOfBirth.setText("Click Here To Select Your Date Of Birth");
        edtPhone.setText("");

        edtRegPassword.setText("");

    }

}