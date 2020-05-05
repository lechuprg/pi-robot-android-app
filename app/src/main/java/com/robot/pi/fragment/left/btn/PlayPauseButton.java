package com.robot.pi.fragment.left.btn;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.robot.pi.R;
import com.robot.pi.rest.camera.CameraRestClient;
import com.robot.pi.rest.camera.VideoStreamResponseListener;

/**
 * Created by Lechu on 16.11.2016.
 */
public class PlayPauseButton implements View.OnTouchListener {
    public static boolean streamOn = false;
    private CameraRestClient cameraRestClient;

    public PlayPauseButton() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                streamOn = streamOn ? false : true;
                if (streamOn) {
                    cameraRestClient.startStream();
                    v.setBackgroundResource(R.drawable.pause);
                } else {
                    cameraRestClient.stopStream();
                    v.setBackgroundResource(R.drawable.play);
                }
                return true;
        }
        return false;
    }

    public void setCamerRestClient(CameraRestClient cameraRestClient) {
        this.cameraRestClient = cameraRestClient;
    }
}
