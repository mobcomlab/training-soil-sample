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


public class AddSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sample);

        final EditText id = (EditText) findViewById(R.id.add_id);
        final EditText xCoord = (EditText) findViewById(R.id.add_xCoord);
        final EditText yCoord = (EditText) findViewById(R.id.add_yCoord);
        final EditText dateText = (EditText) findViewById(R.id.add_date);
        final EditText ph = (EditText) findViewById(R.id.add_ph);
        final EditText cd = (EditText) findViewById(R.id.add_cd);

        Button btn = (Button) findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Text_id = id.getText().toString();
                final float Text_xCord = Float.parseFloat(xCoord.getText().toString());
                final float Text_yCord = Float.parseFloat(yCoord.getText().toString());

                final float Text_ph = Float.parseFloat(ph.getText().toString());
                final int Text_cd = Integer.parseInt(cd.getText().toString());

                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = sdf.parse(dateText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final DatabaseManager databaseManager = new DatabaseManager(AddSampleActivity.this);
                databaseManager.addSoilSample(Text_id, Text_xCord, Text_yCord, date, Text_ph, Text_cd);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
