package com.example.splashscreenwithlogin;

import android.content.Intent;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenwithlogin.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapResult extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView infoView;
    Bundle bundle;
    Button b1;
    private static final String TAG = "MapResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_result);
        infoView=findViewById(R.id.Info_view);
        bundle=getIntent().getExtras();
        b1=findViewById(R.id.paytm);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MapResult.this,checksum.class);
                it.putExtra("loc_desc",bundle.getString("Address","Current User's Location"));
                it.putExtra("loc_lat",String.valueOf(bundle.getDouble("Latitude")));
                it.putExtra("loc_lang",String.valueOf(bundle.getDouble("Longitude")));
                it.putExtra("price",Constants.PAYTM_PRICE);
                startActivity(it);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(bundle.getDouble("Latitude"), bundle.getDouble("Longitude"));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Selected Location"));
        moveCamera(sydney, 15f);
        String info;
        info="Address: "+bundle.getString("Address","Current User's Location");
        if (bundle.getString("Locality")!=null) {
            info += "\nLocality: " + bundle.getString("Locality");
        }
        info += "\nLatitude: " + bundle.getDouble("Latitude");
        info += "\nLongitude: " + bundle.getDouble("Longitude");


        infoView.setText(info);
    }
    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}
