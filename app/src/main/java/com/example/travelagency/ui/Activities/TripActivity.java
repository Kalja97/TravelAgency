package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.TripViewModel;


public class TripActivity extends AppCompatActivity  {

    private static final String TAG = "TripActivitys";

    private Trip trip;
    private TextView tvCountryName;
    private TextView tvTripName;
    private TextView tvDuration;
    private TextView tvDate;
    private TextView tvPrice;
    private TextView tvDescription;

    private TripViewModel vmTrip;

    String countryName;
    String tripName;


    //To put the symbols to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_trip, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        setTitle("Trip");

        tripName = getIntent().getStringExtra("tripName");
        countryName = getIntent().getStringExtra("countryName");

        initiateView();

        TripViewModel.Factory tripFac = new TripViewModel.Factory(getApplication(), tripName);
        vmTrip = ViewModelProviders.of(this, tripFac).get(TripViewModel.class);
        vmTrip.getTrip().observe(this, tripEntity -> {
            if (tripEntity != null) {
                trip = tripEntity;
                updateContent();
            }
        });
    }

    private void setImage(){

        Log.d(TAG, "setImage: setting te image and name to widgets.");

        String imageUrl = trip.getImageUrl();

        ImageView image = findViewById(R.id.imageView);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

    private void initiateView() {
        tvCountryName = findViewById(R.id.country);
        tvTripName = findViewById(R.id.trip);
        tvDuration = findViewById(R.id.duration);
        tvDate = findViewById(R.id.date);
        tvPrice = findViewById(R.id.price);
        tvDescription = findViewById(R.id.description);
    }

    private void updateContent() {
        if (trip != null) {
            setImage();
            tvCountryName.setText(getIntent().getStringExtra("countryName"));
            tvTripName.setText(trip.getTripname());
            tvDuration.setText(trip.getDuration());
            tvDate.setText(trip.getDate());
            tvPrice.setText(trip.getPrice());
            tvDescription.setText(trip.getDescription());
        }
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, TripActivityEdit.class);
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
                            Intent intent = new Intent(TripActivity.this, Trips2Activity.class);
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
