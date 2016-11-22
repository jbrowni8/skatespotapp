package csc472.project2.browningjamesfinal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class UploadActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "UploadActivity";

    public static final int CAM_REQ_CODE = 100;

    private Location mBestReading;
    private LocationManager mLocationManager;

    private LocationListener mLocationListener;


    private SkateSpot spot;

    private ArrayAdapter<CharSequence> adapter;

    private String type;
    private String name;
    private String description;
    private boolean covered;
    private float rating;
    private String accessibleTimes;

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.spots_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Acquire reference to the LocationManager
        if (null == (mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE))) {
            finish();
        }
        // Get best last location measurement
        mBestReading = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mLocationListener = new LocationListener() {

            // Called back when location changes

            public void onLocationChanged(Location location) {


                // Determine whether new location is better than current best
                // estimate

                if (null == mBestReading
                        || location.getAccuracy() < mBestReading.getAccuracy()) {

                    mBestReading = location;
                }
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // NA
            }

            public void onProviderEnabled(String provider) {
                // NA
            }

            public void onProviderDisabled(String provider) {
                // NA
            }
        };
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
        //        , Toast.LENGTH_SHORT).show();
        Log.d(TAG, "--> onItemSelected item is "
                + "Item is : " + parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        finish();
    }

    public void createSpot(View v) {
        EditText eName = (EditText) findViewById(R.id.editText2);
        EditText eTime = (EditText) findViewById(R.id.editText4);
        EditText eCovered = (EditText) findViewById(R.id.editText3);
        EditText eExtras = (EditText) findViewById(R.id.editText5);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        name = eName.getText() + "";
        accessibleTimes = eTime.getText().toString();
        covered = eCovered.getText().toString().equals("Yes") ? true : false;
        description = eExtras.getText() + "";
        rating = ratingBar.getRating();

        spot = new SkateSpot(type, name, description, covered, rating, accessibleTimes,
                mBestReading.getLatitude(), mBestReading.getLongitude(), mBestReading);

        System.out.println(spot.toString());

        resetValues(eName, eTime, eExtras, ratingBar, eCovered);

        Intent intent = new Intent(UploadActivity.this, MapsActivity.class);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
        intent.putExtra("SkateSpot", spot);
        startActivity(intent);
        finish();
    }


    private void resetValues(EditText eName, EditText eTime,
                             EditText eExtras, RatingBar ratingBar, EditText eCovered) {
        eName.setText("");
        eTime.setText("");
        eExtras.setText("");
        eTime.setText("");
        eCovered.setText("");
        ratingBar.setRating(0);
        spinner.setSelection(0);
    }

    public void onCameraClick(View v) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAM_REQ_CODE);
        }
    }

    public void onCancel(View v) {
        finish();
    }
}
