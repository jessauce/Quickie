package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;


public class Get_Started extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button startButton = findViewById(R.id.startButton);
        final CheckBox checkBox = findViewById(R.id.checkBox);
        TextView termsTextView = findViewById(R.id.termsTextView);

        TextView textView = findViewById(R.id.termsTextView);

        String text = "terms and conditions";

        SpannableString ss = new SpannableString(text);

        UnderlineSpan underlineSpan = new UnderlineSpan();

        ss.setSpan(underlineSpan, 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);

        // Initialize button state based on checkbox state
        startButton.setEnabled(checkBox.isChecked());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable/disable button based on checkbox state
                startButton.setEnabled(isChecked);
                // Change button color based on checkbox state
                int buttonColor = isChecked ? getResources().getColor(R.color.button_enabled_color) : getResources().getColor(R.color.button_disabled_color);
                startButton.setBackgroundColor(buttonColor);
            }
        });

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

        builder.setNegativeButton("Understood", new DialogInterface.OnClickListener() {
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
