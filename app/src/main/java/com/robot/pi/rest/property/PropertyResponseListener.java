package com.robot.pi.rest.property;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.RobotResponse;
import com.robot.msg.response.ServiceDetail;
import com.robot.pi.R;
import com.robot.pi.rest.rs.ResponseListener;

import java.util.Map;

/**
 * Created by Lechu on 09.10.2016.
 */
public class PropertyResponseListener extends ResponseListener implements Response.Listener {

    public PropertyResponseListener(Context context) {
        super(context);
    }

    @Override
    public void onResponse(Object response) {
        RelativeLayout relativeLayout = getRelativeLayoutWrapper();
        relativeLayout.setMinimumHeight(1000);
        //if we are connected we remove temp button and save oryginal button
        View viewById = (Button)relativeLayout.findViewById(R.id.tmpSaveBtn);
        ViewGroup parent = (ViewGroup) viewById.getParent();
        parent.removeView(viewById);
        buildSystemInfo(response, relativeLayout);
        Button btnTag = new Button(context);
        addSaveButton(relativeLayout, btnTag);
    }

    private void addSaveButton(RelativeLayout relativeLayout, Button btnTag) {
        btnTag.setText("Update params" );
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        relativeLayout.addView(btnTag, params);
        btnTag.setOnClickListener(new SaveButton());
    }

    private void buildSystemInfo(Object response, RelativeLayout relativeLayout) {
        RobotResponse robotResponse = extractResponse(response);

        for(ServiceDetail serviceDetail: robotResponse.getServiceDetails()){
            if(serviceDetail !=null && OperationTypes.PROPERTY.equals(serviceDetail.getServiceName())) {
                int i = 1;
                for(Map.Entry enty: serviceDetail.getKeyValue().entrySet()){
                    createEdit(i++, String.valueOf(enty.getKey()), String.valueOf(enty.getValue()), relativeLayout);
                }
            }
        }
    }

    private void createEdit(int id, String propName, String value, RelativeLayout view) {

        TextView textView = new TextView(context);
        textView.setText(propName);
        textView.setTextSize(9);
        textView.setId(id+100);
        EditText editText = new EditText(context);
        editText.setText(value);
        editText.setWidth(view.getWidth());
        editText.setTextSize(10);
        editText.setId(id);
        editText.getBackground().mutate().setColorFilter(255, PorterDuff.Mode.SRC_ATOP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params2.addRule(RelativeLayout.BELOW, RelativeLayout.TRUE);
        params2.topMargin = 50 * id + 60;
        view.addView(textView, params2);
        params2.topMargin = 50 * id + (32*id) + 92;
        view.addView(editText, id, params2);
    }

    private RelativeLayout  getRelativeLayoutWrapper() {
        return (RelativeLayout) ((Activity) context).findViewById(R.id.properties_wrapper);
    }
}
