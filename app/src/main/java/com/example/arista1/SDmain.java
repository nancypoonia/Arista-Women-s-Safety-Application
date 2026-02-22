package com.example.arista1; // Change this to your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SDmain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdmain); // Use your actual XML file name
    }

    public void palmheelstrike(View view) {
        Intent intent = new Intent(this, Sd1Activity.class);
        startActivity(intent);
    }

    public void eyestrike(View view) {
        Intent intent = new Intent(this, Sd2Activity.class);
        startActivity(intent);
    }

    public void kneestrike(View view) {
        Intent intent = new Intent(this, Sd3Activity.class);
        startActivity(intent);
    }

    public void elbowjack(View view) {
        Intent intent = new Intent(this, Sd4Activity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this , action_center.class);
        startActivity(intent);
    }
}
