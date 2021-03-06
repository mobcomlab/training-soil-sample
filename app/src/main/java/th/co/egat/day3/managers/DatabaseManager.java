package th.co.egat.day3.managers;

import android.content.Context;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import th.co.egat.day3.models.SoilSample;

public class DatabaseManager {

    private final Context context;

    public DatabaseManager(final Context context) {
        this.context = context;
    }

    public List<SoilSample> getSoilSamples() {
        return Realm.getInstance(context)
                .where(SoilSample.class)
                .findAll();
    }

    public SoilSample getSoilSample(final String soilSampleId) {
        return Realm.getInstance(context)
                .where(SoilSample.class)
                .equalTo("id", soilSampleId)
                .findFirst();
    }

    public void addSoilSample(final String id, final double xCoord, final double yCoord, final Date date, final float ph, final int cd) {
        final Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        SoilSample soilSample = getSoilSample(id);

        // If doesn't exist then create
        if (soilSample == null) {
            soilSample = realm.createObject(SoilSample.class);
        }

        soilSample.setId(id);
        soilSample.setxCoord(xCoord);
        soilSample.setyCoord(yCoord);
        soilSample.setDate(date);
        soilSample.setPh(ph);
        soilSample.setCd(cd);

        realm.commitTransaction();
    }

    public void deleteSoilSamples() {
        final Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.clear(SoilSample.class);
        realm.commitTransaction();
    }

}
