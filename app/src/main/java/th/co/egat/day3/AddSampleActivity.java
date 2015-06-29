package th.co.egat.day3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import th.co.egat.day3.managers.DatabaseManager;
import th.co.egat.day3.models.SoilSample;


public class AddSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sample);

        final EditText idText = (EditText) findViewById(R.id.add_id);
        final EditText xCoordText = (EditText) findViewById(R.id.add_xCoord);
        final EditText yCoordText = (EditText) findViewById(R.id.add_yCoord);
        final EditText dateText = (EditText) findViewById(R.id.add_date);
        final EditText phText = (EditText) findViewById(R.id.add_ph);
        final EditText cdText = (EditText) findViewById(R.id.add_cd);

        // If a sample id was sent then load data to be edited
        final String soilSampleId = getIntent().getStringExtra("soilSampleId");
        if (soilSampleId != null) {
            SoilSample sample = new DatabaseManager(this).getSoilSample(soilSampleId);
            if (sample != null) {
                idText.setText(sample.getId());
                xCoordText.setText(Double.toString(sample.getxCoord()));
                yCoordText.setText(Double.toString(sample.getyCoord()));
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                dateText.setText(sdf.format(sample.getDate()));
                phText.setText(Float.toString(sample.getPh()));
                cdText.setText(Integer.toString(sample.getCd()));
            }

        }

        Button btn = (Button) findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = idText.getText().toString();
                final float xCoord = Float.parseFloat(xCoordText.getText().toString());
                final float yCoord = Float.parseFloat(yCoordText.getText().toString());
                final float ph = Float.parseFloat(phText.getText().toString());
                final int cd = Integer.parseInt(cdText.getText().toString());

                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = sdf.parse(dateText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final DatabaseManager databaseManager = new DatabaseManager(AddSampleActivity.this);
                databaseManager.addSoilSample(id, xCoord, yCoord, date, ph, cd);
                finish();
            }
        });

    }

}
