package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Splash3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the third splash screen layout
        setContentView(R.layout.splash_3);

        // Find the Next button and set up the click listener
        Button btnNext = findViewById(R.id.btnNext3);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the fourth splash screen
                Intent intent = new Intent(Splash3Activity.this, Splash4Activity.class);
                startActivity(intent);
            }
        });
    }
}