package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import java.io.File;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Environment;
public class PersonalData extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView emailTextView;
    private TextView phoneTextView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);


        emailTextView = findViewById(R.id.Email);
        phoneTextView = findViewById(R.id.PhoneNo);
        TextView nameTextView = findViewById(R.id.Name);
        TextView usernameTextView = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Retrieve the current user's email
        String userEmail = mAuth.getCurrentUser().getEmail();

        // Query the userData collection based on the user's email
        db.collection("userData")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String email = document.getString("email");
                        String phone = document.getString("phone");
                        String firstName = document.getString("firstName");
                        String lastName = document.getString("lastName");
                        String username = document.getString("username");

                        // Set the email, phone number, name, and username in the respective TextViews
                        emailTextView.setText(email);
                        phoneTextView.setText(phone);
                        nameTextView.setText(firstName + " " + lastName);
                        usernameTextView.setText(username);
                    }
                });
    }
}
