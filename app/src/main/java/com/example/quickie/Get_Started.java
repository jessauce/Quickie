package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class Get_Started extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button startButton = findViewById(R.id.startButton);
        final CheckBox checkBox = findViewById(R.id.checkBox);

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

    private void navigateToMainActivity() {
        Intent intent = new Intent(Get_Started.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
