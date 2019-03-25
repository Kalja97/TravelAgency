package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.LocationListViewModel;
import com.example.travelagency.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //----
    private static final String TAG = "MainActivity";
    //----
   // private ListView listview;
    private SwipeMenuListView listview;
    private List<Location> locationList;
    private LocationListViewModel viewModel;

    private Location location;
    private LocationViewModel vmLocation;

    String countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryName = getIntent().getStringExtra("countryName");
        /*
            listview = (ListView) findViewById(R.id.listview);
            String[] shows = getResources().getStringArray(R.array.shows_array);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shows);
            listview.setAdapter(adapter);
        */

        listview = (SwipeMenuListView) findViewById(R.id.listview);
        locationList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        LocationListViewModel.Factory factory = new LocationListViewModel.Factory(getApplication());

        viewModel = ViewModelProviders.of(this, factory).get(LocationListViewModel.class);
        viewModel.getLocations().observe(this, showEntities -> {
            if (showEntities != null) {
                locationList = showEntities;
                adapter.addAll(locationList);
            }
        });

        listview.setAdapter(adapter);

//       Retrieved from: https://github.com/baoyongzhang/SwipeMenuListView
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listview.setMenuCreator(creator);

//        ItemClickListener for AdapterView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Trips2Activity.class);
        /*
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );
        */
                intent.putExtra("countryName", locationList.get(position).getCountryName());
                startActivity(intent);
            }
        });

//        MenuItemClickListener for SwipeMenuList
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
                        delete(position);
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

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
                Intent intentHome = new Intent(this, addLocationActivity.class);
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

// Delete location from DB
    private void delete(int position){

        location = locationList.get(position);
        viewModel.deleteLocation(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Delete trip: success");
                refresh();
            }

            private void refresh() {
//                Refresh page
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }

    private void goToEditLocation(int position){
        Intent intent = new Intent(this, EditLocation.class);
        intent.putExtra("countryName", locationList.get(position).getCountryName());
        startActivity(intent);
    }
}





