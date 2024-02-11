package ps.example.mmoy;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import moh.TaskManagerActivity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private ArrayList<Course> courseList;

    public CourseAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView txtV_CourseCode;
        public TextView txtV_DaysTime;

        public TextView txtV_Room;

        ////////////////////////////////////
        public TextView txtV_CourseTitle;


        // ... other views ...

        public CourseViewHolder(View itemView) {
            super(itemView);
            txtV_CourseCode = itemView.findViewById(R.id.txtV_CourseCode);
            txtV_DaysTime = itemView.findViewById(R.id.txtV_DaysTime);
            txtV_Room = itemView.findViewById(R.id.txtV_Room);

//          txtV_CourseTitle = itemView.findViewById(R.id.txtV_CourseTitle);
            // ... initialize other views ...
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.osa_item_course, parent, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course currCourse = courseList.get(position);
        holder.txtV_CourseCode.setText(currCourse.getCourseCode());
        holder.txtV_DaysTime.setText(currCourse.getDaysTime());//
        holder.txtV_Room.setText(currCourse.getLocation());
//        holder.txtV_CourseTitle.setText(currentItem.getCourseTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Present options to edit or delete
                showEditDeleteOptions(currCourse, view.getContext());
            }
        });
/////////////////////////////////////////////////////////////////////////////////////txtV_CourseCode
        holder.txtV_CourseCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(currCourse.getCourseCode());

                // Set the message to display the details
                builder.setMessage("Title: " + currCourse.getCourseTitle() +
                        "\nClass: " + currCourse.getClassNum() +
                        "\nInstructor: " + currCourse.getInstructor());

                // Add a "Manage Tasks" button
                builder.setPositiveButton("Manage Tasks", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Manage Tasks" button
                        // Handle task management here
                        // For example, start a new Activity with an Intent
                    }
                });

                // Add a "Close" button
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////txtV_Room
        String loc = holder.txtV_Room.getText().toString();

        holder.txtV_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Navigate to Class")
                        .setMessage("Do you want to be guided to " + loc + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                navigateToBuilding(currCourse.getFaculty(), view.getContext());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


    }

    private void showEditDeleteOptions(Course course, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(course.getCourseCode());

        // Define the options
        CharSequence[] options = {"Edit", "Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Edit
                        // Start an activity to edit the course
//                        Intent editIntent = new Intent(context, EditCourseActivity.class);
//                        editIntent.putExtra("COURSE_ID", course.getId()); // Assuming Course class has getId method
//                        context.startActivity(editIntent);
//                        break;
//                    case 1: // Delete
//                        // Confirm deletion
//                        confirmDelete(course, context);
                        break;
                }
            }
        });
        builder.show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void navigateToBuilding(String roomNumber, Context context) {
        String coordinates = convertRoomToLocation(roomNumber);
        if (!coordinates.equals("Coordinates not found")) {
            Uri gmmIntentUri = Uri.parse("geo:" + coordinates);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
                Toast.makeText(context, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
        }
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Map<String, String> map_buildingToCoordinates = new HashMap<>();

    private void initializeBuildingCoordinates() {
        map_buildingToCoordinates.put("Bamieh", "31.96064560143811, 35.183623461167244");
        map_buildingToCoordinates.put("Maktoum", "31.95939078524763, 35.18167466798065");
//        map_buildingToCoordinates.put("Maktoum", "lat,long");
        // Add the rest of your mappings here
        // e.g., buildingToCoordinatesMap.put("Masri", "lat,long");
    }

    private String convertRoomToLocation(String faculty) {
        // Placeholder: Convert room number to a location name or coordinates
        // This is where you map room numbers to actual building names or coordinates
//        return "Some Building, Campus Name, City";

        // Call this method somewhere in your initialization code
        initializeBuildingCoordinates();

        // Extract the building name from the room number
//        String buildingName = extractBuildingName(faculty);

        // Lookup the coordinates based on the building name
        String coordinates = map_buildingToCoordinates.get(faculty);

        if (coordinates != null) {
            return coordinates;
        } else {
            return "Coordinates not found";
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
