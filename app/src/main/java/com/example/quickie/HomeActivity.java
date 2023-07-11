package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button bottomsheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String[] originItems = {"Cebu South Bus Terminal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, originItems);
        AutoCompleteTextView originComboBox = findViewById(R.id.originComboBox);
        originComboBox.setAdapter(adapter);
        originComboBox.setThreshold(0); // Set threshold to 0 for immediate dropdown appearance

        String[] destinationItems = {"Naga Terminal", "San Fernando Terminal", "Carcar Terminal", "Argao Terminal", "Dalaguete Terminal", "Alcoy Terminal", "Boljoon Terminal", "Osblob Terminal", "Santander Terminal"};
        ArrayAdapter<String> dadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, destinationItems);
        AutoCompleteTextView destinationComboBox = findViewById(R.id.destinationComboBox);
        destinationComboBox.setAdapter(dadapter);
        destinationComboBox.setThreshold(0); // Set threshold to 0 for immediate dropdown appearance

        bottomsheet = findViewById(R.id.continueButton);
        bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom);

        LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
        LinearLayout shareLayout = dialog.findViewById(R.id.layoutShare);
        LinearLayout uploadLayout = dialog.findViewById(R.id.layoutUpload);
        LinearLayout printLayout = dialog.findViewById(R.id.layoutPrint);
        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "Edit is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "Share is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        uploadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "Upload is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        printLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "Print is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}






