package com.example.travelagency;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] country_names;
    TypedArray country_pics;
    String[] action_title;

    List<RowItem> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rowItems = new ArrayList<RowItem>();

        country_names = getResources().getStringArray(R.array.country_names);

        country_pics = getResources().obtainTypedArray(R.array.country_pics);

        action_title = getResources().getStringArray(R.array.action_title);



        for (int i = 0; i < country_names.length; i++) {
            RowItem item = new RowItem(country_names[i],
                    country_pics.getResourceId(i, -1), action_title[i]);
            rowItems.add(item);
        }

        mylistview = (ListView) findViewById(R.id.list);
        CustomAdapter adapter = new CustomAdapter(this, rowItems);
        mylistview.setAdapter(adapter);

        mylistview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String member_name = rowItems.get(position).getCountry_name();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, tripsActivity.class);
        startActivity(intent);
    }
}
