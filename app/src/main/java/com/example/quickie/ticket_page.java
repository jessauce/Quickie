package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button btnGenerateQR;
    private ImageView imgQRCode;
    private TextView txtReferenceNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        btnGenerateQR = findViewById(R.id.btnGenerateQR);
        imgQRCode = findViewById(R.id.imgQRCode);
        txtReferenceNumber = findViewById(R.id.txtReferenceNumber);

        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode(generateReferenceNumber());
            }
        });
    }

    private String generateReferenceNumber() {
        return "Ref-" + UUID.randomUUID().toString();
    }

    private void generateQRCode(String data) {
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
}