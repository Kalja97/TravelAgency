package com.example.travelagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

}