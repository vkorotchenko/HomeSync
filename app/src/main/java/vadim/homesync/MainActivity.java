package vadim.homesync;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.joshsera.PadActivity;

import vadim.homesync.rest.RestClient;
import vadim.homesync.services.NetworkService;

import static vadim.homesync.common.Message.BEDROOM_LIGHTS;
import static vadim.homesync.common.Message.BLINDS_DOWN;
import static vadim.homesync.common.Message.BLINDS_STOP;
import static vadim.homesync.common.Message.BLINDS_UP;
import static vadim.homesync.common.Message.DINING_LIGHTS;
import static vadim.homesync.common.Message.KITCHEN_LIGHTS;
import static vadim.homesync.common.Message.LIVING_ROOM_LIGHTS;
import static vadim.homesync.common.Message.PATIO_LIGHTS;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    RestClient rest_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rest_client = new RestClient(this);

        startNetworkService();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void startNetworkService() {
        Intent serviceIntent = new Intent(this, NetworkService.class);
        serviceIntent.putExtra("inputExtra", "HomeSync Network Listener");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_remote:
                Intent intent2 = new Intent(this, PadActivity.class);
                startActivity(intent2);
                return true;

        }
        return false;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
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
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(provider, 400, 1, this);
                }

            }
        }
    }

    public void sendElectronicsBlindsUp(View view) {
        rest_client.sentElectronicsMsg(BLINDS_UP);
    }

    public void sendElectronicsBlindsStop(View view) {
        rest_client.sentElectronicsMsg(BLINDS_STOP);
    }

    public void sendElectronicsBlindsDown(View view) {
        rest_client.sentElectronicsMsg(BLINDS_DOWN);
    }

    public void sendElectronicsBedroomLights(View view) {
        rest_client.sentElectronicsMsg(BEDROOM_LIGHTS);
    }

    public void sendElectronicsLivingroomLights(View view) {
        rest_client.sentElectronicsMsg(LIVING_ROOM_LIGHTS);
    }

    public void sendElectronicsKitchenLights(View view) {
        rest_client.sentElectronicsMsg(KITCHEN_LIGHTS);
    }

    public void sendElectronicsDiningLights(View view) {
        rest_client.sentElectronicsMsg(DINING_LIGHTS);
    }

    public void sendElectronicsPatioLights(View view) {
        rest_client.sentElectronicsMsg(PATIO_LIGHTS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
