package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    String countryName;
    String tripName;

    //-----------------
    AlertDialog dialog;
    EditText editText;
    //------------------

    private Trip trip;
    private TripViewModel vmTrip;

    //To put the symbols to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
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


        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){

                }
        });

        tvCountryName.setOnClickListener(this);
        tvTripName.setOnClickListener(this);
        tvDuration.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvDescription.setOnClickListener(this);

        TripViewModel.Factory tripFac = new TripViewModel.Factory(getApplication(), tripName);
        vmTrip = ViewModelProviders.of(this, tripFac).get(TripViewModel.class);
        vmTrip.getTrip().observe(this, tripEntity -> {
            if (tripEntity != null) {
                trip = tripEntity;
                updateContent();
            }
        });
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
            tvCountryName.setText(getIntent().getStringExtra("countryName"));
            tvTripName.setText(trip.getTripname());
            tvDuration.setText(trip.getDuration());
            tvDate.setText(trip.getDate());
            tvPrice.setText(trip.getPrice());
            tvDescription.setText(trip.getDescription());
        }
    }

    //TextViews auswählen und dialogbox zum Text ändern anzeigen
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.country:
                editText.setText(tvCountryName.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvCountryName.setText(editText.getText());
                    }
                });


                break;

            case R.id.trip:
                editText.setText(tvTripName.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvTripName.setText(editText.getText());
                    }
                });
                break;

            case R.id.duration:
                editText.setText(tvDuration.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDuration.setText(editText.getText());
                    }
                });
                break;

            case R.id.price:
                editText.setText(tvPrice.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvPrice.setText(editText.getText());
                    }
                });
                break;

            case R.id.date:
                editText.setText(tvDate.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDate.setText(editText.getText());
                    }
                });
                break;

            case R.id.description:
                editText.setText(tvDescription.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        tvDescription.setText(editText.getText());

                    }
                });
                break;


        }

        String countryName = (String) tvCountryName.getText();
        String trip = (String) tvTripName.getText();
        String day = (String) tvDuration.getText();
        String cost = (String) tvPrice.getText();
        String datum = (String) tvDate.getText();
        String desc = (String) tvDescription.getText();

        saveChanges(countryName,trip, day, cost, datum, desc);
    }

    private void saveChanges(String country, String city, String days, String price, String date, String description) {

        trip.setCountryName(country);
        trip.setTripname(city);
        trip.setDuration(days);
        trip.setPrice(price);
        trip.setDate(date);
        trip.setDescription(description);

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
}
