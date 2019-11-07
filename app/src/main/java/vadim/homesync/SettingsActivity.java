package vadim.homesync;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Switch;

import vadim.homesync.settings.SettingsManager;

public class SettingsActivity extends Activity implements OnItemSelectedListener {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		EditText ipText = findViewById(R.id.ip_entry);
		EditText portText = findViewById(R.id.port_entry);
		EditText remoteText = findViewById(R.id.remote_address_entry);
		EditText networkText = findViewById(R.id.home_network_entry);
		EditText externalText = findViewById(R.id.external_address_entry);
		EditText externalPortText = findViewById(R.id.external_port_entry);
		Switch experimentalInput = findViewById(R.id.experimental_ai_input);

		// Restore preferences
	    ipText.setText(SettingsManager.getAddress(this));
	    portText.setText(SettingsManager.getPort(this));
	    remoteText.setText(SettingsManager.getRemoteAddress(this));
	    networkText.setText(SettingsManager.getHomeSsid(this));
	    externalText.setText(SettingsManager.getExternalIp(this));
	    externalPortText.setText(SettingsManager.getExternalPort(this));
		experimentalInput.setChecked(SettingsManager.getExperimentalAi(this));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	public void saveSettings(View view) {

		EditText ipText = findViewById(R.id.ip_entry);
		EditText portText = findViewById(R.id.port_entry);
		EditText remoteText = findViewById(R.id.remote_address_entry);
		EditText networkText = findViewById(R.id.home_network_entry);
		EditText externalText = findViewById(R.id.external_address_entry);
		EditText externalPortText = findViewById(R.id.external_port_entry);
		Switch experimentalInput = findViewById(R.id.experimental_ai_input);

		SettingsManager.saveAddress(this,ipText.getText().toString());
		SettingsManager.savePort(this,portText.getText().toString());
		SettingsManager.saveRemoteAddress(this,remoteText.getText().toString());
		SettingsManager.saveHomeSsid(this,networkText.getText().toString());
		SettingsManager.saveExternalAddress(this,externalText.getText().toString());
		SettingsManager.saveExternalPort(this,externalPortText.getText().toString());
		SettingsManager.saveExperimental(this,experimentalInput.isChecked());

		//close settings window
		finish();
	}

	@Override
	 public void onItemSelected(AdapterView<?> parent, View view,
                                int pos, long id) {
		SettingsManager.saveMsgType(this,parent.getItemAtPosition(pos).toString());
    }
	
	@Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
