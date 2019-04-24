package com.example.travelagency.ui.Activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelagency.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


public class RemoteActivity extends AppCompatActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;

    private Button button;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);


        button = findViewById(R.id.fetch);
        textView= findViewById(R.id.text);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
        firebaseRemoteConfig.setDefaults(R.xml.default_map);

        /*
        Setting color, size and string for TextView using parameters returned from
        remote config server
         */
        textView.setTextColor(Color.parseColor(firebaseRemoteConfig.getString("text_color")));
        textView.setTextSize((float) firebaseRemoteConfig.getValue("text_size").asDouble());
        textView.setText(firebaseRemoteConfig.getString("text_str"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 This will initiate fetching of parameters. We have set the expiry time as 0
                 which will ensure we get fresh parameters every time
                 */
                firebaseRemoteConfig.fetch(60*5).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RemoteActivity.this, "Activated", Toast.LENGTH_SHORT).show();
                            /*
                            Activiting fetched parameters. The new parameters will now be available to your app
                             */
                            firebaseRemoteConfig.activateFetched();
                        }else {
                            Toast.makeText(RemoteActivity.this, "Not Activated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}