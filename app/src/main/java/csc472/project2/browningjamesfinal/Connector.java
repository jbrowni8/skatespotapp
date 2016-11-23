package csc472.project2.browningjamesfinal;

/**
 * Created by Browning on 11/22/16.
 */

import android.location.Location;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Connector extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://skatespotapp.000webhostapp.com/test.php/";
    private Map<String, String> params;

    public Connector(String type, String name, String description, boolean covered, float rating,
                     String accessibleTimes, Location mBestReading, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("type", type);
        params.put("name", name);
        params.put("covered", (covered ? 1 : 0) + "");
        params.put("rating", rating + "");
        params.put("description", description);
        params.put("accessibleTimes", accessibleTimes);
        params.put("bestLocation", mBestReading.toString());
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
