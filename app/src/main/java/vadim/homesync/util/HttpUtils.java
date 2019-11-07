package vadim.homesync.util;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import vadim.homesync.settings.SettingsManager;

public class HttpUtils {

    public static void sendMsg(final String uri, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HttpUtils",String.format("Get call completed to %s", uri));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HttpUtils",String.format("Get call failed: %s", error.getMessage()));
            }
        });
        queue.add(stringRequest);

    }

    public static void setExternal(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String uri = "http://checkip.amazonaws.com/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HttpUtils",String.format("Get call completed to %s", uri));
                        // Display the first 500 characters of the response string.
                        SettingsManager.saveExternalAddress(context,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HttpUtils",String.format("Get call failed: %s", error.getMessage()));
            }
        });
        queue.add(stringRequest);

    }
}