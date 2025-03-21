package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.widget.Button;
import java.text.SimpleDateFormat; // Import the SimpleDateFormat class
import java.util.Date;

public class PickBus extends AppCompatActivity {

    private String selectedDate = "";
    private String selectedLocationAbbreviation = "";

    public void navigateToHomeActivity(View view) {
        Intent intent = new Intent(PickBus.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_bus);


        // Get the selected destination and its corresponding price from the intent
        String selectedDestination = getIntent().getStringExtra("selectedDestination");
        int price = getIntent().getIntExtra("price", 0);
        int standardPrice = getIntent().getIntExtra("standardPrice", 0);
        int deluxePrice = getIntent().getIntExtra("deluxePrice", 0);
        int premiumPrice = getIntent().getIntExtra("premiumPrice", 0);

        selectedDate = getIntent().getStringExtra("selectedDate");

        updateSelectedDate();

        if (selectedDestination != null && !selectedDestination.isEmpty()) {
            TextView locations = findViewById(R.id.locations);
            String abbreviation = getAbbreviationForDestination(selectedDestination);
            locations.setText("CEB → " + abbreviation);

            TextView priceTextView = findViewById(R.id.expresspricestandard);
            priceTextView.setText("₱" + price);

            // Set the prices for the standard, deluxe, and premium buses based on the selected destination
            TextView standardPriceTextView = findViewById(R.id.pricestandard);
            standardPriceTextView.setText("₱" + standardPrice);

            TextView deluxePriceTextView = findViewById(R.id.pricesdeluxe);
            deluxePriceTextView.setText("₱" + deluxePrice);

            TextView premiumPriceTextView = findViewById(R.id.pricespremium);
            premiumPriceTextView.setText("₱" + premiumPrice);
        }
    }

    private void updateSelectedDate() {
        TextView dateTextView = findViewById(R.id.expressStandarddate);
        dateTextView.setText(selectedDate);

        TextView standardDateTextView = findViewById(R.id.Standarddate);
        standardDateTextView.setText(selectedDate);

        TextView deluxeDateTextView = findViewById(R.id.deluxedate);
        deluxeDateTextView.setText(selectedDate);

        TextView premiumDateTextView = findViewById(R.id.premiumdate);
        premiumDateTextView.setText(selectedDate);
    }

    public void navigateToChooseSeat(View view) {
        Intent intent = new Intent(PickBus.this, ChooseSeat.class);
        intent.putExtra("selectedDate", selectedDate);

        // Get the selected destination from the TextView
        TextView locations = findViewById(R.id.locations);
        String selectedItinerary = locations.getText().toString().replace("CEB → ", "");
        intent.putExtra("selectedItinerary", selectedItinerary);

        // Get the price from the TextView and pass it as an extra
        TextView priceTextView = findViewById(R.id.pricestandard);
        String priceString = priceTextView.getText().toString().replace("₱", ""); // Remove the "₱" symbol
        int price = Integer.parseInt(priceString); // Convert the price to an integer
        intent.putExtra("selectedPrice", price);

        startActivity(intent);
    }
    public void navigateToChooseSeatDeluxe(View view) {
        Intent intent = new Intent(PickBus.this, ChooseSeatDeluxe.class);
        intent.putExtra("selectedDate", selectedDate);

        // Get the selected destination from the TextView
        TextView locations = findViewById(R.id.locations);
        String selectedItinerary = locations.getText().toString().replace("CEB → ", "");
        intent.putExtra("selectedItinerary", selectedItinerary);

        // Get the price from the TextView and pass it as an extra
        TextView priceTextView = findViewById(R.id.pricesdeluxe);
        String priceString = priceTextView.getText().toString().replace("₱", ""); // Remove the "₱" symbol
        int price = Integer.parseInt(priceString); // Convert the price to an integer
        intent.putExtra("selectedPrice", price);

        startActivity(intent);
    }
    public void navigateToChooseSeatPremium(View view) {
        Intent intent = new Intent(PickBus.this, ChooseSeatPremium.class);
        intent.putExtra("selectedDate", selectedDate);

        // Get the selected destination from the TextView
        TextView locations = findViewById(R.id.locations);
        String selectedItinerary = locations.getText().toString().replace("CEB → ", "");
        intent.putExtra("selectedItinerary", selectedItinerary);

        // Get the price from the TextView and pass it as an extra
        TextView priceTextView = findViewById(R.id.pricespremium);
        String priceString = priceTextView.getText().toString().replace("₱", ""); // Remove the "₱" symbol
        int price = Integer.parseInt(priceString); // Convert the price to an integer
        intent.putExtra("selectedPrice", price);

        startActivity(intent);
    }

    public void navigateToChooseSeatExpress(View view) {
        Intent intent = new Intent(PickBus.this, ChooseSeatExpress.class);
        intent.putExtra("selectedDate", selectedDate);

        // Get the selected destination from the TextView
        TextView locations = findViewById(R.id.locations);
        String selectedItinerary = locations.getText().toString().replace("CEB → ", "");
        intent.putExtra("selectedItinerary", selectedItinerary);

        // Get the price from the TextView and pass it as an extra
        TextView priceTextView = findViewById(R.id.expresspricestandard);
        String priceString = priceTextView.getText().toString().replace("₱", ""); // Remove the "₱" symbol
        int price = Integer.parseInt(priceString); // Convert the price to an integer
        intent.putExtra("selectedPrice", price);

        startActivity(intent);
    }
    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(PickBus.this, ticket_page.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(PickBus.this, Profile.class);
        startActivity(intent);
    }

    public String getAbbreviationForDestination(String destination) {
        String abbreviation = "";
        switch (destination) {
            case "Naga Terminal":
                abbreviation = "NAGA";
                break;
            case "San Fernando Terminal":
                abbreviation = "SAN F";
                break;
            case "Carcar Terminal":
                abbreviation = "CARCAR";
                break;
            case "Argao Terminal":
                abbreviation = "ARGAO";
                break;
            case "Dalaguete Terminal":
                abbreviation = "DLGT";
                break;
            case "Alcoy Terminal":
                abbreviation = "ALCOY";
                break;
            case "Boljoon Terminal":
                abbreviation = "BOLJN";
                break;
            case "Osblob Terminal":
                abbreviation = "OSLB";
                break;
            case "Santander Terminal":
                abbreviation = "SANTNDR";
                break;
            default:
                abbreviation = destination;
                break;
        }
        return abbreviation;
    }
}