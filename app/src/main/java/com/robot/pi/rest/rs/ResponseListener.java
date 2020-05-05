package com.robot.pi.rest.rs;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.msg.response.RobotResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lechu on 22.09.2016.
 */
public class ResponseListener {
    private static final Logger LOGGER = Logger.getLogger(DefaultResponseListener.class.getName());
    protected Context context;
    ObjectMapper mapper;

    public ResponseListener(Context context) {
        this.context = context;
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected RobotResponse extractResponse(Object response) {
        LOGGER.log(Level.INFO, "Response: " + response.toString());
        if(JSONObject.class.isInstance(response)) {
            try {
                return mapper.readValue(response.toString(), RobotResponse.class);
            } catch (IOException e) {
                LOGGER.warning("Could not parse response.");
            }
        }
        return null;
    }
}
