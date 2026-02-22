package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Splash4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the fourth splash screen layout
        setContentView(R.layout.splash_4);

        // Find the Next button and set up the click listener
        Button btnNext = findViewById(R.id.btnNext4);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the fifth splash screen
                Intent intent = new Intent(Splash4Activity.this, Splash5Activity.class);
                startActivity(intent);
            }
        });
    }
}