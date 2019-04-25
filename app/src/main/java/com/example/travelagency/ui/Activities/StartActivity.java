package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelagency.R;
import com.example.travelagency.ui.Activities.location.LocationsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


public class StartActivity extends AppCompatActivity {

    ImageView bgstart;
    private final String TAG = "Test";

    private FirebaseRemoteConfig firebaseRemoteConfig;

    private TextView titel;
    private TextView textView;
    private Button button;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textView= findViewById(R.id.remoteText);
        titel= findViewById(R.id.text_title);
        button = findViewById(R.id.btnget);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
        firebaseRemoteConfig.setDefaults(R.xml.default_map);

        firebaseRemoteConfig.fetch(60*5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(StartActivity.this, "Activated", Toast.LENGTH_SHORT).show();


                    //Activiting fetched parameters. The new parameters will now be available to your app
                    firebaseRemoteConfig.activate();

                }else {
                    Toast.makeText(StartActivity.this, "Not Activated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Setting string for TextView using parameters returned from remote config server
        textView.setTextColor(Color.parseColor(firebaseRemoteConfig.getString("text_color")));
        textView.setText(firebaseRemoteConfig.getString("text_str"));
        textView.setTextSize((float) firebaseRemoteConfig.getValue("text_size").asDouble());
        titel.setText(firebaseRemoteConfig.getString("text_title"));
        button.setTextColor(Color.parseColor(firebaseRemoteConfig.getString("text_btn_color")));

        //zooming picture
        bgstart = (ImageView) findViewById(R.id.bgstart);
        bgstart.animate().scaleX(1.5f).scaleY(1.5f).setDuration(6000).start();
    }

    //Method to show the locations
    public void gotomain (View view){
        Intent intent = new Intent (this, LocationsActivity.class);
        startActivity(intent);
    }
}
