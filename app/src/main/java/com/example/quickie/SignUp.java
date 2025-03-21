package com.example.quickie;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.ColorStateList;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ColorStateList signUpButtonTextColor;
    private ColorStateList signUpButtonBackgroundTint;

    private boolean isHintDisplayed = true;
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView termsTextView = findViewById(R.id.signIn2TextView);
        TextView textView = findViewById(R.id.signIn2TextView);
        String text = "Already have an account? Sign in here!";
        SpannableString ss = new SpannableString(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ss.setSpan(underlineSpan, 25, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);


        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);
        TextView txtSignIn = findViewById(R.id.signIn2TextView);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUpButtonTextColor = signUpButton.getTextColors();
        signUpButtonBackgroundTint = signUpButton.getBackgroundTintList();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid Email!");
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    SignUpUser(firstName, lastName, email, phone, username, password);
                }
            }
        });


        // Set up the initial visibility for the "+63" prefix and hint text
        phoneEditText.setText("");
        phoneEditText.setHint("Phone Number");
        isHintDisplayed = true;

        // Create a custom TextWatcher for the EditText
        phoneEditText.addTextChangedListener(new CustomTextWatcher(phoneEditText));

        // Set up the onFocusChangeListener for the EditText
        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // When the EditText gains focus, show the "+63" prefix
                    if (phoneEditText.getText().toString().isEmpty()) {
                        phoneEditText.setText("+63");
                        // Set the cursor position after the "+63" prefix
                        phoneEditText.setSelection(phoneEditText.getText().length());
                    }
                    isHintDisplayed = false;
                } else {
                    // When the EditText loses focus
                    if (phoneEditText.getText().toString().equals("+63")) {
                        // If the user only typed "+63" without any additional digits, hide the prefix
                        phoneEditText.setText("");
                        isHintDisplayed = true;
                    } else if (phoneEditText.getText().toString().isEmpty()) {
                        // If the EditText is empty, show the hint text
                        phoneEditText.setHint("Phone Number");
                        isHintDisplayed = true;
                    }
                }
            }
        });
    }

    private class CustomTextWatcher implements TextWatcher {
        private final EditText editText;

        CustomTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isUpdating) {
                // Skip the event to prevent recursive calls
                return;
            }

            // If the user tries to delete the "+63" prefix, re-insert it
            if (!s.toString().startsWith("+63")) {
                isUpdating = true;
                editText.setText("+63");
                // Set the cursor position after the "+63" prefix
                editText.setSelection(editText.getText().length());
            }

            isUpdating = false;
        }

        @Override
        public void afterTextChanged(Editable s) {
            // If the EditText contains only "+63", treat it as empty
            if (s.toString().equals("+63") && !isUpdating) {
                editText.setText("");
            }
        }
    }


    private void SignUpUser(String firstName, String lastName, String email, String phone, String username, String password) {
        progressBar.setVisibility(View.VISIBLE);
        signUpButton.setEnabled(false);
        signUpButton.setTextColor(getResources().getColor(R.color.button_disabled_color));
        signUpButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_enabled_color)));

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("email", email);
                user.put("password", password);
                user.put("phone", phone);
                user.put("username", username);

                db.collection("userData")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                signUpButton.setEnabled(true);
                                signUpButton.setTextColor(getResources().getColor(R.color.white));
                                signUpButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                signUpButton.setEnabled(true);
                signUpButton.setTextColor(getResources().getColor(R.color.white));
                signUpButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));
            }
        });
    }
}