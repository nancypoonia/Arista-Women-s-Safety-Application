package com.example.arista1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class Safety_Device extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safety_device);
    }

    // General method to open websites
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // Click methods linked from XML
    public void openSafetyPendantWebsite(View view) {
        openWebsite("https://www.amazon.in/Tracker-Necklace-Anti-Lost-Pendant-Tracking/dp/B083QSMVGV");
    }

    public void openHyperbolaCatapultWebsite(View view) {
        openWebsite("https://www.amazon.in/Palmer-Safety-Carabiner-Adjustable-Strength/dp/B0BDZZNDZ7");
    }

    public void openPepperSpraysWebsite(View view) {
        openWebsite("https://www.amazon.in/s?k=pepper+spray");
    }

    public void openLightLoudAlarmWebsite(View view) {
        openWebsite("https://www.amazon.in/s?k=personal+alarm");
    }

    public void goBack(View view) {
        Intent intent = new Intent(this , Dashboard.class);
        startActivity(intent);

    }
}

