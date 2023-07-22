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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

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

                                        // Update the password in the userData collection on Firestore
                                        updatePasswordInFirestore(newPassword);

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

    private void updatePasswordInFirestore(String newPassword) {
        // Get a reference to the userData collection
        CollectionReference userDataRef = db.collection("userData");

        // Create a query to find the document with the matching email field
        userDataRef.whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // The query returned a document with the matching email field
                        String documentId = task.getResult().getDocuments().get(0).getId();

                        // Create a map to update the password field in the document
                        Map<String, Object> updateData = new HashMap<>();
                        updateData.put("password", newPassword);

                        // Update the password field in the document
                        userDataRef.document(documentId)
                                .update(updateData)
                                .addOnSuccessListener(aVoid -> {
                                    // Password field updated successfully in Firestore
                                    // You can add any additional logic here if needed
                                    Toast.makeText(ChangePass.this, "Password updated in Firestore successfully: " + newPassword, Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // Failed to update the password field in Firestore
                                    Toast.makeText(ChangePass.this, "Failed to update password in Firestore.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ChangePass.this, "Error updating password in Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // No document found with the matching email field
                        Toast.makeText(ChangePass.this, "User document not found in Firestore.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void navigateToProfile(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(ChangePass.this, Profile.class);
        startActivity(intent);
    }
}
