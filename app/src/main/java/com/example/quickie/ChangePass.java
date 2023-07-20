package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePass extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private EditText edtCurrentPassword, edtNewPassword, edtConfirmNewPassword;
    private String userEmail; // To store the user's email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        edtCurrentPassword = findViewById(R.id.currentPassword);
        edtNewPassword = findViewById(R.id.newPassword);
        edtConfirmNewPassword = findViewById(R.id.confirmNewPassword);

        if (currentUser == null) {
            // User is not logged in. Handle this as per your app logic.
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Retrieve the current user's email
            userEmail = currentUser.getEmail();
        }
    }

    public void btnChangePasswordClick(View view) {
        String currentPassword = edtCurrentPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

        if (currentPassword.isEmpty()) {
            edtCurrentPassword.setError("Current Password Field can't be empty");
            return;
        }

        if (newPassword.isEmpty()) {
            edtNewPassword.setError("New Password Field can't be empty");
            return;
        }

        if (confirmNewPassword.isEmpty()) {
            edtConfirmNewPassword.setError("Confirm New Password Field can't be empty");
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "New Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reauthenticate the user with their current password
        AuthCredential credential = EmailAuthProvider.getCredential(userEmail, currentPassword);
        currentUser.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If reauthentication is successful, update the password
                        currentUser.updatePassword(newPassword)
                                .addOnCompleteListener(passwordUpdateTask -> {
                                    if (passwordUpdateTask.isSuccessful()) {
                                        // Password updated successfully
                                        Toast.makeText(ChangePass.this, "Password updated successfully.", Toast.LENGTH_SHORT).show();

                                        // After changing the password, sign out the user
                                        FirebaseAuth.getInstance().signOut();

                                        // Pass the updated password back to MainActivity
                                        Intent intent = new Intent();
                                        intent.putExtra("NEW_PASSWORD", newPassword);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else {
                                        // Failed to update the password
                                        Toast.makeText(ChangePass.this, "Failed to update password. Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Reauthentication failed, current password is incorrect
                        Toast.makeText(ChangePass.this, "Current password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(ChangePass.this, Profile.class);
        startActivity(intent);
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(ChangePass.this, ticket_page.class);
        startActivity(intent);
    }

    public void onHomeButtonClick(View view) {
        // Navigate to Home activity
        Intent intent = new Intent(ChangePass.this, HomeActivity.class);
        startActivity(intent);
    }
}
