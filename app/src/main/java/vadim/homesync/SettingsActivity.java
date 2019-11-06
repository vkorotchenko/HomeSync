package vadim.homesync;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;

import vadim.homesync.settings.SettingsManager;

public class SettingsActivity extends Activity implements OnItemSelectedListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//load values
		EditText ipText = findViewById(R.id.ip_entry);
		EditText portText = findViewById(R.id.port_entry);
		EditText remoteText = findViewById(R.id.remote_address_entry);
		EditText networkText = findViewById(R.id.home_network_entry);
		EditText externalText = findViewById(R.id.external_address_entry);

		
		
		// Restore preferences
	    ipText.setText(SettingsManager.getAddress(this));
	    portText.setText(SettingsManager.getPort(this));
	    remoteText.setText(SettingsManager.getRemoteAddress(this));
	    networkText.setText(SettingsManager.getHomeNetwork(this));
	    externalText.setText(SettingsManager.getExternal(this));
	    	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	public void saveSettings(View view) {
		//save settings
		String address =  ((EditText)findViewById(R.id.ip_entry)).getText().toString();
		String port = ((EditText) findViewById(R.id.port_entry)).getText().toString();
		String remote = ((EditText) findViewById(R.id.remote_address_entry)).getText().toString();
		String network = ((EditText) findViewById(R.id.home_network_entry)).getText().toString();
		String external = ((EditText) findViewById(R.id.external_address_entry)).getText().toString();
		
		SettingsManager.saveAddress(this,address);
		SettingsManager.savePort(this,port);
		SettingsManager.saveRemoteAddress(this,remote);
		SettingsManager.saveHomeNetwork(this,network);
		SettingsManager.saveExternalAddress(this,external);


							
		//close settings window
		finish();
	}

	@Override
	 public void onItemSelected(AdapterView<?> parent, View view,
                                int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		SettingsManager.saveMsgType(this,parent.getItemAtPosition(pos).toString());
    }
	
	@Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
