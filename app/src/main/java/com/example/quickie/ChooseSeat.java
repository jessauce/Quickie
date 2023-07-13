package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseSeat extends AppCompatActivity {

    public void navigateToPickBus(View view) {
        Intent intent = new Intent(ChooseSeat.this, PickBus.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(ChooseSeat.this, ticket_page.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(ChooseSeat.this, Profile.class);
        startActivity(intent);
    }

    public void navigateToTicketPage() {
        // Navigate to About Us activity
        Intent intent = new Intent(ChooseSeat.this, PickBus.class);
        startActivity(intent);
    }
}