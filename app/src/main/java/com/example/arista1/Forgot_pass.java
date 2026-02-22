package com.example.arista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Forgot_pass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);


        Button btnNxt = findViewById(R.id.button_confirm);
        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Forgot_pass.this , Login.class);
                startActivity(intent);
                Toast.makeText(Forgot_pass.this, "Password successfully changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}