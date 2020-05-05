package com.robot.pi.rest.system;

import android.content.Context;

import com.robot.pi.R;
import com.robot.pi.rest.rq.RestClient;
import com.robot.pi.rest.rq.RobotRequest;

/**
 * Created by Lechu on 09.10.2016.
 */
public class SystemInfoRestClient extends RestClient<RobotRequest> {
    private static final String SYSTEM_INFO = "systemInfo";

    public SystemInfoRestClient(Context context) {
        super(context);
        setResponseListener(new SystemInfoResponseListener(context));
    }

    public boolean getSystemInfo() {
        sendHttpRequest(buildUrl(), null);
        return true;
    }

    private String buildUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(SYSTEM_INFO);
        return stringBuilder.toString();
    }

}
