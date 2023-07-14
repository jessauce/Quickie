package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PersonalData extends AppCompatActivity {

    public void navigateToProfile(View view) {
        Intent intent = new Intent(PersonalData.this, Profile.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(PersonalData.this, ticket_page.class);
        startActivity(intent);
    }

    public void onHomeButtonClick(View view) {
        // Navigate to Home activity
        Intent intent = new Intent(PersonalData.this, HomeActivity.class);
        startActivity(intent);
    }
    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(PersonalData.this, Profile.class);
        startActivity(intent);
    }
}