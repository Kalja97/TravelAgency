package com.example.travelagency.ui.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.TripViewModel;

public class TripActivityEdit extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TripActivityEdit";

    private TextView tvCountryName;
    private TextView tvTripName;
    private TextView tvDuration;
    private TextView tvDate;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvImageUrl;

    private Toast toast;

    String countryName;
    String tripName;

//    ------
    String name;
    String city;
    String day;
    String cost;
    String datum;
    String desc;
    String imageUrl;
    //-------

    //-----------------
    AlertDialog dialog;
    EditText editText;
    //------------------

    private Trip trip;
    private TripViewModel vmTrip;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_edit);

        tripName = getIntent().getStringExtra("tripName");
        countryName = getIntent().getStringExtra("countryName");

        initiateView();

        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edit");
        dialog.setView(editText);


        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){

                }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());


        ;

//      tvCountryName.setOnClickListener(this);
        tvTripName.setOnClickListener(this);
        tvDuration.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvDescription.setOnClickListener(this);
        tvImageUrl.setOnClickListener(this);

        TripViewModel.Factory tripFac = new TripViewModel.Factory(getApplication(), tripName);
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

    private void saving(){
        name = (String) tvCountryName.getText();
                        city = "" + tvTripName.getText();
                        day = "" + tvDuration.getText();
                        cost = "" + tvPrice.getText();
                        datum = "" + tvDate.getText();
                        desc = "" + tvDescription.getText();
                        imageUrl = "" + tvImageUrl.getText();

                        saveChanges(name,city, day, cost, datum, desc, imageUrl);
    }

    private void saveChanges(String country, String city, String days, String price, String date, String description, String imageUrl) {

        trip.setCountryName(country);
        trip.setTripname(city);
        trip.setDuration(days);
        trip.setPrice(price);
        trip.setDate(date);
        trip.setDescription(description);
        trip.setImageUrl(imageUrl);

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
                Intent intentTrip = new Intent(this, Trips2Activity.class);
                intentTrip.putExtra("countryName", countryName);
                intentTrip.putExtra("tripName", tripName);
                startActivity(intentTrip);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
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
        tvImageUrl = findViewById(R.id.imageUrl);
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
            tvImageUrl.setText(trip.getImageUrl());
        }
    }
}
