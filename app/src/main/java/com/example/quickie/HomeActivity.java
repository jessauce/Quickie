package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import com.google.android.gms.maps.model.LatLng;

public class HomeActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    Button bottomsheet;
    Button pickBusButton;
    ImageView pinFromImageView;
    ImageView pinToImageView;
    MapFragment mapFragment;

    public AutoCompleteTextView originComboBox;
    public AutoCompleteTextView destinationComboBox;

    private LatLng fromLatLng;
    private LatLng toLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        // Set click listeners for the buttons
        String[] originItems = {"Cebu South Bus Terminal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, originItems);
        originComboBox = findViewById(R.id.originComboBox);
        originComboBox.setAdapter(adapter);
        originComboBox.setThreshold(0); // Set threshold to 0 for immediate dropdown appearance

        String[] destinationItems = {"Naga Terminal", "San Fernando Terminal", "Carcar Terminal", "Argao Terminal", "Dalaguete Terminal", "Alcoy Terminal", "Boljoon Terminal", "Osblob Terminal", "Santander Terminal"};
        ArrayAdapter<String> dadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, destinationItems);
        destinationComboBox = findViewById(R.id.destinationComboBox);
        destinationComboBox.setAdapter(dadapter);
        destinationComboBox.setThreshold(0); // Set threshold to 0 for immediate dropdown appearance

        pinFromImageView = findViewById(R.id.pinFromImageView);
        pinToImageView = findViewById(R.id.pinToImageView);

        pinFromImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOrigin = originComboBox.getText().toString();
                if (!selectedOrigin.isEmpty() && selectedOrigin.equals("Cebu South Bus Terminal")) {
                    LatLng fromLatLng = new LatLng(10.2983, 123.8934);
                    mapFragment.pinLocation(fromLatLng, selectedOrigin);
                } else {
                    Toast.makeText(HomeActivity.this, "Please select an origin location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pinToImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDestination = destinationComboBox.getText().toString();
                if (!selectedDestination.isEmpty() && selectedDestination.equals("Naga Terminal")) {
                    LatLng toLatLng = new LatLng(10.2100, 123.7580);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("San Fernando Terminal")) {
                    LatLng toLatLng = new LatLng(10.1667, 123.7000);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Carcar Terminal")) {
                    LatLng toLatLng = new LatLng(10.1067, 123.6403);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Argao Terminal")) {
                    LatLng toLatLng = new LatLng(10.1190, 123.6069);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Dalaguete Terminal")) {
                    LatLng toLatLng = new LatLng(9.7649, 123.5902);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Alcoy Terminal")) {
                    LatLng toLatLng = new LatLng(9.7583, 123.6033);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Boljoon Terminal")) {
                    LatLng toLatLng = new LatLng(9.6240, 123.5134);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Osblob Terminal")) {
                    LatLng toLatLng = new LatLng(9.5137, 123.4056);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Santander Terminal")) {
                    LatLng toLatLng = new LatLng(9.5047, 123.2835);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else {
                    Toast.makeText(HomeActivity.this, "Please select a destination location", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bottomsheet = findViewById(R.id.continueButton);
        bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        mapFragment = (MapFragment) fragment;
        mapFragment.setHomeActivity(this);
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom);

        //LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
        //LinearLayout shareLayout = dialog.findViewById(R.id.layoutShare);
        pickBusButton = dialog.findViewById(R.id.pickbusButton);
        pickBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPickBusActivity();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void navigateToPickBusActivity() {
        Intent intent = new Intent(HomeActivity.this, PickBus.class);
        startActivity(intent);
    }

    public void onTicketsButtonClick(View view) {
        // Navigate to Tickets activity
        Intent intent = new Intent(HomeActivity.this, ticket_page.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        // Navigate to Profile activity
        Intent intent = new Intent(HomeActivity.this, Profile.class);
        startActivity(intent);
    }

    public void setFromLatLng(LatLng fromLatLng) {
        this.fromLatLng = fromLatLng;
    }

    public void setToLatLng(LatLng toLatLng) {
        this.toLatLng = toLatLng;
    }
}