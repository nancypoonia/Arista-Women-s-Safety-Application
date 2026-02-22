package com.example.arista1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_activity extends AppCompatActivity {
EditText name , email , contact , password;
ProgressBar progressBar;
FirebaseAuth mAuth;
FirebaseFirestore fStore;
String userID;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(Register_activity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the home screen layout (to be implemented)
        // This is a placeholder for the main app functionality
        setContentView(R.layout.registration);
        progressBar = findViewById(R.id.progress_bar);
        mAuth =  FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        fStore = FirebaseFirestore.getInstance();
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        password = findViewById(R.id.password);


        Toast.makeText(this, "Welcome to ARISTA Safety App!", Toast.LENGTH_LONG).show();
        Button btnNxt = findViewById(R.id.button_register);


        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email1, password1 , name1 , contact1 ;
                email1 = String.valueOf(email.getText());
                password1 = String.valueOf(password.getText());
                name1 = String.valueOf(name.getText());
                contact1 = String.valueOf(contact.getText());


                if (TextUtils.isEmpty(email1)){
                    Toast.makeText(Register_activity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password1)){
                    Toast.makeText(Register_activity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contact1)){
                    Toast.makeText(Register_activity.this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name1)){
                    Toast.makeText(Register_activity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password1.length() < 6 ){
                    Toast.makeText(Register_activity.this, "Password length must be greater than 6 characters.", Toast.LENGTH_SHORT).show();
                    
                }
                mAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(Register_activity.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    userID= mAuth.getCurrentUser().getUid();
                                    // here we are creating collections and document using firestore.
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                           // here string as a key and object as a data.
                                    Map<String,Object>user = new HashMap<>();
                                    user.put("Full Name",name1);
                                    user.put("Contact",contact1);
                                    user.put("Email",email1);
                                    user.put("Password",password1);
                                    // putting data to firestore
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: User profile is created for "+userID);
                                        }
                                    });

                                    Intent intent = new Intent(Register_activity.this , Dashboard.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.


                                    Toast.makeText(Register_activity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }

        });
        Button btnNext2 = findViewById(R.id.button_signin);
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Register_activity.this , Login.class);
                startActivity(intent);
            }
        });

    }
}
