package com.robot.pi.fragment.left.btn;

import android.app.Activity;
import android.content.Context;
import android.icu.util.LocaleData;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.robot.pi.R;
import com.robot.pi.cache.CacheProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Lechu on 29.11.2016.
 */
public class TakePhotoButton implements View.OnTouchListener {
    CacheProperty cacheProperty;

    public TakePhotoButton(Context webView) {
        cacheProperty = new CacheProperty((Activity) webView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            v.setBackgroundResource(R.drawable.camera_on);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss"));
                downloadFile(cacheProperty.getIpAddress() + ":9000/?action=snapshot", "pi_photo_" + dateTime + ".jpg");
                v.setBackgroundResource(R.drawable.camera);
                return true;
            }
        } catch (Exception e) {
            v.setBackgroundResource(R.drawable.camera);
        }
        return false;
    }

    public void downloadFile(String fileURL, String fileName) {
        new Downloader(fileName, fileURL).execute();
    }
}
