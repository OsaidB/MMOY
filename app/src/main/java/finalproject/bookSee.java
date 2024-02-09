package finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ps.example.mmoy.R;

public class bookSee extends AppCompatActivity {
    public static String title;
    public static String category;
    public static String description;
    public static double price;
    public static int senderId;

    TextView lblTitle, lblCat, lblDes, lblPrice;
    Button btnRes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mah_book_see);
        lblTitle = findViewById(R.id.lblTitle);
        lblCat = findViewById(R.id.lblCat);
        lblDes = findViewById(R.id.lblDes);
        lblPrice = findViewById(R.id.lblPrice);
        btnRes = findViewById(R.id.btnRes);

        lblTitle.setText(title);
        lblCat.setText(category);
        lblDes.setText(description);
        lblPrice.setText(price + "");

    }
}