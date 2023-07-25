package com.example.quickie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.DatePicker;

import java.util.Calendar;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import com.google.android.gms.maps.model.LatLng;

public class HomeActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePickerDialog timePickerDialog;
    private Button timeButton;

    Button bottomsheet;
    Button pickBusButton;
    ImageView pinFromImageView;
    ImageView pinToImageView;
    MapFragment mapFragment;
    private int regularPassengerCount = 0;
    private int discountPassengerCount = 0;


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

        String[] destinationItems = {"Naga Terminal", "San Fernando Terminal", "Carcar Terminal", "Argao Terminal", "Dalaguete Terminal", "Alcoy Terminal", "Boljoon Terminal", "Oslob Terminal", "Santander Terminal"};
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
                    LatLng toLatLng = new LatLng(9.9419, 123.6033);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Dalaguete Terminal")) {
                    LatLng toLatLng = new LatLng(9.7776, 123.5074);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Alcoy Terminal")) {
                    LatLng toLatLng = new LatLng(9.7110, 123.4801);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Boljoon Terminal")) {
                    LatLng toLatLng = new LatLng(9.6259, 123.4484);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Oslob Terminal")) {
                    LatLng toLatLng = new LatLng(9.5137, 123.4056);
                    mapFragment.pinLocation(toLatLng, selectedDestination);
                } else if (!selectedDestination.isEmpty() && selectedDestination.equals("Santander Terminal")) {
                    LatLng toLatLng = new LatLng(9.4443, 123.3414);
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

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom);

        Button datePickerButton = dialog.findViewById(R.id.datePickerButton);
        initDatePicker(datePickerButton);

        Button timeButton = dialog.findViewById(R.id.timeButton);
        initTimePicker(timeButton);

        pickBusButton = dialog.findViewById(R.id.pickbusButton);

        TextView regularCountTextView = dialog.findViewById(R.id.regularCountTextView);
        TextView discountCountTextView = dialog.findViewById(R.id.discountCountTextView);

        regularCountTextView.setText(String.valueOf(regularPassengerCount));
        discountCountTextView.setText(String.valueOf(discountPassengerCount));

        Button regularMinusButton = dialog.findViewById(R.id.regularMinusButton);
        Button regularPlusButton = dialog.findViewById(R.id.regularPlusButton);
        Button discountMinusButton = dialog.findViewById(R.id.discountMinusButton);
        Button discountPlusButton = dialog.findViewById(R.id.discountPlusButton);

        regularMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regularPassengerCount > 0) {
                    regularPassengerCount--;
                    regularCountTextView.setText(String.valueOf(regularPassengerCount));
                }
            }
        });

        regularPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regularPassengerCount++;
                regularCountTextView.setText(String.valueOf(regularPassengerCount));
            }
        });

        discountMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discountPassengerCount > 0) {
                    discountPassengerCount--;
                    discountCountTextView.setText(String.valueOf(discountPassengerCount));
                }
            }
        });

        discountPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountPassengerCount++;
                discountCountTextView.setText(String.valueOf(discountPassengerCount));
            }
        });

        pickBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDestination = destinationComboBox.getText().toString();
                int price = getPriceForDestination(selectedDestination);
                int standardPrice = getPriceForStandardBus(selectedDestination);
                int deluxePrice = getPriceForDeluxeBus(selectedDestination);
                int premiumPrice = getPriceForPremiumBus(selectedDestination);
                Intent intent = new Intent(HomeActivity.this, PickBus.class);
                intent.putExtra("selectedDestination", selectedDestination);
                intent.putExtra("price", price);
                intent.putExtra("standardPrice", standardPrice);
                intent.putExtra("deluxePrice", deluxePrice);
                intent.putExtra("premiumPrice", premiumPrice);

                intent.putExtra("selectedDate", datePickerButton.getText().toString());

                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private int getPriceForDestination(String destination) {
        int price = 0;
        switch (destination) {
            case "Naga Terminal":
                price = 50;
                break;
            case "San Fernando Terminal":
                price = 72;
                break;
            case "Carcar Terminal":
                price = 95;
                break;
            case "Argao Terminal":
                price = 117;
                break;
            case "Dalaguete Terminal":
                price = 153;
                break;
            case "Alcoy Terminal":
                price = 189;
                break;
            case "Boljoon Terminal":
                price = 234;
                break;
            case "Osblob Terminal":
                price = 176;
                break;
            case "Santander Terminal":
                price = 310;
                break;
            // Add more cases for other destinations if needed
            default:
                price = 0; // If the destination is not in the mapping, set price to 0
                break;
        }
        return price;
    }

    public int getPriceForStandardBus(String destination) {
        int standardPrice = 0;
        switch (destination) {
            case "Naga Terminal":
                standardPrice = 45;
                break;
            case "San Fernando Terminal":
                standardPrice = 66;
                break;
            case "Carcar Terminal":
                standardPrice = 81;
                break;
            case "Argao Terminal":
                standardPrice = 102;
                break;
            case "Dalaguete Terminal":
                standardPrice = 140;
                break;
            case "Alcoy Terminal":
                standardPrice = 171;
                break;
            case "Boljoon Terminal":
                standardPrice = 215;
                break;
            case "Osblob Terminal":
                standardPrice = 253;
                break;
            case "Santander Terminal":
                standardPrice = 285;
                break;
            // Add more cases for other destinations if needed
            default:
                standardPrice = 0; // If the destination is not in the mapping, set price to 0
                break;
        }
        return standardPrice;
    }

    private int getPriceForDeluxeBus(String destination) {
        int deluxePrice = 0;
        switch (destination) {
            case "Naga Terminal":
                deluxePrice = 55;
                break;
            case "San Fernando Terminal":
                deluxePrice = 78;
                break;
            case "Carcar Terminal":
                deluxePrice = 102;
                break;
            case "Argao Terminal":
                deluxePrice = 125;
                break;
            case "Dalaguete Terminal":
                deluxePrice = 164;
                break;
            case "Alcoy Terminal":
                deluxePrice = 199;
                break;
            case "Boljoon Terminal":
                deluxePrice = 245;
                break;
            case "Osblob Terminal":
                deluxePrice = 288;
                break;
            case "Santander Terminal":
                deluxePrice = 323;
                break;
            // Add more cases for other destinations if needed
            default:
                deluxePrice = 0; // If the destination is not in the mapping, set price to 0
                break;
        }
        return deluxePrice;
    }

    private int getPriceForPremiumBus(String destination) {
        int premiumPrice = 0;
        switch (destination) {
            case "Naga Terminal":
                premiumPrice = 75;
                break;
            case "San Fernando Terminal":
                premiumPrice = 102;
                break;
            case "Carcar Terminal":
                premiumPrice = 132;
                break;
            case "Argao Terminal":
                premiumPrice = 160;
                break;
            case "Dalaguete Terminal":
                premiumPrice = 204;
                break;
            case "Alcoy Terminal":
                premiumPrice = 244;
                break;
            case "Boljoon Terminal":
                premiumPrice = 295;
                break;
            case "Osblob Terminal":
                premiumPrice = 343;
                break;
            case "Santander Terminal":
                premiumPrice = 383;
                break;
            // Add more cases for other destinations if needed
            default:
                premiumPrice = 0; // If the destination is not in the mapping, set price to 0
                break;
        }
        return premiumPrice;
    }

    public void openTimePicker(View view) {
        timePickerDialog.show();
    }

    private void navigateToPickBusActivity() {
        String selectedDestination = destinationComboBox.getText().toString();
        Intent intent = new Intent(HomeActivity.this, PickBus.class);
        intent.putExtra("selectedDestination", selectedDestination);
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(Button datePickerButton) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datePickerButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void initTimePicker(Button timeButton) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = makeTimeString(hourOfDay, minute);
                timeButton.setText(time);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(HomeActivity.this, timeSetListener, hour, minute, false);
    }

    private String makeTimeString(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    public void onClickRegularMinusButton(View view) {
        if (regularPassengerCount > 0) {
            regularPassengerCount--;
            TextView regularCountTextView = findViewById(R.id.regularCountTextView);
            regularCountTextView.setText(String.valueOf(regularPassengerCount));
        }
    }

    public void onClickRegularPlusButton(View view) {
        regularPassengerCount++;
        TextView regularCountTextView = findViewById(R.id.regularCountTextView);
        regularCountTextView.setText(String.valueOf(regularPassengerCount));
    }

    public void onClickDiscountMinusButton(View view) {
        if (discountPassengerCount > 0) {
            discountPassengerCount--;
            TextView discountCountTextView = findViewById(R.id.discountCountTextView);
            discountCountTextView.setText(String.valueOf(discountPassengerCount));
        }
    }

    public void onClickDiscountPlusButton(View view) {
        discountPassengerCount++;
        TextView discountCountTextView = findViewById(R.id.discountCountTextView);
        discountCountTextView.setText(String.valueOf(discountPassengerCount));
    }

}