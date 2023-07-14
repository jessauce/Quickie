package com.example.quickie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import java.io.File;
import androidx.core.content.FileProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Environment;


import java.io.IOException;

public class Profile extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImageView);

        TextView changeImageButton = findViewById(R.id.changeImageButton);
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        TextView aboutUsLayout = findViewById(R.id.aboutusEditText);
        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAboutUs();
            }
        });

        TextView signOutLayout = findViewById(R.id.signoutEditText);
        signOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });

    }


    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri photoUri;

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(Profile.this, ticket_page.class);
        startActivity(intent);
    }

    public void onHomeButtonClick(View view) {
        // Navigate to Home activity
        Intent intent = new Intent(Profile.this, HomeActivity.class);
        startActivity(intent);
    }
    public void navigateToPersonalData(View view) {
        // Navigate to PersonalData activity
        Intent intent = new Intent(Profile.this, PersonalData.class);
        startActivity(intent);
    }
    public void navigateToPaymentMethods(View view) {
        // Navigate to About Us activity
        Intent intent = new Intent(Profile.this, PaymentMethods.class);
        startActivity(intent);
    }

    public void navigateToChangePass(View view) {
        // Navigate to About Us activity
        Intent intent = new Intent(Profile.this, ChangePass.class);
        startActivity(intent);
    }
    public void navigateToAboutUs() {
        // Navigate to About Us activity
        Intent intent = new Intent(Profile.this, AboutUs.class);
        startActivity(intent);
    }

    public void navigateToMainActivity() {
        // Navigate to Main activity (sign out)
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }

}
