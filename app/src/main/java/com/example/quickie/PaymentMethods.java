package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentMethods extends AppCompatActivity {

    public void navigateToProfile(View view) {
        Intent intent = new Intent(PaymentMethods.this, Profile.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
    }
    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(PaymentMethods.this, ticket_page.class);
        startActivity(intent);
    }

    public void onHomeButtonClick(View view) {
        // Navigate to Home activity
        Intent intent = new Intent(PaymentMethods.this, HomeActivity.class);
        startActivity(intent);
    }
    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(PaymentMethods.this, Profile.class);
        startActivity(intent);
    }
}