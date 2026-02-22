package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Splash2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the second splash screen layout
        setContentView(R.layout.splash_2);

        // Find the Next button and set up the click listener
        Button btnNext = findViewById(R.id.btnNext2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the third splash screen
                Intent intent = new Intent(Splash2Activity.this, Splash3Activity.class);
                startActivity(intent);
            }
        });
    }
}
