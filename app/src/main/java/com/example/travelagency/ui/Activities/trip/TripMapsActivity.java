package com.example.travelagency.ui.Activities.trip;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.travelagency.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class TripMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String tripName;
    String countryName;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_maps);

        tripName = getIntent().getStringExtra("tripName");
        countryName = getIntent().getStringExtra("countryName");

        setTitle("Map " + tripName);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //Get coordinates of trip
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return p1;
    }

    //Method to set a marker on the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //get location of trip
        LatLng triplocation = getLocationFromAddress(TripMapsActivity.this, tripName);

        //set marker on the map
        Marker location = mMap.addMarker(new MarkerOptions().position(triplocation).title("Marker in " + tripName).draggable(true).snippet(countryName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(triplocation));
        location.showInfoWindow();
    }
}
