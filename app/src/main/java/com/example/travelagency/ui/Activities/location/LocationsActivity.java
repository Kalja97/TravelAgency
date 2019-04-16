package com.example.travelagency.ui.Activities.location;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.R;
import com.example.travelagency.ui.Activities.SettingsActivity;
import com.example.travelagency.ui.Activities.trip.TripsActivity;
import com.example.travelagency.viewmodel.location.LocationListViewModel;
import com.example.travelagency.viewmodel.location.LocationListViewModelF;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends AppCompatActivity {

    private static final String TAG = "LocationsActivity";

    //Attributs
    private SwipeMenuListView listview;
    private List<LocationF> locationList;
    private LocationListViewModelF viewModel;

    String countryName;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set title in the actionbar
        setTitle("Countries");

        countryName = getIntent().getStringExtra("countryName");

        //Adapter array
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //listview
        listview = (SwipeMenuListView) findViewById(R.id.listview);
        locationList = new ArrayList<>();

        LocationListViewModelF.Factory factory = new LocationListViewModelF.Factory(getApplication());

        //get data from database
        viewModel = ViewModelProviders.of(this, factory).get(LocationListViewModelF.class);
        viewModel.getLocations().observe(this, locatiomEntities -> {
            if (locatiomEntities != null) {
                locationList = locatiomEntities;
                adapter.clear();
                adapter.addAll(locationList);
            }
        });

        //adapter to the listview
        listview.setAdapter(adapter);

        //Retrieved from: https://github.com/baoyongzhang/SwipeMenuListView
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(81, 216,
                        199)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem infoItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                infoItem.setBackground(new ColorDrawable(Color.rgb(116, 0,
                        249)));
                // set item width
                infoItem.setWidth(170);
                //set text
                infoItem.setTitle("Info");
                // set item title fontsize
                infoItem.setTitleSize(18);
                // set item title font color
                infoItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(infoItem);
            }
        };

        // set creator
        listview.setMenuCreator(creator);

        //ItemClickListener for AdapterView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TripsActivity.class);

                intent.putExtra("countryName", locationList.get(position).getCountryName());
                startActivity(intent);
            }
        });

        //MenuItemClickListener for SwipeMenuList
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d(TAG, "onMenuItemClick: clicked item" + index);
                        goToEditLocation(position);
                        break;

                    case 1:
                        Log.d(TAG, "onMenuItemClick: clicked item" + index);
                        goToInfoLocation(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    //create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_locations, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intentHome = new Intent(this, AddLocationActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //start intent fpr the edit view
    private void goToEditLocation(int position){
        Intent intent = new Intent(this, EditLocationActivity.class);
        intent.putExtra("countryName", locationList.get(position).getCountryName());
        startActivity(intent);
    }

    //start the intent for the info view
    private void goToInfoLocation(int position){
        Intent intent = new Intent(this, DetailsLocationActivity.class);
        intent.putExtra("countryName", locationList.get(position).getCountryName());
        startActivity(intent);
    }
}





