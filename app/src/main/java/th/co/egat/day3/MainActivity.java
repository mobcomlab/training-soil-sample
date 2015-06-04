package th.co.egat.day3;

import android.content.Intent;
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
import th.co.egat.day3.managers.WebServiceCallbackListener;
import th.co.egat.day3.managers.WebServiceManager;
import th.co.egat.day3.models.SoilSample;

public class MainActivity
        extends AppCompatActivity implements WebServiceCallbackListener {

    final static int REQUEST_ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the map
        findViewById(R.id.map).setVisibility(View.GONE);

        // Setup recycler
        final RecyclerView samples = (RecyclerView) findViewById(R.id.samples);
        samples.setLayoutManager(new LinearLayoutManager(this));

        refreshData();
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            new DatabaseManager(this).deleteSoilSamples();
            refreshData();
            return true;
        }
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
            return true;
        }
        if (id == R.id.action_get_from_ws) {
            requestUpdateFromWebService();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestUpdateFromWebService() {
        new WebServiceManager(this, this).requestSoilSamples();
    }

    @Override
    public void onWebServiceCallback() {
        refreshData();
    }

    private void refreshData() {
        // Get data
        final List<SoilSample> data = new DatabaseManager(this).getSoilSamples();
        final RecyclerView samples = (RecyclerView) findViewById(R.id.samples);
        samples.setAdapter(new SoilSampleAdapter(this, data));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ADD) {
            refreshData();
        }

    }
}
