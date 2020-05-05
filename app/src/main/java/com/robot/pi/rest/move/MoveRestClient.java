package com.robot.pi.rest.move;

import android.content.Context;

import com.robot.pi.R;
import com.robot.pi.rest.rq.RestClient;
import com.robot.pi.rest.rq.RobotRequest;
import com.robot.pi.rest.rs.DefaultResponseListener;

/**
 * Created by Lechu on 17.08.2016.
 */
public class MoveRestClient extends RestClient<RobotRequest>{
    private static final String MOVE_DC_URL = "moveDc?move=";

    public MoveRestClient(Context context) {
        super(context);
        setResponseListener(new DefaultResponseListener(context));
    }

    public boolean move(MoveDirection moveDirection) {
        sendHttpRequest(buildUrl(moveDirection.name()), null);
        return true;
    }

    private String buildUrl(String direction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(MOVE_DC_URL);
        stringBuilder.append(direction);
        return stringBuilder.toString();
    }


}
