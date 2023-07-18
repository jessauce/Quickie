package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class PersonalData extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;

    public void navigateToProfile(View view) {
        Intent intent = new Intent(PersonalData.this, Profile.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        profileImageView = findViewById(R.id.profileImageView);

        TextView changeImageButton = findViewById(R.id.changeImageButton);
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(PersonalData.this, ticket_page.class);
        startActivity(intent);
    }

    public void onHomeButtonClick(View view) {
        // Navigate to Home activity
        Intent intent = new Intent(PersonalData.this, HomeActivity.class);
        startActivity(intent);
    }
    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(PersonalData.this, Profile.class);
        startActivity(intent);
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri photoUri;

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
}