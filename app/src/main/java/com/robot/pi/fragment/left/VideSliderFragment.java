package com.robot.pi.fragment.left;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.robot.pi.R;
import com.robot.pi.cache.CacheProperty;
import com.robot.pi.fragment.left.btn.LedButton;
import com.robot.pi.fragment.left.btn.PlayPauseButton;
import com.robot.pi.fragment.left.btn.TakePhotoButton;
import com.robot.pi.rest.camera.CameraRestClient;
import com.robot.pi.rest.camera.VideoStreamResponseListener;

/**
 * Created by Lechu on 02.10.2016.
 */
public class VideSliderFragment extends Fragment {

    WebView webView;
    PlayPauseButton playPauseButton = new PlayPauseButton();
    LedButton ledButton = new LedButton();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.video_stream_fragment, container, false);

        webView = rootView.findViewById(R.id.webCamView);
        initVideoPlayBtn(rootView);

        Button camera = rootView.findViewById(R.id.camera);
        camera.setOnTouchListener(new TakePhotoButton(inflater.getContext()));

        Button led = rootView.findViewById(R.id.light);
        ledButton.setContext( webView.getContext());
        led.setOnTouchListener(ledButton);
        webView.loadData(emptyPage(), "text/html", "UTF-8");
        return rootView;
    }



    private void initVideoPlayBtn(ViewGroup rootView) {
        VideoStreamResponseListener videoStreamResponseListener = new VideoStreamResponseListener(webView, rootView);
        CameraRestClient cameraRestClient = new CameraRestClient(webView.getContext());
        cameraRestClient.setResponseListener(videoStreamResponseListener);
        playPauseButton.setCamerRestClient(cameraRestClient);
        Button startStopBtn = rootView.findViewById(R.id.startStopBtn);
        startStopBtn.setBackgroundResource(R.drawable.play);
        startStopBtn.setOnTouchListener(playPauseButton);
    }


    public static String emptyPage() {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><body align=\"center\" style=\"color:#005A31;margin-top:130px;\">");
        builder.append("Welcome! </br>");
        builder.append("Streaming stoped.");
        builder.append("</body></html>");
        return builder.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.reload();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.stopLoading();
        }
    }
}
