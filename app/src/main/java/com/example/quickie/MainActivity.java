package com.example.quickie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    TextView txtSignUp;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    TextView txtForgotPassword;
    TextView countdownTimer;
    TextView otpExpiresInText; // Added reference to the "OTP expires in:" TextView
    String txtEmail, txtPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private int wrongPasswordCounter = 0;
    private CountDownTimer countDownTimer;
    private final long countdownDuration = 60000; // Countdown duration in milliseconds (1 minute)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView termsTextView = findViewById(R.id.txtSignUp);
        TextView textView = findViewById(R.id.txtSignUp);
        String text = "Don't have an account? Sign up here!";
        SpannableString ss = new SpannableString(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ss.setSpan(underlineSpan, 23, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);

        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        progressBar = findViewById(R.id.progressBar2);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtForgotPassword = findViewById(R.id.forgotPassword);
        countdownTimer = findViewById(R.id.countdownTimer);
        otpExpiresInText = findViewById(R.id.otpExpiresInText); // Initialize the "OTP expires in:" TextView

        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.INVISIBLE);
        txtForgotPassword.setVisibility(View.INVISIBLE);
        countdownTimer.setVisibility(View.INVISIBLE);
        otpExpiresInText.setVisibility(View.INVISIBLE); // Set the visibility to invisible initially

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = edtEmail.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();

                if (!txtEmail.isEmpty()) {
                    if (txtEmail.matches(emailPattern)) {
                        if (!txtPassword.isEmpty()) {
                            signInUser();
                        } else {
                            edtPassword.setError("Password Field can't be empty");
                        }
                    } else {
                        edtEmail.setError("Enter a valid Email Address");
                    }
                } else {
                    edtEmail.setError("Email Field can't be empty");
                }
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a message to the user
                Toast.makeText(MainActivity.this, "We have sent a 6-digit OTP to your email.", Toast.LENGTH_SHORT).show();
                startCountdown();
                txtForgotPassword.setVisibility(View.INVISIBLE);
                countdownTimer.setVisibility(View.VISIBLE);
                otpExpiresInText.setVisibility(View.VISIBLE); // Set the "OTP expires in:" TextView visible
            }
        });
    }

    private void signInUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setEnabled(false);
        btnSignIn.setTextColor(getResources().getColor(R.color.button_disabled_color));
        btnSignIn.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));

        mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage;
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            errorMessage = "User does not exist.";
                        } else {
                            errorMessage = "Error - " + e.getMessage();
                        }

                        if (wrongPasswordCounter >= 14) {
                            errorMessage = "Too many failed attempts. Try again later.";
                        }

                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        btnSignIn.setEnabled(true);
                        btnSignIn.setTextColor(getResources().getColor(R.color.white));
                        btnSignIn.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));

                        wrongPasswordCounter++;

                        if (wrongPasswordCounter >= 3) {
                            txtForgotPassword.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(countdownDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String countdownText = String.format("%02d:%02d", minutes, seconds);
                countdownTimer.setText(countdownText);

                // Change the text color to red when 30 seconds or less are remaining
                if (millisUntilFinished <= 30000) {
                    countdownTimer.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onFinish() {
                countdownTimer.setText("00:00");
                txtForgotPassword.setVisibility(View.VISIBLE);
                countdownTimer.setVisibility(View.INVISIBLE);
                otpExpiresInText.setVisibility(View.INVISIBLE); // Set the "OTP expires in:" TextView invisible
                wrongPasswordCounter = 0; // Reset the wrong password counter
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
