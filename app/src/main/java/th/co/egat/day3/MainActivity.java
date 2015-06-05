package th.co.egat.day3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import th.co.egat.day3.adapters.SoilSampleAdapter;
import th.co.egat.day3.managers.DatabaseManager;
import th.co.egat.day3.managers.WebServiceCallbackListener;
import th.co.egat.day3.managers.WebServiceManager;
import th.co.egat.day3.models.SoilSample;

public class MainActivity
        extends AppCompatActivity implements WebServiceCallbackListener, OnMapReadyCallback {

    final static int REQUEST_ADD = 1;

    private List<SoilSample> data;
    private Map<Marker, SoilSample>  markerSoils;

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

        // Setup Google Maps
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            //Intent intent = new Intent(this, AddSampleActivity.class);
            //startActivityForResult(intent, REQUEST_ADD);
            return true;
        }
        if (id == R.id.action_get_from_ws) {
            requestUpdateFromWebService();
            return true;
        }
        if (id == R.id.action_recycler) {
            findViewById(R.id.samples).setVisibility(View.VISIBLE);
            findViewById(R.id.map).setVisibility(View.GONE);
            return true;
        }
		else if (id == R.id.action_map) {
            findViewById(R.id.samples).setVisibility(View.GONE);
            findViewById(R.id.map).setVisibility(View.VISIBLE);
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
        data = new DatabaseManager(this).getSoilSamples();
        final RecyclerView samples = (RecyclerView) findViewById(R.id.samples);
        samples.setAdapter(new SoilSampleAdapter(this, data));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ADD) {
            refreshData();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set center
        LatLng trainingCenter = new LatLng(18.292380, 99.676552);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trainingCenter, 15));

        // Enable my location
        googleMap.setMyLocationEnabled(true);

        // Enable zoom control
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Set map view type
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add markers
        markerSoils = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            SoilSample soilSample = data.get(i);
            LatLng coord = new LatLng(soilSample.getyCoord(), soilSample.getxCoord());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(coord)
                    .title(soilSample.getId())
                    .snippet(sdf.format(soilSample.getDate()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
            );
            markerSoils.put(marker, soilSample);
        }

        // Handle info window click
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                SoilSample sample = markerSoils.get(marker);
                // Launch detail activity
                final Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("soilSampleId", sample.getId());
                startActivity(intent);
            }
        });

    }

}
