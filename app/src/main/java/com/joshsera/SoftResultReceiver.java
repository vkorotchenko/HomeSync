package com.joshsera;

import android.os.Handler;
import android.os.ResultReceiver;

public class SoftResultReceiver extends ResultReceiver {
	//
	//private SoftListener list;
	
	public SoftResultReceiver(Handler handler) {
		super(handler);
	}
}
