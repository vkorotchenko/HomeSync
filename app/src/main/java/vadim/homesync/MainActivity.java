package vadim.homesync;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.joshsera.PadActivity;

import vadim.homesync.rest.RestClient;
import vadim.homesync.settings.SettingsManager;
import vadim.homesync.util.ConnectionUtils;
import vadim.homesync.util.HttpUtils;

public class MainActivity extends AppCompatActivity {

    RestClient rest_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rest_client = new RestClient(this);

        //save external address if on WiFi
        populateExternalIp(this);

        if(SettingsManager.getExperimentalAi(this)) {
            //close blinds when leave WiFi

            //Open blinds when alarm goes off and connected to WiFi

            //turn on lights when connect to Wifi

        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void populateExternalIp(Context context) {
        if(ConnectionUtils.isConnectedToHomeWiFi(context)) {
            HttpUtils.setExternal(context);
        }
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

    public void sendElectronicsBlindsUp(View view) {
        rest_client.sentElectronicsMsg("BLINDS_UP");
    }
    public void sendElectronicsBlindsStop(View view) {
        rest_client.sentElectronicsMsg("BLINDS_STOP");
    }
    public void sendElectronicsBlindsDown(View view) {
        rest_client.sentElectronicsMsg("BLINDS_DOWN");
    }
    public void sendElectronicsBedroomLights(View view) {
        rest_client.sentElectronicsMsg("BEDROOM_LIGHTS");
    }
    public void sendElectronicsLivingroomLights(View view) {
        rest_client.sentElectronicsMsg("LIVINGROOM_LIGHTS");
    }
    public void sendElectronicsKitchenLights(View view) {
        rest_client.sentElectronicsMsg("KITCHENK_LIGHTS");
    }
    public void sendElectronicsDiningLights(View view) {
        rest_client.sentElectronicsMsg("DINING_LIGHTS");
    }
    public void sendElectronicsPatioLights(View view) {
        rest_client.sentElectronicsMsg("PATIO_LIGHTS");
    }
}
