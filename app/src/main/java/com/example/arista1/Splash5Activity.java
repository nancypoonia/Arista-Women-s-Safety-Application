package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Splash5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the fifth (final) splash screen layout
        setContentView(R.layout.splash_5);

        // Find the Get Started button and set up the click listener
        Button btnGetStarted = findViewById(R.id.btnNext5);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the home/main activity of the app
                // This would be the first screen after onboarding
                Intent intent = new Intent(Splash5Activity.this, Register_activity.class);
                // Clear the back stack so user can't go back to onboarding screens
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
