package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Gcash1 extends AppCompatActivity {

    private Button dateButton;
    private String selectedDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash1);

        selectedDate = getIntent().getStringExtra("selectedDate");

        int totalPrice = getIntent().getIntExtra("totalPrice", 0);

        Button proceedButton = findViewById(R.id.GLoginButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gcash1.this, Gcash2.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });
    }

    public void navigateToGcash2Page(View view) {
        Intent intent = new Intent(Gcash1.this, Gcash2.class);
        intent.putExtra("selectedDate", selectedDate);
        startActivity(intent);
    }
}