package com.example.travelagency;

import android.os.AsyncTask;
import android.util.Log;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.Entities.Trip;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db){
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addLocation(final AppDatabase db, final String countryName, final int inhabitants,
                                    final String description, final String language) {
        Location location = new Location(countryName, inhabitants, description, language);
        db.locationDao().insert(location);
    }

    private static void addTrip(final AppDatabase db, final String location, final String tripName, final String duration,
                                final String date, final String price, final String description, String imageUrl, float rating) {
        Trip trip = new Trip(location, tripName, duration, date, price, description, imageUrl, rating);
        db.tripDao().insert(trip);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.locationDao().deleteAll();

        addLocation(db,
                "Switzerland", 8503111, "Switzerland borders Germany to the north, Austria and Liechtenstein to the east, Italy to the south and France to the west.", "German, French, Italian"
        );
        addLocation(db,
                "Scotland", 565956, "Scotland is a part of the United Kingdom of Great Britain and Northern Ireland. Scotland consists of the northern third of the largest European island of Great Britain and several island groups.", "English"
        );
        addLocation(db,
                "Spain", 56546565, "Spain is a member of the UN, the EU, the OECD and NATO. It is one of the most highly developed countries and one of the twenty largest export and import nations (2017). Spain is the second most visited country in the world after France.", "Spanish"
        );


        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addTrip(db,
                "Spain", "Barcelone", "4 Nights","29.06.2019 bis 05.07.2019" , "CHF 520.-" , "Barcelona is the capital of Catalonia and the second largest city in Spain after Madrid. It lies on the Mediterranean Sea, about 120 kilometres south of the Pyrenees and the border with France.","https://ob9a8415roh4djoj110c31a1-wpengine.netdna-ssl.com/wp-content/uploads/2013/02/barcelona-aerial-view.jpg", 3.5f
        );
        addTrip(db,
                "Spain", "Madrid", "5 Nights","11.07.2019 bis 16.07.2019" , "CHF 680.-" , "Madrid is the capital of Spain. With about seven million inhabitants, the metropolitan region of Madrid is one of the largest in Europe.  Madrid (excluding suburbs) with around 3.2 million inhabitants is the third largest city in the European Union after London and Berlin and the largest city in southern Europe.", "https://room-matehotels.com/images/img/general/hotel-madrid.jpg", 4f
        );
        addTrip(db,
                "Switzerland", "Zermatt", "3 Nights","08.08.2019 bis 11.08.2019" , "CHF 390.-" , "Zermatt is a political municipality and a burgher municipality with a burgher council of the district Visp as well as a parish of the deanery Visp in the Swiss canton Valais. After Visp, Zermatt is the second largest town in the district of Visp in terms of population and lies in the Mattertal valley at an altitude of about 1610 m at the north-eastern foot of the Matterhorn.", "https://eightforestlane.com/wp-content/uploads/2018/02/Zermatt19-web.jpg", 2f
        );
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
