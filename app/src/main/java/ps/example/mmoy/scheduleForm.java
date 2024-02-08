package ps.example.mmoy;

import static ps.example.mmoy.MainActivity.prefs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class scheduleForm extends AppCompatActivity {
    ///////////////////////////////////WHAT
    private EditText edtTxt_courseCode;
    private EditText edtTxt_courseTitle;

    ///////////////////////////////////WHO
    private EditText edtTxt_instructor;

    ///////////////////////////////////something :)
    private EditText edtTxt_classNum;

    ///////////////////////////////////WHEN
    private EditText spclEdtTxt_courseDays;
    private Spinner spnr_timeFrom;
    private Spinner spnr_timeTo;

    ///////////////////////////////////WHERE
    private Spinner spnr_faculty;
    private EditText edtTxt_roomNum;

    ///////////////////////////////////ACCCTION
    private Button submitSchedule;
    /////////////////////////////////////////////////////////////////////////////////////VOLLEY
    static RequestQueue requestQueue;

    private ArrayList<Course> coursesList;

    private TextView txtvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);

        coursesList = new ArrayList<Course>();

        setupViews();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("list")) {
            String title = intent.getStringExtra("title");
            txtvTitle.setText(title);
//            coursesList = (ArrayList<Course>) getIntent().getSerializableExtra("list");

            coursesList = (ArrayList<Course>) intent.getSerializableExtra("list");
//            coursesList=intent.getExtra("list");
        }


//        fetchCoursesFromDatabase();
//        System.out.println(isEmpty);
//        Intent intent = new Intent(scheduleForm.this, schedule.class);
//        startActivity(intent);
//
//        if (!coursesList.isEmpty()) {
////            Intent intent = new Intent(scheduleForm.this, schedule.class);
////            startActivity(intent);
//        }
        spclEdtTxt_courseDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysDialog();
            }
        });

        submitSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from the form fields
                String courseCode = edtTxt_courseCode.getText().toString().toUpperCase();
                String courseTitle = edtTxt_courseTitle.getText().toString();

                String instructor = edtTxt_instructor.getText().toString();

                String classNum = edtTxt_classNum.getText().toString();

                String courseDays = spclEdtTxt_courseDays.getText().toString();
                String timeFrom = spnr_timeFrom.getSelectedItem().toString();
                String timeTo = spnr_timeTo.getSelectedItem().toString();

                String faculty = spnr_faculty.getSelectedItem().toString();
                String roomNum = edtTxt_roomNum.getText().toString();

                boolean isValid = dataIsValid(courseCode, courseTitle, instructor,
                        classNum, courseDays, timeFrom,
                        timeTo, faculty, roomNum);


                // Validate the data
                if (!isValid) {
                    Toast.makeText(scheduleForm.this, "Please fill in all fields correctly.", Toast.LENGTH_LONG).show();
                } else {
                    Course course = new Course(courseCode, courseTitle, instructor,
                            classNum, courseDays, timeFrom,
                            timeTo, faculty, roomNum);


//                    boolean isDuplicate = false;
//                    for (Course c : coursesList) {
//                        System.out.println("chinaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+c);
//                        if (c.getCourseCode().equals(courseCode) &&
//                                c.getCourseTitle().equals(courseTitle)) {
////                            System.out.println(c);
//                            isDuplicate = true;
//                            break;
//                        }
//                    }


                    if (coursesList.contains(course)) {//duplicate
//                    if (isDuplicate) {//duplicate
                        Toast.makeText(scheduleForm.this, "This course has already been scheduled.", Toast.LENGTH_LONG).show();

                    } else {
                        coursesList.add(course);
//                        addCourseToDB(courseCode, courseTitle, instructor,
//                                classNum, courseDays, timeFrom,
//                                timeTo, faculty, roomNum);

                        addCourseToDB_forStudent(courseCode, courseTitle, instructor,
                                classNum, courseDays, timeFrom,
                                timeTo, faculty, roomNum);

                        Intent intent = new Intent(scheduleForm.this, scheduleActivity.class);
                        intent.putExtra("list", coursesList);
                        startActivity(intent);
                    }

                }

            }
        });

    }

    private void addCourseToDB(String courseCode, String courseTitle, String instructor, String classNum,
                               String courseDays, String timeFrom, String timeTo, String faculty, String roomNum) {

        String url = "http:/10.0.2.2:5000/add_course";


        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("course_code", courseCode);

            jsonParams.put("course_title", courseTitle);
            jsonParams.put("instructor", instructor);
            jsonParams.put("class_num", classNum);
            jsonParams.put("course_days", courseDays);
            jsonParams.put("time_from", timeFrom);
            jsonParams.put("time_to", timeTo);
            jsonParams.put("faculty", faculty);
            jsonParams.put("room_num", roomNum);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParams,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String resultMessage = "";
                            try {
                                resultMessage = response.getString("result");


                                String finalResultMessage = resultMessage;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the result message to the user using a Toast
                                        Toast.makeText(getApplicationContext(), finalResultMessage, Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            Toast.makeText(scheduleForm.this, resultMessage, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(scheduleForm.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            scheduleForm.requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //    private void fetchCoursesFromDatabase() {
//        // Assuming you have a function to make the HTTP request
//        // For example with Volley:
//        String url = "http:/10.0.2.2:5000/courses";
//
//        // Prepare the request
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject courseObject = response.getJSONObject(i);
//
//                        String courseCode = courseObject.getString("course_code");
//                        String courseTitle = courseObject.getString("course_title");
//
//                        String instructor = courseObject.getString("instructor");
//
//                        String classNum = courseObject.getString("class_num");
//
//                        String courseDays = courseObject.getString("course_days");
//                        String timeFrom = courseObject.getString("time_from");
//                        String timeTo = courseObject.getString("time_to");
//
//                        String faculty = courseObject.getString("faculty");
//                        String roomNum = courseObject.getString("room_num");
//
//                        // Add to enteredCourses list
//                        coursesList.add(new Course(courseCode, courseTitle, instructor, classNum,
//                                courseDays, timeFrom, timeTo, faculty, roomNum));
//
//                        System.out.println(coursesList);
//
//                    } catch (JSONException e) {
//                        Log.d("Error", e.toString());
//                        e.printStackTrace();
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(scheduleForm.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("Error_json", error.toString());
//                    }
//                });
//
//        // Add the request to the RequestQueue.
//        requestQueue.add(request);
//    }
    private void addCourseToDB_forStudent(String courseCode, String courseTitle, String instructor, String classNum,
                                          String courseDays, String timeFrom, String timeTo, String faculty, String roomNum) {

        int studentId = prefs.getInt("studentId", -1); // -1 is the default value if no id is found

        String url = "http:/10.0.2.2:5000/add_course_for_student/" + studentId;


        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("course_code", courseCode);

            jsonParams.put("course_title", courseTitle);
            jsonParams.put("instructor", instructor);
            jsonParams.put("class_num", classNum);
            jsonParams.put("course_days", courseDays);
            jsonParams.put("time_from", timeFrom);
            jsonParams.put("time_to", timeTo);
            jsonParams.put("faculty", faculty);
            jsonParams.put("room_num", roomNum);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParams,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String resultMessage = "";
                            try {
                                resultMessage = response.getString("result");


                                String finalResultMessage = resultMessage;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the result message to the user using a Toast
                                        Toast.makeText(getApplicationContext(), finalResultMessage, Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            Toast.makeText(scheduleForm.this, resultMessage, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(scheduleForm.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            scheduleForm.requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating JSON params.", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }


    private boolean dataIsValid(String courseCode, String courseTitle, String instructor,
                                String classNum, String courseDays, String timeFrom,
                                String timeTo, String faculty, String roomNum) {
        // Check for empty fields
        if (courseCode.isEmpty() || courseTitle.isEmpty() || instructor.isEmpty() ||
                classNum.isEmpty() || courseDays.isEmpty() || timeFrom.isEmpty() ||
                timeTo.isEmpty() || faculty.isEmpty() || roomNum.isEmpty()) {
            return false; // Some fields are empty
        }

        // Check if classNumber is a number
        try {
            Integer.parseInt(classNum);
        } catch (NumberFormatException e) {
            return false; // classNumber is not a valid number
        }

        // Check if roomNumber is a number
        try {
            Integer.parseInt(roomNum);
        } catch (NumberFormatException e) {
            return false; // roomNumber is not a valid number
        }

        if (!isTimeFromBeforeTimeTo(timeFrom, timeTo)) {
            return false; // timeFrom is not before timeTo
        }


        // Add any additional validations you need

        return true; // All validations passed

    }

    private boolean isTimeFromBeforeTimeTo(String timeFrom, String timeTo) {
        String[] fromParts = timeFrom.split(":");
        String[] toParts = timeTo.split(":");

        int fromHours = Integer.parseInt(fromParts[0]);
        int fromMinutes = Integer.parseInt(fromParts[1]);
        int toHours = Integer.parseInt(toParts[0]);
        int toMinutes = Integer.parseInt(toParts[1]);

        // First, compare hours
        if (fromHours < toHours) {
            return true;
        } else if (fromHours == toHours) {
            // If hours are equal, compare minutes
            return fromMinutes < toMinutes;
        } else {
            return false; // fromHours is greater than toHours, so timeFrom is not before timeTo
        }
    }

    private void setupViews() { //just making the hooks
        requestQueue = Volley.newRequestQueue(this);

        //WHAT
        edtTxt_courseCode = findViewById(R.id.edtTxt_courseCode);   //1
        edtTxt_courseTitle = findViewById(R.id.edtTxt_courseTitle); //2
        //WHO
        edtTxt_instructor = findViewById(R.id.edtTxt_instructor);   //3
        //something :)
        edtTxt_classNum = findViewById(R.id.edtTxt_classNum);       //4

        /////////////////////////////////////////////////
        //WHEN
        spclEdtTxt_courseDays = findViewById(R.id.spclEdtTxt_courseDays);       //5
        spnr_timeFrom = findViewById(R.id.spnr_timeFrom);                       //6
        spnr_timeTo = findViewById(R.id.spnr_timeTo);                           //7

        ArrayList<String> timeOptions = new ArrayList<>();
        // Set the starting time to 08:00
        int startHour = 8;
        int startMinute = 0;

        // Set the ending time to 17:30
        int endHour = 17;
        int endMinute = 30;

        while (startHour < endHour || (startHour == endHour && startMinute <= endMinute)) {
            String timeOption = "-1";
            if (startHour < 10 || startMinute < 10) {

                if (startHour < 10 && startMinute < 10) {
                    timeOption = "0" + startHour + ":" + "0" + startMinute;
                } else {
                    if (startHour < 10)
                        timeOption = "0" + startHour + ":" + startMinute;

                    if (startMinute < 10)
                        timeOption = startHour + ":" + "0" + startMinute;
                }


            } else {
                timeOption = startHour + ":" + startMinute;
            }
            timeOptions.add(timeOption);

            startMinute += 5;

            if (startMinute == 60) {
                startHour++;
                startMinute = 0;
            }
        }


        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeOptions);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnr_timeFrom.setAdapter(timeAdapter);
        spnr_timeTo.setAdapter(timeAdapter);

        /////////////////////////////////////////////////       //WHERE

        spnr_faculty = findViewById(R.id.spnr_faculty);           //8
        String[] faculties = new String[]{"Bamieh", "Maktoum", "Masri", "SCI", "S.Abdulhadi", "Aggad", "wks", "Khoury", "Alsadik", "IOL", "N.Shaheen", "A.Shaheen", "PNH", "Masrouji", "Bahrain", "KNH", "NSA", "GYM", "Zeenni", "Sh.Shaheen", "O.Abdulhadi", "Al.Juraysi", "Masri", "Aweidah", "Bamieh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, faculties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_faculty.setAdapter(adapter);

        edtTxt_roomNum = findViewById(R.id.edtTxt_roomNum);         //9

        //the button
        submitSchedule = findViewById(R.id.submitSchedule);
        txtvTitle = findViewById(R.id.txtvTitle);
    }

    private void showDaysDialog() {
        final String[] days = {"Saturday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        boolean[] checkedDays = new boolean[days.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMultiChoiceItems(days, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedDays[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder selectedDays = new StringBuilder();
                for (int i = 0; i < days.length; i++) {
                    if (checkedDays[i]) {
                        selectedDays.append(days[i]).append(", ");
                    }
                }
                spclEdtTxt_courseDays.setText(selectedDays.toString());
            }
        });

        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}