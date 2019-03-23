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
                                final String date, final String price, final String description) {
        Trip trip = new Trip(location, tripName, duration, date, price, description);
        db.tripDao().insert(trip);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.locationDao().deleteAll();

        addLocation(db,
                "Switzerland", 8503111, "Besuchen Sie die Schweiz.", "German, French, Italian"
        );
        addLocation(db,
                "Scotland", 10000000, "Besuchen Sie Schottland", "English"
        );
        addLocation(db,
                "Spain", 30000000, "Besuchen Sie Spanien", "Spanish"
        );


        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addTrip(db,
                "Spain", "Barcelona", "4 Nächte","29.06.2019 bis 05.07.2019" , "CHF 520.-" , "Testdescription blablabla"
        );
        addTrip(db,
                "Spain", "Madrid", "5 Nächte","29.06.2019 bis 05.07.2019" , "CHF 520.-" , "Testdescription blablabla"
        );
        addTrip(db,
                "Switzerland", "Zermatt", "3 Nächte","29.06.2019 bis 05.07.2019" , "CHF 520.-" , "Testdescription blablabla"
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
