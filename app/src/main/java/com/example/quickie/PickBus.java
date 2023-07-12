package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PickBus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_bus);
    }

    public void navigateToChooseSeat(View view) {
        Intent intent = new Intent(this, ChooseSeat.class);
        startActivity(intent);
    }
}
