package com.robot.pi.rest.rq;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robot.pi.R;
import com.robot.pi.cache.CacheProperty;
import com.robot.pi.rest.rs.DefaultErrorListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lechu on 16.08.2016.
 */
public class RestClient<T extends JSONObject> {
    RequestQueue queue;
    Response.Listener listener;
    Response.ErrorListener errorListener;
    CacheProperty cacheProperty;
    protected String baseUrl;
    protected String cameraUrl;

    public RestClient(Context context) {
        cacheProperty = new CacheProperty((Activity)context);
        queue = Volley.newRequestQueue(context);
        errorListener = new DefaultErrorListener();
        String ipAddress = cacheProperty.getIpAddress();
        System.err.println("------------>" + ipAddress);
        baseUrl = ipAddress + context.getResources().getString(R.string.robot_pi_url);
        cameraUrl = ipAddress + context.getResources().getString(R.string.robot_pi_cam_url);
        System.err.println("------------>+" + cameraUrl);
    }
    public void sendHttpRequest(String url, T jsonObject, int method) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(method, url, jsonObject, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(stringRequest);
    }
    public void sendHttpRequest(String url, T jsonObject) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, listener, errorListener);
        queue.add(stringRequest);
    }

    public void setResponseListener(Response.Listener responseListener) {
        listener = responseListener;
    }

    public void setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener= errorListener;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
