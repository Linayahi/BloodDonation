package com.blooddonation.blooddonation;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dao.LieuDAO;
import metier.LieuDon;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener{

    SharedPreferences sharedPreferences;
    String email,prenom,nom, photo;

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
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        email = GetString("email");
        prenom = GetString("prenom");
        nom = GetString("nom");
        photo = GetString("photo");

        //Remplir les informations du header de navigationView
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);
        TextView mail = (TextView) header.findViewById(R.id.email);
        username.setText(prenom.concat(" ").concat(nom));
        mail.setText(email);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profil)
        {
            Intent i = new Intent(MenuActivity.this, ProfilActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_map)
        {
            Intent i = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_image)
        {
            Intent i = new Intent(MenuActivity.this, ScannActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_quizz)
        {
            Intent i = new Intent(MenuActivity.this, QuizzActivity.class);
            startActivity(i);
        }

        else if (id == R.id.nav_deco)
        {
            Intent i = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String GetString(String key)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }

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

        Log.i("starting","starting1");

        //Afficher la liste des lieux
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
            pDialog = new ProgressDialog(MenuActivity.this);
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
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.drop))


                    );
                }

            }

            else Toast.makeText(MenuActivity.this, "Aucun lieu disponible", Toast.LENGTH_LONG).show();
        }
    }
    /****************************************      Fin Récupérer la liste des lieux    *************************************/

    @Override
    public boolean onMarkerClick(final Marker marker)
    {
        if (!(marker.getId().equals( mCurrLocationMarker.getId())))

        {
            final Dialog dialog = new Dialog(MenuActivity.this);
            dialog.setContentView(R.layout.info_lieu);
            dialog.setTitle("Détails du lieu");
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.white);
            final TextView nomd = (TextView) dialog.findViewById(R.id.nomd);
            final TextView adressed = (TextView) dialog.findViewById(R.id.adressed);
            final TextView descriptiond = (TextView) dialog.findViewById(R.id.descriptiond);
            final TextView latituded = (TextView) dialog.findViewById(R.id.latituded);
            final TextView longituded = (TextView) dialog.findViewById(R.id.longituded);

            //Récupérer les informations sur le lieu
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
                    //Ajouter un évènement
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", true);
                    intent.putExtra("rrule", "FREQ=YEARLY");
                    intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra("title", "Don de sang");
                    intent.putExtra("eventLocation",adressed.getText());
                    startActivity(intent);

                    dialog.dismiss();

                }
            });

            btn_iti = (Button) dialog.findViewById(R.id.btn_iti);
            btn_iti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Lancer l'itinéraire avec Google Maps
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
