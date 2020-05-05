package com.robot.pi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.pi.R;
import com.robot.pi.fragment.left.PropertiesSliderFragment;
import com.robot.pi.fragment.left.SystemInfoSliderFragment;
import com.robot.pi.fragment.left.TemperatureSliderFragment;
import com.robot.pi.fragment.left.VideSliderFragment;

public class LeftSlider extends Fragment {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 4;
    VideSliderFragment videSliderFragment = new VideSliderFragment();
    TemperatureSliderFragment temperatureSliderFragment = new TemperatureSliderFragment();
    PropertiesSliderFragment propertiesSliderFragment = new PropertiesSliderFragment();
    SystemInfoSliderFragment systemInfoSliderFragment = new SystemInfoSliderFragment();
    public LeftSlider() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onViewCreated(container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_left_page_viewer, container, false);
        ViewPager mViewPager = (ViewPager) inflate.findViewById(R.id.pager);
        mViewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        return inflate;

//    return ;
}

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return videSliderFragment;
            case 1:
                return temperatureSliderFragment;
            case 2:
                return propertiesSliderFragment;
            case 3:
                return systemInfoSliderFragment;
        }
        return videSliderFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
}
