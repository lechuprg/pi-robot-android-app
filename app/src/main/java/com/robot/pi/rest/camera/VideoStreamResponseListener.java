package com.robot.pi.rest.camera;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.android.volley.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.RobotResponse;
import com.robot.msg.response.Status;
import com.robot.pi.R;
import com.robot.pi.cache.CacheProperty;
import com.robot.pi.fragment.left.btn.PlayPauseButton;
import com.robot.pi.fragment.left.VideSliderFragment;
import com.robot.pi.rest.rs.DefaultResponseListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lechu on 16.11.2016.
 */
public class VideoStreamResponseListener implements Response.Listener {
    private static final Logger LOGGER = Logger.getLogger(DefaultResponseListener.class.getName());
    private final ViewGroup rootView;
    ObjectMapper mapper;
    CacheProperty cacheProperty;
    private final WebView webView;


    public VideoStreamResponseListener(WebView webView, ViewGroup rootView) {
        this.webView = webView;
        this.rootView = rootView;
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        cacheProperty = new CacheProperty((Activity)rootView.getContext());
    }

    @Override
    public void onResponse(Object response) {
        RobotResponse robotResponse = extractResponse(response);

        if(isResponseOk(robotResponse) && isStreamStart(robotResponse)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String string =  cacheProperty.getIpAddress() + webView.getContext().getResources().getString(R.string.robot_pi_cam_url);
            webView.setInitialScale(193);
            PlayPauseButton.streamOn=true;

            Button button = (Button) rootView.findViewById(R.id.startStopBtn);
            button.setBackgroundResource(R.drawable.pause);
            webView.loadUrl(string);

        } else {
            PlayPauseButton.streamOn = false;
            webView.loadData(VideSliderFragment.emptyPage(), "text/html", "UTF-8");
            Button button = (Button) rootView.findViewById(R.id.startStopBtn);
            button.setBackgroundResource(R.drawable.play);
        }
    }

    private boolean isResponseOk(RobotResponse robotResponse) {
        return robotResponse.getStatus().equals(Status.OK);
    }

    private boolean isStreamStart(RobotResponse robotResponse) {
        return robotResponse.getOperationType().equals(OperationTypes.CAMERA_ON);
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
