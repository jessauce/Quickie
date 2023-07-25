package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gcash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash2);
    }

    public void navigateToGcash3Page(View view) {
        Intent intent = new Intent(Gcash2.this, Gcash3.class);
        startActivity(intent);
    }
}