package com.example.cleanshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class SendActivity extends AppCompatActivity implements LocationListener {

    ImageView imgView;
    LocationManager locationManager;
    TextView locationText;
    private Spinner junkSpinner;
    private static Double lt;
    private static Double lg;
    private Bitmap image;
    private static String path;
    private static String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        locationText = (TextView) findViewById(R.id.tvLocation);
        junkSpinner = (Spinner) findViewById(R.id.spinner_typeOfJunk);
        imgView = (ImageView) findViewById(R.id.imgView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.get("image") != null) {
                image = (Bitmap) extras.get("image");
                imgView.setImageBitmap(image);
            } else if (extras.get("path") != null) {
                path = extras.get("path").toString();
                imgView.setImageBitmap(BitmapFactory.decodeFile(path));
            } else if (extras.get("location") != null) {
                location = extras.get("location").toString();
            }

        }
        if(location != null)
            locationText.setText(location);
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

        String[] junk = new String[] {
                "Drugo", "Divlja deponija", "Sitni otpad", "Kabasti otpad", "Gradjevinski otpad"
        };
        ArrayAdapter<String> junkType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, junk);
        junkType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        junkSpinner.setAdapter(junkType);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(image != null)
            imgView.setImageBitmap(image);
        if(path != null)
            imgView.setImageBitmap(BitmapFactory.decodeFile(path));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.get("location") != null)
                locationText.setText(extras.get("location").toString());
        }
    }

    public void onSetLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);

        if(lt != null && lg != null) {
            intent.putExtra("latitude", lt);
            intent.putExtra("longitude", lg);
            intent.putExtra("image", image);
            intent.putExtra("path", path);
        }
        startActivity(intent);



    }

    public void onSend(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        path = null;
        image = null;
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(image != null) {
            lt = location.getLatitude();
            lg = location.getLongitude();
            String comAd = getCompleteAddressString(lt, lg);
            locationText.setText(comAd);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                // not returned
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}

