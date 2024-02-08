package forthefilnal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Pharmacy_Cafeteria extends AppCompatActivity {
Button map2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_cafeteria);
        map2=findViewById(R.id.map);

        map2.setOnClickListener(view -> {
            getDirctions();
//            String loc=from.getText().toString();
//            String toloc=to.getText().toString();
//            if(loc.equals("") || toloc.equals("")){
//                Toast.makeText(this, "please enter the the from and the to", Toast.LENGTH_SHORT).show();
//            }else {
//                getDirctions();
//            }
        });
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