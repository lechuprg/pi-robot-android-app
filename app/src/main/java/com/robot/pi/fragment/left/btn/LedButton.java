package com.robot.pi.fragment.left.btn;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.robot.pi.R;
import com.robot.pi.rest.rq.RestClient;

/**
 * Created by Lechu on 16.11.2016.
 */
public class LedButton implements View.OnTouchListener {
    public static boolean ledOn = false;
    private RestClient restClient;

    public LedButton() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (ledOn) {
                    restClient.sendHttpRequest(restClient.getBaseUrl() + "/turnOffLed", null);
                    v.setBackgroundResource(R.drawable.light);
                    ledOn = false;
                } else {
                    restClient.sendHttpRequest(restClient.getBaseUrl() + "/turnOnLed", null);
                    v.setBackgroundResource(R.drawable.light_on);
                    ledOn = true;
                }
                break;
        }
        return false;
    }

    public void setContext(Context context) {
        restClient = new RestClient(context);
    }
}