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
    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(PickBus.this, ticket_page.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(PickBus.this, Profile.class);
        startActivity(intent);
    }
}
