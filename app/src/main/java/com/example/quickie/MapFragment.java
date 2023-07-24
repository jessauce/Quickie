package com.example.quickie;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.PolylineOptions;
import android.graphics.Color;



public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private HomeActivity homeActivity;
    private Marker originMarker;
    private Marker destinationMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        supportMapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng cebuCityLatLng = new LatLng(10.3157, 123.8854);
        float zoomLevel = 11f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cebuCityLatLng, zoomLevel));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (homeActivity != null) {
                    String selectedOrigin = homeActivity.originComboBox.getText().toString();
                    String selectedDestination = homeActivity.destinationComboBox.getText().toString();
                    if (selectedOrigin.isEmpty()) {
                        addOriginMarker(latLng, selectedOrigin);
                    } else if (selectedDestination.isEmpty()) {
                        addDestinationMarker(latLng, selectedDestination);
                    }
                }
            }
        });
    }

    private void addOriginMarker(LatLng latLng, String title) {
        if (originMarker != null) {
            originMarker.remove();
        }
        originMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void addDestinationMarker(LatLng latLng, String title) {
        if (destinationMarker != null) {
            destinationMarker.remove();
        }
        destinationMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
        if (originMarker != null) {
            originMarker.setVisible(true);
            LatLng originLatLng = originMarker.getPosition();

            /*// Add a Polyline between the origin and destination markers
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(originLatLng, latLng)
                    .color(Color.RED)
                    .width(3f);
            googleMap.addPolyline(polylineOptions);*/

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(originLatLng);
            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            int padding = 100; // Adjust the padding as desired
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cameraUpdate);
        }
    }



    public void pinLocation(LatLng latLng, String title) {
        if (originMarker == null) {
            addOriginMarker(latLng, title);
        } else if (destinationMarker == null) {
            addDestinationMarker(latLng, title);
        } else {
            originMarker.setVisible(false);
            addDestinationMarker(latLng, title);
        }
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }
}