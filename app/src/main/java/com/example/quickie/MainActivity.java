package com.example.quickie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    TextView txtSignUp;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    TextView txtForgotPassword;
    TextView countdownTimer;
    TextView txtSendViaSms;
    TextView txtSendViaEmail;
    TextView otpExpiresInText;

    String txtEmail, txtPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private int wrongPasswordCounter = 0;
    private CountDownTimer countDownTimer;
    private final long countdownDuration = 60000; // Countdown duration in milliseconds (1 minute)
    private FirebaseFirestore db;
    private String phoneNumber; // Declare phoneNumber variable
    private String otp; // Declare otp variable

    private boolean sendViaSmsClicked = false;
    private boolean sendViaEmailClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        TextView termsTextView = findViewById(R.id.txtSignUp);
        TextView textView = findViewById(R.id.txtSignUp);
        String text = "Don't have an account? Sign up here!";
        SpannableString ss = new SpannableString(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ss.setSpan(underlineSpan, 23, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);

        txtSendViaSms = findViewById(R.id.txtSendViaSms);
        txtSendViaSms.setVisibility(View.INVISIBLE);

        txtSendViaEmail = findViewById(R.id.txtSendViaEmail);
        txtSendViaEmail.setVisibility(View.INVISIBLE);

        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        progressBar = findViewById(R.id.progressBar2);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtForgotPassword = findViewById(R.id.forgotPassword);
        countdownTimer = findViewById(R.id.countdownTimer);
        otpExpiresInText = findViewById(R.id.otpExpiresInText);
        otpExpiresInText.setVisibility(View.INVISIBLE); // Set the visibility to invisible initially

        progressBar.setVisibility(View.INVISIBLE);
        txtForgotPassword.setVisibility(View.INVISIBLE);
        countdownTimer.setVisibility(View.INVISIBLE);

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
                String email = edtEmail.getText().toString().trim();

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    db.collection("userData")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                        phoneNumber = document.getString("phone"); // Assign the phoneNumber value
                                        otp = generateOTP(); // Generate the OTP

                                        // Set the flags to false initially
                                        sendViaSmsClicked = false;
                                        sendViaEmailClicked = false;

                                        // Show the two options for sending OTP (via SMS and via Email)
                                        txtForgotPassword.setVisibility(View.INVISIBLE);
                                        txtSendViaSms.setVisibility(View.VISIBLE);
                                        txtSendViaEmail.setVisibility(View.VISIBLE);

                                        // Don't show the toast message here

                                    } else {
                                        // No user found with the entered email
                                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        txtSendViaSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the flag to true and the other to false
                sendViaSmsClicked = true;
                sendViaEmailClicked = false;

                // Send the OTP via SMS and show countdown timer
                sendMessageWithOTP(phoneNumber, otp);
                startCountdown();

                // Set visibility of "Send via SMS" and "Send via Email" TextViews
                txtSendViaSms.setVisibility(View.VISIBLE);
                txtSendViaEmail.setVisibility(View.INVISIBLE);
            }
        });

        txtSendViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the flag to true and the other to false
                sendViaEmailClicked = true;
                sendViaSmsClicked = false;

                // Add the functionality to send OTP via Email here
                // For now, let's just show a Toast message indicating this feature is not yet implemented
                Toast.makeText(MainActivity.this, "Sending OTP via Email (Not implemented yet)", Toast.LENGTH_SHORT).show();

                // Set visibility of "Send via SMS" and "Send via Email" TextViews
                txtSendViaEmail.setVisibility(View.VISIBLE);
                txtSendViaSms.setVisibility(View.INVISIBLE);
            }
        });
    }


    private void startCountdown() {
        // Start the countdown timer and show the "OTP expires in" text only if either sendViaSmsClicked or sendViaEmailClicked is true
        if (sendViaSmsClicked || sendViaEmailClicked) {
            countdownTimer.setVisibility(View.VISIBLE);
            otpExpiresInText.setVisibility(View.VISIBLE);
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
                    txtSendViaSms.setVisibility(View.INVISIBLE);
                    txtSendViaEmail.setVisibility(View.INVISIBLE);
                    wrongPasswordCounter = 0; // Reset the wrong password counter
                }
            };
            countDownTimer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
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



    private String generateOTP() {
        // Generate a 6-digit OTP
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    private void sendMessageWithOTP(String phoneNumber, String otp) {
        String message = "The OTP for forgot password login access is " + otp + ". Reminder: Never share your OTP with anyone.";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Failed to send OTP via SMS.", Toast.LENGTH_SHORT).show();
        }
    }
}