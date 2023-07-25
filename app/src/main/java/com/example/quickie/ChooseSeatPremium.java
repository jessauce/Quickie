package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.EventListener;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class ChooseSeatPremium extends AppCompatActivity {

    // Initialize the selectedSeats list here
    private List<String> selectedSeats = new ArrayList<>();
    private TextView selectedSeatsTextView;
    private TextView totalStandardTextView;
    private String selectedDate = "";
    private FirebaseFirestore db;
    private static final String COLLECTION_PATH = "CSBT/"; // Adjust this if needed

    private ListenerRegistration seatStatusListener;

    private String databaseItinerary; // Add this variable to store the database itinerary

    // Define the SeatStatusCallback interface here
    public interface SeatStatusCallback {
        void onSeatStatusReceived(String seatStatus);
        void onSeatStatusError(Exception e);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_premium);
        selectedSeatsTextView = findViewById(R.id.seatstakentext);
        totalStandardTextView = findViewById(R.id.totalstandardtext);

        db = FirebaseFirestore.getInstance();
        selectedDate = getIntent().getStringExtra("selectedDate");

        // Retrieve the selected itinerary from the intent
        String selectedItinerary = getIntent().getStringExtra("selectedItinerary");

        // Convert selectedItinerary to databaseItinerary using a switch statement
        switch (selectedItinerary) {
            case "OSLB":
                databaseItinerary = "CSBT-OSLOB";
                break;
            case "ALCOY":
                databaseItinerary = "CSBT-ALCOY";
                break;
            case "ARGAO":
                databaseItinerary = "CSBT-ARGAO";
                break;
            case "NAGA":
                databaseItinerary = "CSBT-NAGA";
                break;
            case "SANTNDR":
                databaseItinerary = "CSBT-SANTANDER";
                break;
            default:
                // If the selectedItinerary doesn't match any case, set a default value (or handle it accordingly)
                databaseItinerary = "";
                break;
        }

        // Add log and toast for selectedDate here
        Log.d("ChooseSeat", "Selected Date: " + selectedDate);
        Log.d("ChooseSeat", "Selected Itinerary: " + selectedItinerary);
        Log.d("ChooseSeat", "Database Itinerary: " + databaseItinerary);
        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Selected Itinerary: " + selectedItinerary, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Database Itinerary: " + databaseItinerary, Toast.LENGTH_SHORT).show();

        // Now you can use the databaseItinerary variable to construct the Firestore document path dynamically
        String documentPath = COLLECTION_PATH + databaseItinerary + "/rideDate/" + selectedDate + "/morningTrip/Premium";

        // Set up a real-time listener for the document
        db.document(documentPath).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("ChooseSeatDeluxe", "Error fetching data: " + e.getMessage());
                    Toast.makeText(ChooseSeatPremium.this, "Data retrieval failed. Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (document != null && document.exists()) {
                    // DocumentSnapshot contains the seat status data for the selected date, morning trip, and Deluxe bus
                    // You can access the seat status for each seat using document.getString("seatA1"), document.getString("seatA2"), etc.
                    // The seat status should be either "0" (available) or "1" (taken)
                    // Now you can update the UI to display the seat status
                    updateSeatStatus(document, "A1", R.id.A1);
                    updateSeatStatus(document, "B1", R.id.B1);
                    updateSeatStatus(document, "C1", R.id.C1);
                    updateSeatStatus(document, "D1", R.id.D1);
                    updateSeatStatus(document, "A2", R.id.A2);
                    updateSeatStatus(document, "B2", R.id.B2);
                    updateSeatStatus(document, "C2", R.id.C2);
                    updateSeatStatus(document, "D2", R.id.D2);
                    updateSeatStatus(document, "A3", R.id.A3);
                    updateSeatStatus(document, "B3", R.id.B3);
                    updateSeatStatus(document, "C3", R.id.C3);
                    updateSeatStatus(document, "D3", R.id.D3);
                    updateSeatStatus(document, "A4", R.id.A4);
                    updateSeatStatus(document, "B4", R.id.B4);
                    updateSeatStatus(document, "C4", R.id.C4);
                    updateSeatStatus(document, "D4", R.id.D4);
                    updateSeatStatus(document, "A5", R.id.A5);
                    updateSeatStatus(document, "B5", R.id.B5);
                    updateSeatStatus(document, "C5", R.id.C5);
                    updateSeatStatus(document, "D5", R.id.D5);
                    updateSeatStatus(document, "A6", R.id.A6);
                    updateSeatStatus(document, "B6", R.id.B6);
                    updateSeatStatus(document, "C6", R.id.C6);
                    updateSeatStatus(document, "D6", R.id.D6);
                    updateSeatStatus(document, "A7", R.id.A7);
                    updateSeatStatus(document, "B7", R.id.B7);
                    updateSeatStatus(document, "C7", R.id.C7);
                    updateSeatStatus(document, "D7", R.id.D7);
                    updateSeatStatus(document, "A8", R.id.A8);
                    updateSeatStatus(document, "B8", R.id.B8);
                    updateSeatStatus(document, "C8", R.id.C8);
                    updateSeatStatus(document, "D8", R.id.D8);
                    // Add more seat updates here for other seats
                    // ...

                    // Calculate the total price of the selected seats
                    int totalPrice = selectedSeats.size();
                    totalStandardTextView.setText("Total Price: ₱" + totalPrice);

                    // Show a toast message indicating successful data retrieval
                    Toast.makeText(ChooseSeatPremium.this, "Data retrieval successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Document doesn't exist or an error occurred while fetching data
                    // Show a toast message indicating data retrieval failure
                    Toast.makeText(ChooseSeatPremium.this, "Data retrieval failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateSeatStatusInDatabase(String seatId, String status) {
        if (databaseItinerary.isEmpty()) {
            Log.e("ChooseSeat", "Error: Database itinerary is not set.");
            return;
        }

        String documentPath = COLLECTION_PATH + databaseItinerary + "/rideDate/" + selectedDate + "/morningTrip/Premium";
        DocumentReference seatDocumentRef = db.document(documentPath);

        seatDocumentRef
                .update(seatId, status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("ChooseSeat", "Seat status updated in the database.");
                        } else {
                            Log.e("ChooseSeat", "Error updating seat status in the database: " + task.getException());
                            Toast.makeText(ChooseSeatPremium.this, "Failed to update seat status.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void updateSeatStatus(DocumentSnapshot document, String seatId, int seatViewId) {
        String seatStatus = document.getString(seatId);
        ImageView seatImageView = findViewById(seatViewId);

        // Assuming you have two drawable resources for available and taken seats
        int availableSeatResource = R.drawable.availableseats;
        int takenSeatResource = R.drawable.unavailableseats; // Update this line to use the correct resource ID

        if (seatStatus != null && seatStatus.equals("1")) {
            // Seat is taken
            seatImageView.setImageResource(takenSeatResource);
        } else {
            // Seat is available
            seatImageView.setImageResource(availableSeatResource);
        }
    }

    public void navigateToPickBus(View view) {
        // Navigate to PickBus activity
        Intent intent = new Intent(ChooseSeatPremium.this, PickBus.class);
        startActivity(intent);
    }

    public void onSeatClick(View view) {
        final ImageView seatImageView = (ImageView) view;
        final String seatId = seatImageView.getResources().getResourceEntryName(seatImageView.getId());

        // Retrieve the current status of the seat from the database
        getSeatStatusFromDatabase(seatId, new ChooseSeat.SeatStatusCallback() {
            @Override
            public void onSeatStatusReceived(String seatStatus) {
                if (seatStatus == null) {
                    // Seat status could not be retrieved, show an error message
                    Toast.makeText(ChooseSeatPremium.this, "Failed to get seat status. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (seatStatus.equals("1")) {
                    // Seat is taken, notify the user
                    Toast.makeText(ChooseSeatPremium.this, "This seat is already taken.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Seat status is "0" (available), update the seat status locally
                if (selectedSeats.contains(seatId)) {
                    // Seat was already selected, remove it from the list and update its color
                    selectedSeats.remove(seatId);
                    seatImageView.setImageResource(R.drawable.availableseats);
                } else {
                    // Seat was not selected, add it to the list and update its color
                    selectedSeats.add(seatId);
                    seatImageView.setImageResource(R.drawable.selectedseat);
                }

                // Update the text view with the selected seats and total price
                updateSelectedSeatsTextView();
            }

            @Override
            public void onSeatStatusError(Exception e) {
                // Handle any errors that occurred while fetching the seat status
                Log.e("ChooseSeat", "Error fetching seat status: " + e.getMessage());
                Toast.makeText(ChooseSeatPremium.this, "Failed to get seat status. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSelectedSeatsTextView() {
        StringBuilder seatsText = new StringBuilder();
        for (String seat : selectedSeats) {
            seatsText.append(seat).append(", ");
        }

        if (seatsText.length() > 2) {
            seatsText.setLength(seatsText.length() - 2); // Remove the last comma and space
        }

        selectedSeatsTextView.setText("Standard Seats Taken: " + seatsText);

        // Recalculate and update the total price
        int totalPrice = selectedSeats.size();
        totalStandardTextView.setText("Total Price: ₱" + totalPrice);
    }

    public void navigateToTicketPage(View view) {
        // Check if any seats are selected before proceeding
        if (selectedSeats.isEmpty()) {
            Toast.makeText(this, "Please select seats before checking out.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the seat status in the Firestore database for each selected seat
        for (String seatId : selectedSeats) {
            updateSeatStatusInDatabase(seatId, "1");
        }

        // Implement the logic to create a ticket and pass the necessary data to the TicketPage activity
        // For example, you can pass the selected seats, total price, etc., as extras in the Intent.
        // Then, start the TicketPage activity.
        Intent intent = new Intent(ChooseSeatPremium.this, ticket_page.class);
        // Add extras to the intent if needed
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the real-time listener when the activity is destroyed to avoid memory leaks
        if (seatStatusListener != null) {
            seatStatusListener.remove();
        }
    }

    // Adjust the method signature to accept a SeatStatusCallback parameter
    private void getSeatStatusFromDatabase(final String seatId, final ChooseSeat.SeatStatusCallback callback) {
        String documentPath = COLLECTION_PATH + databaseItinerary + "/rideDate/" + selectedDate + "/morningTrip/Premium";
        DocumentReference seatDocumentRef = db.document(documentPath);

        seatDocumentRef
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String seatStatus = documentSnapshot.getString(seatId);
                            if (seatStatus != null) {
                                // Seat status found, call the appropriate method of the callback interface
                                callback.onSeatStatusReceived(seatStatus);
                            } else {
                                // Seat status not found, call the appropriate method of the callback interface with an error
                                callback.onSeatStatusError(new Exception("Seat status not found for seat: " + seatId));
                            }
                        } else {
                            // Document not found, call the appropriate method of the callback interface with an error
                            callback.onSeatStatusError(new Exception("Document not found for seat: " + seatId));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while fetching seat status, call the appropriate method of the callback interface with an error
                        callback.onSeatStatusError(e);
                    }
                });
    }

}
