package com.robot.pi.rest.property;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robot.pi.cache.CacheProperty;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lechu on 04.11.2016.
 */
public class SaveButton implements View.OnClickListener {
    PropertyRestClient restClient;
    CacheProperty cacheProperty;
    @Override
    public void onClick(View v) {
        cacheProperty = new CacheProperty((Activity) v.getContext());
        EditText ipAddrs = (EditText)v.getRootView().findViewById(0);

        cacheProperty.setIpAddress(ipAddrs.getText().toString());
        restClient = new PropertyRestClient(v.getContext());
        Map<String, String> map = new HashMap<>();
        for(int i = 1; i<11; i++ ) {
            EditText viewById = (EditText)v.getRootView().findViewById(i);
            if(viewById!=null) {
                TextView propName = (TextView)v.getRootView().findViewById(i + 100);
                map.put(propName.getText().toString(), viewById.getText().toString());
            } else  {
                break;
            }

        }

        try {
            restClient.updateProperty(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
