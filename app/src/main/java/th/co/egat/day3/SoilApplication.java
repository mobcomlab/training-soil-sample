package th.co.egat.day3;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import th.co.egat.day3.managers.DatabaseManager;

public class SoilApplication
        extends Application {

    private static final String PREF_FIRST_RUN = "pref_first_run";

    @Override
    public void onCreate() {
        super.onCreate();

        // First run only
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean(PREF_FIRST_RUN, true)) {
            final DatabaseManager databaseManager = new DatabaseManager(this);

            databaseManager.addSoilSample("soil_sample_54.1", 99.72516885456F, 18.38498117950304F, new Date(2682374400L), 8.43F, 2100);
            databaseManager.addSoilSample("soil_sample_54.2", 99.73440459506952F, 18.31024882229098F, new Date(2682374400L), 8.47F, 2230);
            databaseManager.addSoilSample("soil_sample_54.3", 99.7533312472838F, 18.350076652832293F, new Date(2682374400L), 7.64F, 2380);
            databaseManager.addSoilSample("soil_sample_54.4", 99.7533312472838F, 18.350076652832293F, new Date(2682374400L), 8.13F, 1700);
            databaseManager.addSoilSample("soil_sample_54.5", 99.752360845319F, 18.348914621405005F, new Date(2682374400L), 9.36F, 176);

            sp.edit().putBoolean(PREF_FIRST_RUN, false).apply();
        }
    }
}
