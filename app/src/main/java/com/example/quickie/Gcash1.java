package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gcash1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash1);
    }

    public void navigateToGcash2Page(View view) {
        Intent intent = new Intent(Gcash1.this, Gcash2.class);
        startActivity(intent);
    }
}