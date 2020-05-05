package com.robot.pi.rest.system;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.TextView;

import com.android.volley.Response;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.RobotResponse;
import com.robot.msg.response.ServiceDetail;
import com.robot.pi.R;
import com.robot.pi.rest.rs.ResponseListener;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Lechu on 09.10.2016.
 */
public class SystemInfoResponseListener extends ResponseListener implements com.android.volley.Response.Listener {

    private static final String ANDORID_APP_INFO = "<br/>This is version 1.0 of Robot Pi.";
    private static final String SYSTEM_INFO = "System Information";

    public SystemInfoResponseListener(Context context) {
        super(context);
    }

    @Override
    public void onResponse(Object response) {
        Spanned systemInfo = buildSystemInfo(response);
        TextView textView = getTextView();
        textView.setTextSize(12);
        textView.setText(systemInfo);
    }

    private Spanned buildSystemInfo(Object response) {
        RobotResponse robotResponse = extractResponse(response);

        StringBuilder sb = new StringBuilder();

        sb.append(SYSTEM_INFO);
        sb.append("<br/><br/>");
        for(ServiceDetail serviceDetail: robotResponse.getServiceDetails()){
            if(serviceDetail !=null && OperationTypes.SYSTEM_INFO.equals(serviceDetail.getServiceName())) {
                sb.append("\n\r");
                for(Map.Entry enty: serviceDetail.getKeyValue().entrySet()){
                    sb.append("<b>");
                    sb.append(enty.getKey());
                    sb.append("</b>");
                    sb.append(": ");
                    sb.append("<i>");
                    sb.append(enty.getValue());
                    sb.append("</i>");
                    sb.append("<br/>");
                }
            }
        }
        sb.append(ANDORID_APP_INFO);
        return Html.fromHtml(sb.toString());
    }

    private TextView getTextView() {
        return (TextView) ((Activity) context).findViewById(R.id.system_info_text);
    }
}
