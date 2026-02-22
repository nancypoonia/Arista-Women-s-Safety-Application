package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    EditText en1 , en2,pin_code , rel;
FirebaseAuth auth;
String userId;
Button button ,button2 , action;
TextView textView_em , textView_Ph , textView_Na;
FirebaseUser user;
FirebaseFirestore fStore;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_);
    en1 =findViewById(R.id.en1);
    en2 =findViewById(R.id.en2);
    pin_code =findViewById(R.id.pinn);
    rel =findViewById(R.id.relation);
    button2 = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();
        button =findViewById(R.id.logout);
        action = findViewById(R.id.button2);
        textView_em = findViewById(R.id.textEmail);
        textView_Ph = findViewById(R.id.textName);
        textView_Na = findViewById(R.id.textNumber);
        user = auth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();

    DocumentReference documentReference = fStore.collection("users").document(userId);
    documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        textView_Ph.setText(documentSnapshot.getString("Contact"));
            textView_Na.setText(documentSnapshot.getString("Full Name"));
        }
    });

        if (user == null ){
            Intent intent = new Intent(getApplicationContext() , Login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView_em.setText(user.getEmail());

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext() , Login.class);
                startActivity(intent);

            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, action_center.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pin_codee = pin_code.getText().toString();
                        String enn1 = en1.getText().toString();
                        String enn2 = en2.getText().toString();
                        String relation = rel.getText().toString();

                        DocumentReference documentReference = fStore.collection("users").document(userId);
                        Map<String, Object> emergencyData = new HashMap<>();
                        emergencyData.put("Emergency Contact 1", enn1);
                        emergencyData.put("Emergency Contact 2", enn2);
                        emergencyData.put("Pin Code", pin_codee);
                        emergencyData.put("Relation", relation);

                        documentReference.update(emergencyData)
                                .addOnSuccessListener(aVoid -> Toast.makeText(Dashboard.this, "Emergency info updated", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(Dashboard.this, "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });


            }
        });
    }
}