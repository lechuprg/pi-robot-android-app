package com.robot.pi.fragment.left.btn;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lechu on 29.11.2016.
 */
public class Downloader extends AsyncTask<String, Void, Boolean> {

    private String fileName;
    private String fileURL;

    public Downloader(String fileName, String fileURL) {
        this.fileName = fileName;
        this.fileURL = fileURL;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/piRobot/img");
            dir.mkdirs();
            File file = new File(dir, fileName);
            if ( file.exists() ) {
                file.delete();
            }
            InputStream inputStream = new URL(fileURL).openStream();
            FileOutputStream f = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) >= 0) {
                f.write(buffer, 0, bytesRead);
            }
            f.flush();
            f.close();
            inputStream.close();
        } catch (Exception e) {
            Log.d("Downloader", e.getMessage());
            return false;
        }
        return true;
    }
}
