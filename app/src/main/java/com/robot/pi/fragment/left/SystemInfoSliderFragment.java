package com.robot.pi.fragment.left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.pi.R;
import com.robot.pi.rest.system.SystemInfoRestClient;

/**
 * Created by Lechu on 02.10.2016.
 */
public class SystemInfoSliderFragment extends Fragment {
    SystemInfoRestClient client ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new SystemInfoRestClient(inflater.getContext());
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.system_info_fragment, container, false);
        client.getSystemInfo();
        return rootView;
    }

}
