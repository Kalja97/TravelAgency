package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelagency.Adapters.GridAdapter;
import com.example.travelagency.R;

public class TripsActivity extends AppCompatActivity {


    GridView gridview;

    String[] values = {
            "Barcelona",
            "Madrid",
            "Java",
            "Twitter",
    };

    int[] images = {
            R.drawable.barcelonaview,
            R.drawable.spain,
            R.drawable.scotland,
            R.drawable.switzerland,
    };

    //Menü-Items auf der Actionbar anbringen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_trips, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        gridview = (GridView) findViewById(R.id.grid);

        GridAdapter gridAdapter = new GridAdapter(this, values, images);

        gridview.setAdapter(gridAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.textviewgrid))
                                .getText(), Toast.LENGTH_SHORT).show();

                    nextpage(gridview);
                }
            }
        );
    }

    public void nextpage (View view){
        Intent intent = new Intent (this, TripActivity.class);
        startActivity(intent);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intentHome = new Intent(this, addTripActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}