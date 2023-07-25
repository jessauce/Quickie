package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.os.Handler;
import android.widget.ProgressBar;

public class Gcash2 extends AppCompatActivity {
    private Button dateButton;
    private String selectedDate = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash2);

        // Get the total price from the Intent
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);

        // Find the TextView and set the total price
        TextView priceAmountTextView = findViewById(R.id.priceamount);
        priceAmountTextView.setText("₱" + totalPrice);

        // Calculate 10% off the total price
        double discountAmount = calculateDiscount(totalPrice, 0.10);

        // Find the TextView for displaying the discounted amount
        TextView totalAmountTextView = findViewById(R.id.totalamount);

        // Set the discounted amount in the TextView
        totalAmountTextView.setText("₱" + discountAmount);

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
    }

    public void navigateToGcash3Page(View view) {
        // Show the ProgressBar before navigating
        progressBar.setVisibility(View.VISIBLE);
        TextView totalAmountTextView = findViewById(R.id.totalamount);
        String totalAmount = totalAmountTextView.getText().toString();

        // Simulate a 4-second delay using a Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the ProgressBar after the delay
                progressBar.setVisibility(View.GONE);

                // Proceed to Gcash3 page after the delay
                Intent intent = new Intent(Gcash2.this, Gcash3.class);
                intent.putExtra("totalAmount", totalAmount);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
            }
        }, 4000);
    }

    // Method to calculate the discount amount
    private double calculateDiscount(int totalPrice, double discountPercentage) {
        return totalPrice - (totalPrice * discountPercentage);
    }
}