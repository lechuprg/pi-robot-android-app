package com.robot.pi.rest.rs;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.RobotResponse;
import com.robot.msg.response.ServiceDetail;
import com.robot.pi.R;

import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lechu on 17.08.2016.
 */
public class DefaultResponseListener extends ResponseListener implements com.android.volley.Response.Listener {
    private static final Logger LOGGER = Logger.getLogger(DefaultResponseListener.class.getName());

    TextView textView;


    public DefaultResponseListener(Context context) {
        super(context);
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void onResponse(Object response) {
        this.textView = getTextView();
        LOGGER.log(Level.INFO, "Response: " + response.toString());
        if (JSONObject.class.isInstance(response)) {
            LOGGER.log(Level.INFO, "Response info writing");
            try {
                RobotResponse robotResponse = mapper.readValue(response.toString(), RobotResponse.class);
                textView.setText(buildTextMsg(robotResponse));
                textView.setTextSize(9);
                textView.setPadding(5, 5, 5, 5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            textView.setText("Could not parse response...");
        }
    }

    private String buildTextMsg(RobotResponse robotResponse) {
        StringBuilder sb = new StringBuilder();

        sb.append(robotResponse.getOperationType().name().replace("_", " "));
        for (ServiceDetail serviceDetail : robotResponse.getServiceDetails()) {
            if (serviceDetail != null && OperationTypes.TEMPERATURE_CHECK.equals(serviceDetail.getServiceName())) {
                sb.append("\n");
                sb.append("Temperature: ");
                sb.append(formatNumber(serviceDetail.getKeyValue().get("Temperature")));
                sb.append(" C");
                sb.append("\n");
                sb.append("Humidity: ");
                sb.append(formatNumber(serviceDetail.getKeyValue().get("Humidity")));
                sb.append(" %");
            }
            if (serviceDetail != null && OperationTypes.DISTANCE_CHECK.equals(serviceDetail.getServiceName())) {
                sb.append("\n");
                sb.append("Distance: ");
                sb.append(formatNumber(serviceDetail.getKeyValue().get("Distance")));
                sb.append(" cm");
            }
        }
        return sb.toString();
    }

    private String formatNumber(String number) {
        try {
            return String.format("%.2f", Double.valueOf(number));
        } catch (Exception e) {
            return number;
        }
    }

    private TextView getTextView() {
        return (TextView) ((Activity) context).findViewById(R.id.systemInformation);
    }
}
