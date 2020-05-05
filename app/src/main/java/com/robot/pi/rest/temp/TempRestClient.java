package com.robot.pi.rest.temp;

import android.content.Context;

import com.robot.pi.rest.rq.RestClient;
import com.robot.pi.rest.rq.RobotRequest;
import com.robot.pi.rest.rs.DefaultResponseListener;

/**
 * Created by Lechu on 17.08.2016.
 */
public class TempRestClient extends RestClient<RobotRequest>{
    private static final String TEMP_HISTORY_URL = "tempCheckHistorical";
    private static final String DAYS = "?days=";


    public TempRestClient(Context context) {
        super(context);
        setResponseListener(new DefaultResponseListener(context));
    }

    public boolean getHistoricalTemperature() {
        sendHttpRequest(buildUrl(30), null);
        return true;
    }

    private String buildUrl(int days) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(TEMP_HISTORY_URL);
//        stringBuilder.append(DAYS);
//        stringBuilder.append(1);
        return stringBuilder.toString();
    }
}
