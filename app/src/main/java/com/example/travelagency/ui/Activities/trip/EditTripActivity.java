package com.example.travelagency.ui.Activities.trip;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.Repository.TripRepository;
import com.example.travelagency.ui.Activities.SettingsActivity;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.trip.TripViewModel;

public class EditTripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditTripActivity";

    //Textviews
    private TextView tvCountryName;
    private TextView tvTripName;
    private TextView tvDuration;
    private TextView tvDate;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvImageUrl;
    private RatingBar rbratingBar;

    //Toast
    private Toast toast;

    //get intent
    String countryName;
    String tripName;
    String id;

    //Strings for saving
    String name;
    String city;
    String day;
    String cost;
    String datum;
    String desc;
    String imageUrl;
    float rating;

    AlertDialog dialog;
    EditText editText;

    private Trip trip;
    private TripViewModel vmTrip;
    private TripRepository repository;

    //create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        setTitle("Edit trip");

        id = getIntent().getStringExtra("id");
        countryName = getIntent().getStringExtra("countryName");

        initiateView();

        repository = new TripRepository();

        //Dialog for choosing textview to change
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edit");
        dialog.setView(editText);

        //Rating bar
        RatingBar mBar = (RatingBar) findViewById(R.id.ratingBar);

        //Handling for click on button
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){

                }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());;

        //Set onclicklisteners for textviews
        tvTripName.setOnClickListener(this);
        tvDuration.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvDescription.setOnClickListener(this);
        tvImageUrl.setOnClickListener(this);

        //Get data from database
        TripViewModel.Factory tripFac = new TripViewModel.Factory(getApplication(), countryName, id, repository);
        vmTrip = ViewModelProviders.of(this, tripFac).get(TripViewModel.class);
        vmTrip.getTrip().observe(this, tripEntity -> {
            if (tripEntity != null) {
                trip = tripEntity;
                updateContent();
            }
        });
      }

    //TextViews auswählen und dialogbox zum Text ändern anzeigen
    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.trip:
                editText.setText(tvTripName.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvTripName.setText(editText.getText());
                    }
                });
                break;

            case R.id.duration:
                editText.setText(tvDuration.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDuration.setText(editText.getText());
                    }
                });

                break;

            case R.id.price:
                editText.setText(tvPrice.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvPrice.setText(editText.getText());
                    }
                });
                break;

            case R.id.date:
                editText.setText(tvDate.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDate.setText(editText.getText());
                    }
                });
                break;

            case R.id.imageUrl:
                editText.setText(tvImageUrl.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvImageUrl.setText(editText.getText());
                    }
                });
                break;

            case R.id.description:
                editText.setText(tvDescription.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDescription.setText(editText.getText());
                    }
                });
                break;
        }
    }

    //Method for saving which calls method save changes
    private void saving(){
        name = tvCountryName.getText().toString().trim();
                        city = "" + tvTripName.getText();
                        day = "" + tvDuration.getText();
                        cost = "" + tvPrice.getText();
                        datum = "" + tvDate.getText();
                        desc = "" + tvDescription.getText();
                        imageUrl = "" + tvImageUrl.getText();
                        rating = rbratingBar.getRating();

        //Call method to save changes
        saveChanges(name,city, day, cost, datum, desc, imageUrl, rating);
    }


    //save the changes into the database
    private void saveChanges(String country, String city, String days, String price, String date, String description, String imageUrl, float rating) {

        trip.setCountryName(country);
        trip.setTripName(city);
        trip.setDuration(days);
        trip.setPrice(price);
        trip.setDate(date);
        trip.setDescription(description);
        trip.setImageUrl(imageUrl);
        trip.setRating(rating);

        vmTrip.updateTrip(trip, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateTrip: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateTrip: failure", e);
            }
        });
    }


    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                saving();

                Intent intentTrip = new Intent(this, TripsActivity.class);
                intentTrip.putExtra("countryName", countryName);
                intentTrip.putExtra("id", id);
                startActivity(intentTrip);
                return true;

            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Set image from url
    private void setImage(){

        Log.d(TAG, "setImage: setting te image and name to widgets.");

        String imageUrl = trip.getImageUrl();

        ImageView image = findViewById(R.id.imageView);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

    //initialize the edit texts
    private void initiateView() {
        tvCountryName = findViewById(R.id.country);
        tvTripName = findViewById(R.id.trip);
        tvDuration = findViewById(R.id.duration);
        tvDate = findViewById(R.id.date);
        tvPrice = findViewById(R.id.price);
        tvDescription = findViewById(R.id.description);
        tvImageUrl = findViewById(R.id.imageUrl);
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
            tvImageUrl.setText(trip.getImageUrl());
            rbratingBar.setRating(trip.getRating());
        }
    }
}
