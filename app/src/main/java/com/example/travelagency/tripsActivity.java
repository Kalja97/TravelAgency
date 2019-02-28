package com.example.travelagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tripsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String[] trip_names;
    String[] quickDescription;

    List<TripsActivityRowItem> tripsActivityRowItem;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        tripsActivityRowItem = new ArrayList<TripsActivityRowItem>();

        trip_names = getResources().getStringArray(R.array.trip_name);

        quickDescription = getResources().getStringArray(R.array.quickDescription);



        for (int i = 0; i < trip_names.length; i++) {
            TripsActivityRowItem item = new TripsActivityRowItem(trip_names[i], quickDescription[i]);
            tripsActivityRowItem.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        ActivityTripsAdapter adapter = new ActivityTripsAdapter(this, tripsActivityRowItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String trip_name = tripsActivityRowItem.get(position).getTrip_name();
        Toast.makeText(getApplicationContext(), "" + trip_names,
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
    }


}
