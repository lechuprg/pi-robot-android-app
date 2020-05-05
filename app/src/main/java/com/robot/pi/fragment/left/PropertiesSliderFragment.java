package com.robot.pi.fragment.left;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robot.pi.R;
import com.robot.pi.cache.CacheProperty;
import com.robot.pi.rest.property.PropertyRestClient;
import com.robot.pi.rest.property.SaveButton;

/**
 * Created by Lechu on 02.10.2016.
 */
public class PropertiesSliderFragment extends Fragment {

    PropertyRestClient propertyRestClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        propertyRestClient = new PropertyRestClient(inflater.getContext());
        propertyRestClient.readProperties();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.properties_fragment, container, false);
        RelativeLayout viewById = (RelativeLayout) rootView.findViewById(R.id.properties_wrapper);
        CacheProperty cacheProperty = new CacheProperty(this.getActivity());
        createEdit(0, "Ip Address", cacheProperty.getJustIpAddress(), viewById,rootView.getContext());
        addSaveButton(viewById, new Button(inflater.getContext()));
        return rootView;
    }


    private void addSaveButton(RelativeLayout relativeLayout, Button btnTag) {
        btnTag.setText("Update params" );
        btnTag.setId(R.id.tmpSaveBtn);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        relativeLayout.addView(btnTag, params);
        btnTag.setOnClickListener(new SaveButton());
    }

    private void createEdit(int id, String propName, String value, RelativeLayout view, Context context) {
        TextView textView = new TextView(context);
        textView.setText(propName);
        textView.setTextSize(9);
        textView.setId(R.id.textView);
        EditText editText = new EditText(context);
        editText.setText(value);
        editText.setWidth(1330);
        editText.setTextSize(10);
        editText.setId(id);
        editText.getBackground().mutate().setColorFilter(12, PorterDuff.Mode.SRC_ATOP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params2.addRule(RelativeLayout.BELOW, RelativeLayout.TRUE);
        params2.topMargin = 0;
        view.addView(textView, params2);
        params.topMargin = 10;
        view.addView(editText, id, params);
    }

}
