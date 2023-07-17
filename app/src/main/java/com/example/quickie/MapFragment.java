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
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private HomeActivity homeActivity;

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
        float zoomLevel = 12f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cebuCityLatLng, zoomLevel));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " KG " + latLng.longitude);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                googleMap.addMarker(markerOptions);

                if (homeActivity != null) {
                    String selectedOrigin = homeActivity.originComboBox.getText().toString();
                    String selectedDestination = homeActivity.destinationComboBox.getText().toString();
                    if (selectedOrigin.isEmpty()) {
                        homeActivity.setFromLatLng(latLng);
                    } else if (selectedDestination.isEmpty()) {
                        homeActivity.setToLatLng(latLng);
                    }
                }
            }
        });
    }

    public void pinLocation(LatLng latLng, String title) {
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        googleMap.addMarker(markerOptions);
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }
}