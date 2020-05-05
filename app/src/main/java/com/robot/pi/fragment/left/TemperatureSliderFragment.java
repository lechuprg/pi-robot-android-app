package com.robot.pi.fragment.left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.pi.R;
import com.robot.pi.rest.temp.TempRestClient;

/**
 * Created by Lechu on 02.10.2016.
 */
public class TemperatureSliderFragment extends Fragment {
    TempRestClient client;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Override
    public void onResume() {
        super.onResume();
        getTemperatureDateFromPi(inflater, getRootView(inflater, container));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        ViewGroup rootView = getRootView(inflater, container);
        getTemperatureDateFromPi(inflater, rootView);
        return rootView;
    }

    private ViewGroup getRootView(LayoutInflater inflater, ViewGroup container) {
        return (ViewGroup) inflater.inflate(
                    R.layout.temperature_fragment, container, false);
    }


    private void getTemperatureDateFromPi(LayoutInflater inflater, ViewGroup rootView) {
        client = new TempRestClient(inflater.getContext());
        client.setResponseListener(new ChartResponseListener(rootView));
        client.getHistoricalTemperature();
    }
}
