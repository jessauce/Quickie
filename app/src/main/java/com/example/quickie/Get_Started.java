package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.content.DialogInterface;


public class Get_Started extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button startButton = findViewById(R.id.startButton);
        final CheckBox checkBox = findViewById(R.id.checkBox);
        TextView termsTextView = findViewById(R.id.termsTextView);

        startButton.setEnabled(false); // Disable button by default

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setEnabled(checkBox.isChecked()); // Enable/disable button based on checkbox state
            }
        });

        termsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the pop-up or any other container for terms and conditions
                showTermsAndConditions();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    navigateToMainActivity();
                } else {
                    // Checkbox is not checked, display an error message or perform any other action
                    Toast.makeText(Get_Started.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showTermsAndConditions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms and Conditions");

        // Create a ScrollView to make the content scrollable
        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(layoutParams);

        // Create a TextView to display the terms and conditions content
        TextView termsTextView = new TextView(this);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_medium);
        termsTextView.setPadding(padding, padding, padding, padding);
        termsTextView.setText(R.string.terms_and_conditions_content);

        // Add the TextView to the ScrollView
        scrollView.addView(termsTextView);

        // Set the ScrollView as the dialog's view
        builder.setView(scrollView);

        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void navigateToMainActivity() {
        Intent intent = new Intent(Get_Started.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
