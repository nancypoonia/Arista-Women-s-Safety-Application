package com.example.arista1;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;
public class action_center extends AppCompatActivity {
    CardView emergencyCard, locationCard, selfDefenseCard, safetyDeviceCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_center);

        emergencyCard = findViewById(R.id.card1);
        safetyDeviceCard = findViewById(R.id.card2);
        selfDefenseCard = findViewById(R.id.card4);
        locationCard = findViewById(R.id.card5);

        emergencyCard.setOnClickListener(v -> {
            startActivity(new Intent(this, Emergency.class));
        });

        locationCard.setOnClickListener(v -> {
            startActivity(new Intent(this, MapActivity.class));
        });

        selfDefenseCard.setOnClickListener(v -> {
            startActivity(new Intent(this, SDmain.class));
        });

        safetyDeviceCard.setOnClickListener(v -> {
            startActivity(new Intent(this, Safety_Device.class));
        });
    }

    // ✅ Move this outside onCreate()
    public void go(View v) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
