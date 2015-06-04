package th.co.egat.day3.managers;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.style.TtsSpan;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebServiceManager {

    final Context context;
    final WebServiceCallbackListener listener;


    public WebServiceManager(Context context, WebServiceCallbackListener listener) {
        super();
        this.context = context;
        this.listener = listener;
    }


    public void requestSoilSamples() {

        String url = "http://10.249.111.124:8080/geoserver/mmgdb/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=mmgdb:soil_sample54&maxFeatures=50&outputFormat=json";

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("WebServiceManager", "Response: " + response.toString());

                        DatabaseManager databaseManager = new DatabaseManager(context);

                        try {
                            JSONArray features = response.getJSONArray("features");
                            for (int i = 0; i < features.length(); i++) {
                                JSONObject feature = features.getJSONObject(i);
                                String id = feature.getString("id");

                                JSONArray coords = feature.getJSONObject("geometry").getJSONArray("coordinates");
                                double xCoord = coords.getDouble(0);
                                double yCoord = coords.getDouble(0);

                                JSONObject properties = feature.getJSONObject("properties");

                                String dateStr = properties.getString("date");
                                int year = (2500 + Integer.parseInt(dateStr.substring(6))) - 543;
                                String gregDateStr = dateStr.substring(0,6) + year;
                                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = sdf.parse(gregDateStr);
                                Log.i("Date", date.toString());
                                float ph = (float)properties.getDouble("ph");
                                int cd = properties.getInt("cd");

                                databaseManager.addSoilSample(id, xCoord, yCoord, date, ph, cd);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }

                        listener.onWebServiceCallback();

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}

