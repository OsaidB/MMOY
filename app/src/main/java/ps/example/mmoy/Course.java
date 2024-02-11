package ps.example.mmoy;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Course implements Serializable {
    private String courseCode;
    private String courseTitle;
    private String instructor;
    private String classNum;
    private String courseDays;
    private String timeFrom;
    private String timeTo;
    private String faculty;
    private String roomNum;

    public Course(String courseCode, String courseTitle, String instructor, String classNum,
                  String courseDays, String timeFrom, String timeTo, String faculty, String roomNum) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.instructor = instructor;
        this.classNum = classNum;
        this.courseDays = courseDays;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.faculty = faculty;
        this.roomNum = roomNum;

    }


//    Course(String courseLabel, String courseTitle, String classNumber) {
//        this.courseCode = courseLabel;
//        this.courseTitle = courseTitle;
//        this.classNum = classNumber;
//    }

    // You may want to override equals() and hashCode() if you plan to use HashSet or to easily find duplicates in a list.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseCode.equals(course.courseCode) &&
                courseTitle.equals(course.courseTitle) &&
                classNum.equals(course.classNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, courseTitle, classNum);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDaysTime() {
        String daysTime="chine";

        String[] daysArray = courseDays.split(",");

        Map<String, String> dayShortcuts = new HashMap<>();
        dayShortcuts.put("Monday", "Mon");
        dayShortcuts.put("Tuesday", "Tue");
        dayShortcuts.put("Wednesday", "Wed");
        dayShortcuts.put("Thursday", "Thu");
        dayShortcuts.put("Friday", "Fri");
        dayShortcuts.put("Saturday", "Sat");
        dayShortcuts.put("Sunday", "Sun");

        for (int i = 0; i < daysArray.length; i++) {
            daysArray[i] = dayShortcuts.get(daysArray[i]);
        }

        String daysShort = String.join(",", daysArray);
        /////////////////////////////////
        daysTime=daysShort+"\n"+timeFrom+" - "+timeTo;


        return daysTime;
    }


    public String getCourseTitle() {
        return courseTitle;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getClassNum() {
        return classNum;
    }

    public String getCourseDays() {
        return courseDays;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getRoomNum() {
        return roomNum;
    }
    public String getLocation() {
        String loc="chinaLoc";
        loc=faculty+roomNum;
        return loc;
    }


}
