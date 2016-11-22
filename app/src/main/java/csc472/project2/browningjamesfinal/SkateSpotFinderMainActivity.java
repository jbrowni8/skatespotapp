package csc472.project2.browningjamesfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class SkateSpotFinderMainActivity extends AppCompatActivity
        implements OnConnectionFailedListener {
    public static final String TAG = "SkateSpotFinderMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skate_spot_finder_main);
    }

    public void onProfileClick(View v) {
    }

    @SuppressLint("LongLogTag")
    public void onMapClick(View v) {
        Log.d(TAG, "---> onMapClick");

        Intent intent = new Intent(SkateSpotFinderMainActivity.this, MapsActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void onUploadClick(View v) {
        Intent intent = new Intent(SkateSpotFinderMainActivity.this, UploadActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




}

