package com.example.arista1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Emergency extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;
    Button back;
    FirebaseUser user;
    FirebaseFirestore fStore;
    FirebaseAuth auth;
    String userId;
    TextView hz, hz2, new11;

    String contact1, contact2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        back = findViewById(R.id.backButton);
        new11 = findViewById(R.id.click);
        hz = findViewById(R.id.new1);
        hz2 = findViewById(R.id.new2);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = auth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();

        new11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        contact1 = documentSnapshot.getString("Emergency Contact 1");
                        contact2 = documentSnapshot.getString("Emergency Contact 2");

                        hz.setText(contact1);
                        hz2.setText(contact2);

                        // Request SMS permission if not granted
                        if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Emergency.this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    PERMISSION_REQUEST_CODE);
                        } else {
                            sendEmergencySms(contact1);
                            sendEmergencySms(contact2);
                        }
                    } else {
                        Toast.makeText(Emergency.this, "No emergency contacts found.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(Emergency.this, "Failed to fetch contacts.", Toast.LENGTH_SHORT).show();
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergency.this, action_center.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendEmergencySms(String number) {
        if (number != null && !number.isEmpty()) {
            String message = "⚠️ Emergency! Please help me immediately.(TEST MESSAGE IGNORE IF RECEIVED)";
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message, null, null);
                Toast.makeText(this, "SMS sent to " + number, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "SMS failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makeCall(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void callPolice(View view) {
        makeCall("100");
    }

    public void callWomenHelpline(View view) {
        makeCall("1091");
    }

    public void callAmbulance(View view) {
        makeCall("102");
    }

    public void callChildHelpline(View view) {
        makeCall("1098");
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendEmergencySms(contact1);
                sendEmergencySms(contact2);
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}