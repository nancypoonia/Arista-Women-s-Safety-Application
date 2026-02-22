package com.example.arista1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Splash1Activity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    // List of required permissions
    private final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.RECEIVE_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_1);

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            // Only proceed if all permissions are granted
            if (hasAllPermissions()) {
                startActivity(new Intent(Splash1Activity.this, Splash2Activity.class));
            } else {
                Toast.makeText(this, "Please grant all permissions to continue", Toast.LENGTH_SHORT).show();
            }
        });

        // Check and request permissions if not granted
        if (!hasAllPermissions()) {
            requestNecessaryPermissions();
        }
    }

    // Check if all required permissions are granted
    private boolean hasAllPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Request missing permissions
    private void requestNecessaryPermissions() {
        ActivityCompat.requestPermissions(this,
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Some permissions denied. Emergency features might not work properly.", Toast.LENGTH_LONG).show();
            }
        }
    }
}