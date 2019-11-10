package com.example.cleanshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cleanshot.ui.dashboard.DashboardFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private TextView tv_cursorLocation;
    private static Double lt;
    private static Double lg;
    private static float zoomLevel;
    private static LatLng center;
    private static String location;
    private static Bitmap image;
    private static String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mapView = (MapView) findViewById(R.id.mapView);
        tv_cursorLocation = (TextView) findViewById(R.id.tv_cursorLocation);



        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            if(extras.get("image") != null)
                image = (Bitmap) extras.get("image");
            if(extras.get("path") != null)
                path = extras.get("path").toString();
            lt = extras.getDouble("latitude");
            lg = extras.getDouble("longitude");
            zoomLevel = 16.0f;
        } else {
            lt = 43.8334546;
            lg = 18.3141132;
            zoomLevel = 17.0f;
        }
        location = getCompleteAddressString(lt, lg);
        tv_cursorLocation.setText(location);
    }

    public void onOK(View view) {
        Intent intent = new Intent(this, SendActivity.class);
        intent.putExtra("location", location);
        intent.putExtra("image", image);
        intent.putExtra("path", path);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lt, lg), zoomLevel));
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                center = map.getProjection().getVisibleRegion().latLngBounds.getCenter();
                location = getCompleteAddressString(center.latitude, center.longitude);
                tv_cursorLocation.setText(location);
            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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
