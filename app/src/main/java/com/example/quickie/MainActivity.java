package com.example.quickie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    TextView txtSignUp;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    String txtEmail, txtPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        progressBar = findViewById(R.id.progressBar2);
        btnSignIn = findViewById(R.id.btnSignIn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
    }

    private void signInUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.INVISIBLE);

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
                        Toast.makeText(MainActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        btnSignIn.setVisibility(View.VISIBLE);
                    }
                });
    }
}
