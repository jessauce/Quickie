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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        imgQRCode = findViewById(R.id.imgQRCode);
        txtReferenceNumber = findViewById(R.id.txtReferenceNumber);

        String referenceNumber = getIntent().getStringExtra("referenceNumber");
        if (referenceNumber != null) {
            generateQRCode(referenceNumber);
        }
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