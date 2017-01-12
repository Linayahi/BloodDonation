package com.blooddonation.blooddonation;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.LieuDAO;
import metier.LieuDon;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private ProgressDialog pDialog;
    Button btn_event,btn_iti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
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
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        new GetLieux().execute();
    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Vous êtes ici");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }



    /****************************************  Récupérer la liste des lieux ***************************************************************/


    private class GetLieux extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Chargement ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... args)
        {
            LieuDAO lieudao = new LieuDAO(getApplicationContext());
            lieudao.open();
            List<LieuDon> lieux = lieudao.getAllLieux();

            ArrayList<HashMap<String, String>> lieuList = new ArrayList<HashMap<String, String>>();

            for (LieuDon l : lieux)
            {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(LieuDAO.LIEU_ID, String.valueOf(l.getId()));
                map.put(LieuDAO.LIEU_NOM, l.getNom());
                map.put(LieuDAO.LIEU_ADRESSE, l.getAdresse());
                map.put(LieuDAO.LIEU_LAT, String.valueOf(l.getLatitude()));
                map.put(LieuDAO.LIEU_LON, String.valueOf(l.getLongitude()));
                map.put(LieuDAO.LIEU_DESC, l.getDesc());


                lieuList.add(map);
            }


            lieudao.close();

            return lieuList;



        }


        protected void onPostExecute(ArrayList<HashMap<String, String>> result)
        {

            super.onPostExecute(result);

            if (pDialog.isShowing())  pDialog.dismiss();

            if (result!=null)
            {
                for(int i=0; i<result.size();i++)
                {

                    double lat = Double.parseDouble(result.get(i).get(LieuDAO.LIEU_LAT));
                    double lon = Double.parseDouble(result.get(i).get(LieuDAO.LIEU_LON));

                    LatLng position = new LatLng(lat,lon);

                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(result.get(i).get(LieuDAO.LIEU_NOM))
                    );
                }

            }

            else Toast.makeText(MapsActivity.this, "Aucun lieu disponible", Toast.LENGTH_LONG).show();
        }
    }
/****************************************      Fin Récupérer la liste des lieux    *************************************/

@Override
 public boolean onMarkerClick(final Marker marker)
{
    if (!(marker.getId().equals( mCurrLocationMarker.getId())))

    {
        final Dialog dialog = new Dialog(MapsActivity.this);
        dialog.setContentView(R.layout.info_lieu);
        dialog.setTitle("Détails du lieu");
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.white);
        final TextView nomd = (TextView) dialog.findViewById(R.id.nomd);
        final TextView adressed = (TextView) dialog.findViewById(R.id.adressed);
        final TextView descriptiond = (TextView) dialog.findViewById(R.id.descriptiond);
        final TextView latituded = (TextView) dialog.findViewById(R.id.latituded);
        final TextView longituded = (TextView) dialog.findViewById(R.id.longituded);

        LieuDAO lieudao = new LieuDAO(getApplicationContext());
        lieudao.open();
        LieuDon lieu = lieudao.getLieuByName(marker.getTitle());
        nomd.setText(lieu.getNom());
        adressed.setText(lieu.getAdresse());
        descriptiond.setText(lieu.getDesc());
        latituded.setText(String.valueOf(lieu.getLatitude()));
        longituded.setText(String.valueOf(lieu.getLongitude()));
        lieudao.close();

        dialog.show();

        btn_event = (Button) dialog.findViewById(R.id.btn_event);
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,EventActivity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });

        btn_iti = (Button) dialog.findViewById(R.id.btn_iti);
        btn_iti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latituded.getText().toString()+","+longituded.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
    else if (marker.getId().equals( mCurrLocationMarker.getId()))
    {
        marker.setTitle("Vous êtes ici");
        marker.showInfoWindow();
    }

    return true;
}
}
