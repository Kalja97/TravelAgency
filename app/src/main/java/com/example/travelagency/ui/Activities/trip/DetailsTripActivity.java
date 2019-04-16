package com.example.travelagency.ui.Activities.trip;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.Entities.TripF;
import com.example.travelagency.R;
import com.example.travelagency.Repository.TripRepositoryF;
import com.example.travelagency.ui.Activities.SettingsActivity;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.trip.TripViewModel;
import com.example.travelagency.viewmodel.trip.TripViewModelF;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class DetailsTripActivity extends AppCompatActivity  {

    private static final String TAG = "TripActivitys";

    //Text views of the layout
    private TextView tvCountryName;
    private TextView tvTripName;
    private TextView tvDuration;
    private TextView tvDate;
    private TextView tvPrice;
    private TextView tvDescription;
    private RatingBar rbratingBar;

    //other attributs
    private TripViewModelF vmTrip;
    private TripF trip;
    private TripRepositoryF repository;

    //intent infos
    String countryName;
    String tripName;
    String id;


    //To put the symbols to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_trip, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);

        setTitle("Trip");

        id = getIntent().getStringExtra("id");
        countryName = getIntent().getStringExtra("countryName");

        initiateView();

        repository = new TripRepositoryF();

        //To show data
        TripViewModelF.Factory tripFac = new TripViewModelF.Factory(getApplication(), countryName, id, repository);
        vmTrip = ViewModelProviders.of(this, tripFac).get(TripViewModelF.class);
        vmTrip.getTrip().observe(this, tripEntity -> {
            if (tripEntity != null) {
                trip = tripEntity;
                updateContent();
            }
        });

        //Button
        Button map = findViewById(R.id.btnmap);

        //Rating bar, no changes possible, only editmode
        rbratingBar.setEnabled(false);

        //clicklistener for the map button
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng maplocation = getLocationFromAddress(DetailsTripActivity.this, tripName);

                //handle what happens when no location can be shown on the google map
                if (maplocation == null){
                    final AlertDialog alertDialog = new AlertDialog.Builder(DetailsTripActivity.this).create();
                    alertDialog.setTitle("No Location found");
                    alertDialog.setCancelable(true);
                    alertDialog.setMessage("Use another Tripname or make sure you are connected to the Internet");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ok", (dialog, which) -> alertDialog.dismiss());
                    alertDialog.show();
                } else{
                    Intent intentMap = new Intent(DetailsTripActivity.this, TripMapsActivity.class);
                    intentMap.putExtra("tripName", tripName);
                    intentMap.putExtra("countryName", countryName);
                    startActivity(intentMap);
                }
            }
        });
    }

    //get coordinates of a trip
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

    //Get the image based on the url and method to set the image
    private void setImage(){

        Log.d(TAG, "setImage: setting the image and name to widgets.");

        try {
            String imageUrl = trip.getImageUrl();
            ImageView image = findViewById(R.id.imageView);

            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(image);
        } catch (Exception ex) {

        }
    }

    //initialize the text views
    private void initiateView() {
        tvCountryName = findViewById(R.id.country);
        tvTripName = findViewById(R.id.trip);
        tvDuration = findViewById(R.id.duration);
        tvDate = findViewById(R.id.date);
        tvPrice = findViewById(R.id.price);
        tvDescription = findViewById(R.id.description);
        rbratingBar = findViewById(R.id.ratingBar);
    }

    //update text in the view
    private void updateContent() {
        if (trip != null) {
            setImage();
            tvCountryName.setText(getIntent().getStringExtra("countryName"));
            tvTripName.setText(trip.getTripName());
            tvDuration.setText(trip.getDuration());
            tvDate.setText(trip.getDate());
            tvPrice.setText(trip.getPrice());
            tvDescription.setText(trip.getDescription());
            rbratingBar.setRating(trip.getRating());
        }
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, EditTripActivity.class);
                intent.putExtra("tripName", tripName);
                intent.putExtra("countryName", countryName);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;

            case R.id.action_delete:
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.action_delete));
                alertDialog.setCancelable(true);
                alertDialog.setMessage(getString(R.string.delete_msg));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.btndelete), (dialog, which) -> {
                    vmTrip.deleteTrip(trip, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Delete trip: success");
                            goToTripsActivity();
                        }

                        private void goToTripsActivity() {
                            Intent intent = new Intent(DetailsTripActivity.this, TripsActivity.class);
                            intent.putExtra("countryName", countryName);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Exception e) {}
                    });
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
                alertDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
