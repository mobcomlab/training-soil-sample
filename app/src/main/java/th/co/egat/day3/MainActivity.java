package th.co.egat.day3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import th.co.egat.day3.adapters.SoilSampleAdapter;
import th.co.egat.day3.managers.DatabaseManager;
import th.co.egat.day3.models.SoilSample;

public class MainActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the map
        findViewById(R.id.map).setVisibility(View.GONE);

        // Get data
        final List<SoilSample> data = new DatabaseManager(this).getSoilSamples();

        // Setup recycler
        final RecyclerView samples =(RecyclerView) findViewById(R.id.samples);
        samples.setAdapter(new SoilSampleAdapter(this, data));
        samples.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
