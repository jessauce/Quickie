package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.UUID;

import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.ProgressBar;

public class Gcash3 extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash3);

        // Get the total amount from the Intent extra
        String totalAmount = getIntent().getStringExtra("totalAmount");

        // Find the TextView in Gcash3 and set the total amount
        TextView totalAmountTextView = findViewById(R.id.totalamount);
        totalAmountTextView.setText(totalAmount);

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
    }
    public void navigateToTicketPage(View view) {

        progressBar.setVisibility(View.VISIBLE);
        // Generate the reference number
        String referenceNumber = generateReferenceNumber();

        // Generate the QR code and pass the reference number to ticket_page
        generateQRCode(referenceNumber);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the ProgressBar after the delay
                progressBar.setVisibility(View.GONE);

                // Proceed to page after the delay
                Intent intent = new Intent(Gcash3.this, ticket_page.class);
                intent.putExtra("referenceNumber", referenceNumber);
                startActivity(intent);
            }
        }, 4000);
    }
    private String generateReferenceNumber() {
        return "Ref-" + UUID.randomUUID().toString();
    }

    private void generateQRCode(String referenceNumber) {
        // You can leave this empty as we have moved the QR code generation to ticket_page
    }
}