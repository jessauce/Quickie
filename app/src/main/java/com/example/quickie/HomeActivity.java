package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class HomeActivity extends AppCompatActivity {

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
    }
}
