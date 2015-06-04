package th.co.egat.day3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import th.co.egat.day3.managers.DatabaseManager;
import th.co.egat.day3.models.SoilSample;

public class DetailActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent == null) {
            throw new IllegalStateException("Must be launched from an intent");
        }

        final String soilSampleId = intent.getStringExtra("soilSampleId");
        final SoilSample sample = new DatabaseManager(this)
                .getSoilSample(soilSampleId);

        // Bind data
        getSupportActionBar().setTitle(sample.getId());

        final TextView detailPh = (TextView) findViewById(R.id.detail_ph);
        detailPh.setText(String.valueOf(sample.getPh()));

        final TextView detailDate = (TextView) findViewById(R.id.detail_date);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        detailDate.setText(sdf.format(sample.getDate()));

        final TextView detailXLocation = (TextView) findViewById(R.id.detail_x_location);
        detailXLocation.setText(String.valueOf(sample.getxCoord()));

        final TextView detailYLocation = (TextView) findViewById(R.id.detail_y_location);
        detailYLocation.setText(String.valueOf(sample.getyCoord()));
    }

}
