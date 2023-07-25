package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.UUID;

import android.widget.Button;


public class Gcash3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash3);
    }
    public void navigateToTicketPage(View view) {
        // Generate the reference number
        String referenceNumber = generateReferenceNumber();

        // Generate the QR code and pass the reference number to ticket_page
        generateQRCode(referenceNumber);

        // Navigate to ticket_page with the generated reference number as an extra
        Intent intent = new Intent(Gcash3.this, ticket_page.class);
        intent.putExtra("referenceNumber", referenceNumber);
        startActivity(intent);
    }
    private String generateReferenceNumber() {
        return "Ref-" + UUID.randomUUID().toString();
    }

    private void generateQRCode(String referenceNumber) {
        // You can leave this empty as we have moved the QR code generation to ticket_page
    }
}