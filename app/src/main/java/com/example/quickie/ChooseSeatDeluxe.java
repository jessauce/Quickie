package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChooseSeatDeluxe extends AppCompatActivity {

    // Initialize the selectedSeats list here
    private List<String> selectedSeats = new ArrayList<>();
    private TextView selectedSeatsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_deluxe);
        selectedSeatsTextView = findViewById(R.id.seatstakentext);
    }

    public void navigateToPickBus(View view) {
        // Navigate to About Us activity
        Intent intent = new Intent(ChooseSeatDeluxe.this, PickBus.class);
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

        selectedSeatsTextView.setText("Deluxe Seats Taken: " + seatsText);
    }
}
