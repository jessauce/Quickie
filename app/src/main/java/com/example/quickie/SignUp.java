package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
                        || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user with email and password
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, task -> {
                                if (task.isSuccessful()) {
                                    // User registration successful
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();

                                    // Create a Map to store the user data
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("firstName", firstName);
                                    userData.put("lastName", lastName);
                                    userData.put("email", email);
                                    userData.put("phone", phone);
                                    userData.put("username", username);

                                    // Save the user data in Firestore
                                    db.collection("Users").document(userId)
                                            .set(userData)
                                            .addOnSuccessListener(aVoid -> {
                                                // User data successfully saved in Firestore
                                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                intent.putExtra("username", username);
                                                intent.putExtra("password", password);
                                                startActivity(intent);
                                                finish(); // Optional: Finish the sign-up activity
                                            })
                                            .addOnFailureListener(e -> {
                                                // An error occurred while saving user data
                                                Toast.makeText(SignUp.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    // User registration failed
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        // User with the given email already exists
                                        Toast.makeText(SignUp.this, "An account with this email already exists", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        // Invalid email format
                                        Toast.makeText(SignUp.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        // Other error occurred during registration
                                        Toast.makeText(SignUp.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
