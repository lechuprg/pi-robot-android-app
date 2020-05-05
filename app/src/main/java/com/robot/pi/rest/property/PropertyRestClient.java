package com.robot.pi.rest.property;

import android.content.Context;

import com.android.volley.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.ServiceDetail;
import com.robot.pi.rest.rq.RestClient;
import com.robot.pi.rest.rq.RobotRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Lechu on 09.10.2016.
 */
public class PropertyRestClient extends RestClient<JSONObject> {
    private static final String UPDATE_PROPERTIES = "updateProperties";
    private static final String READ_PROPERTIES = "readProperties";


    public PropertyRestClient(Context context) {
        super(context);
        setResponseListener(new PropertyResponseListener(context));
    }

    public boolean updateProperty(Map<String, String> updatedProperties) throws JsonProcessingException, JSONException {
        RobotRequest robotRequest = new RobotRequest();
        ServiceDetail serviceDetail = new ServiceDetail(OperationTypes.PROPERTY);
        for(Map.Entry<String,String> entyr: updatedProperties.entrySet()){
            serviceDetail.addKeyValue(entyr.getKey(), entyr.getValue());
        }
        robotRequest.addService(serviceDetail);
        ObjectMapper mapper = new ObjectMapper ();
        String s = mapper.writeValueAsString(robotRequest);
        sendHttpRequest(buildUrl(UPDATE_PROPERTIES), new JSONObject(s), Request.Method.POST);
        return true;
    }

    public boolean readProperties() {
        sendHttpRequest(buildUrl(READ_PROPERTIES), null);
        return true;
    }

    private String buildUrl(String action) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(action);
        return stringBuilder.toString();
    }

}
