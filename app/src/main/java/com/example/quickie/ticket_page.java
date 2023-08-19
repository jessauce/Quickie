package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.UUID;

public class ticket_page extends AppCompatActivity {
    private ImageView imgQRCode;
    private TextView txtReferenceNumber;
    private TextView ticketDateTextView;
    private String selectedDate = "";
    private TextView passengersTextView;
    private TextView nameTextView;
    private TextView seatTextView;
    private TextView PriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        imgQRCode = findViewById(R.id.imgQRCode);
        txtReferenceNumber = findViewById(R.id.txtReferenceNumber);
        ticketDateTextView = findViewById(R.id.ticketdate);
        passengersTextView = findViewById(R.id.ticketpassenger);
        nameTextView = findViewById(R.id.ticketname);
        seatTextView = findViewById(R.id.ticketseat);
        PriceTextView = findViewById(R.id.tickettotal);

        String referenceNumber = getIntent().getStringExtra("referenceNumber");
        if (referenceNumber != null) {
            generateQRCode(referenceNumber);
        }
        String selectedDate = getIntent().getStringExtra("selectedDate");
        if (selectedDate != null) {
            ticketDateTextView.setText(selectedDate); // Set the selected date in the TextView
        }
        String persons = getIntent().getStringExtra("Persons");
        String accName = getIntent().getStringExtra("AccName");
        String seat = getIntent().getStringExtra("Seat");
        String date = getIntent().getStringExtra("Date");
        String price = getIntent().getStringExtra("Price");

        passengersTextView.setText(persons);
        nameTextView.setText(accName);
        seatTextView.setText(seat);
        ticketDateTextView.setText(date);
        PriceTextView.setText(price);
    }

    public String generateReferenceNumber() {
        return "Ref-" + UUID.randomUUID().toString();
    }

    public void generateQRCode(String data) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400);
            imgQRCode.setImageBitmap(bitmap);
            String referenceNumber = "Reference Number: " + data;
            txtReferenceNumber.setText(referenceNumber);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    public void onHomeButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(ticket_page.this, HomeActivity.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(ticket_page.this, Profile.class);
        startActivity(intent);
    }
}