package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the logo screen as the first screen
        setContentView(R.layout.splash_logo);

        // Find the Next button and set up the click listener
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the first splash screen
                Intent intent = new Intent(MainActivity.this, Splash1Activity.class);
                startActivity(intent);
            }
        });
    }
}