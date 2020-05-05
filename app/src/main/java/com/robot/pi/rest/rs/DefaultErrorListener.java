package com.robot.pi.rest.rs;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.logging.Logger;

/**
 * Created by Lechu on 17.08.2016.
 */
public class DefaultErrorListener implements Response.ErrorListener {
    private static final Logger LOGGER = Logger.getLogger(DefaultErrorListener.class.getName());
    @Override
    public void onErrorResponse(VolleyError error) {
        LOGGER.info(error.getMessage());
    }
}
