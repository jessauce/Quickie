package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseSeatExpress extends AppCompatActivity {

    // Initialize the selectedSeats list here
    private List<String> selectedSeats = new ArrayList<>();
    private TextView selectedSeatsTextView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_express);

        selectedSeatsTextView = findViewById(R.id.seatstakentext);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void navigateToPickBus(View view) {
        Intent intent = new Intent(ChooseSeatExpress.this, PickBus.class);
        startActivity(intent);
    }

    public void onSeatClick(View view) {
        ImageView seatImageView = (ImageView) view;
        String seatId = seatImageView.getResources().getResourceEntryName(seatImageView.getId());

        if (selectedSeats.contains(seatId)) {
            // Seat was already selected, remove it from the list and update its color
            selectedSeats.remove(seatId);
            seatImageView.setImageResource(R.drawable.availableseats);
        } else {
            // Seat was not selected, add it to the list and update its color
            selectedSeats.add(seatId);
            seatImageView.setImageResource(R.drawable.selectedseat);
        }

        // Update the text view with the selected seats
        updateSelectedSeatsTextView();
    }

    private void updateSelectedSeatsTextView() {
        StringBuilder seatsText = new StringBuilder();
        for (String seat : selectedSeats) {
            seatsText.append(seat).append(", ");
        }

        if (seatsText.length() > 2) {
            seatsText.setLength(seatsText.length() - 2); // Remove the last comma and space
        }

        selectedSeatsTextView.setText("Express Seats Taken: " + seatsText);
    }

    public void navigateToGcash1Page(View view) {
        // Build the AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("You are about to be redirected to Gcash. Do you want to proceed?");
        alertDialogBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Show the progress bar to indicate loading
                progressBar.setVisibility(View.VISIBLE);

                // Simulate a delay using a Handler (you can remove this in production)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the progress bar
                        progressBar.setVisibility(View.GONE);

                        // Navigate to Gcash1 after the delay
                        Intent intent = new Intent(ChooseSeatExpress.this, Gcash1.class);
                        startActivity(intent);
                    }
                }, 2000); // Adjust the delay time as needed (here, we use 2000 milliseconds or 2 seconds)
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked Cancel, do nothing (dismiss the dialog)
                dialogInterface.dismiss();
            }
        });

        // Show the AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
