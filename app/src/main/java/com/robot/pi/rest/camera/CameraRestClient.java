package com.robot.pi.rest.camera;

import android.content.Context;
import android.util.Pair;

import com.google.common.collect.EvictingQueue;
import com.robot.pi.R;
import com.robot.pi.rest.rq.RestClient;
import com.robot.pi.rest.rq.RobotRequest;
import com.robot.pi.rest.rs.DefaultResponseListener;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lechu on 17.08.2016.
 */
public class CameraRestClient extends RestClient<RobotRequest> implements Observer {
    private static final String MOVE_CAMERA_URL = "moveCameraAll?horizontalDegree=%s&verticalDegree=%s";

    Runnable runnable;
    ThreadPoolExecutor executorService;
    public CameraRestClient(Context context) {
        super(context);
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        executorService =  new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, queue, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

            }
        });
        setResponseListener(new DefaultResponseListener(context));
    }

    public boolean startStream() {
        sendHttpRequest(buildUrl("startVideo"), null);
        return true;
    }

    public boolean stopStream() {
        sendHttpRequest(buildUrl("stopVideo"), null);
        return true;
    }
    public boolean move(int horizontalDegree, int verticalDegree) {
        /**
         * This rounding is done to easier manipulate during driving
         */
        if(isNeerCenter(horizontalDegree) && isNeerCenter(verticalDegree))
        {
            sendHttpRequest(buildUrl(90, 90), null);
            return true;
        }
        sendHttpRequest(buildUrl(horizontalDegree, verticalDegree), null);
        return true;
    }

    private boolean isNeerCenter(int degree) {
        if (0>degree && degree<15) {
            return true;
        }
        return false;
    }

    private String buildUrl(int horizontalDegree, int verticalDegree) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(String.format(MOVE_CAMERA_URL, horizontalDegree, verticalDegree));
        return stringBuilder.toString();
    }

    private String buildUrl(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    @Override
    public void update(Observable observable, Object data) {
        final EvictingQueue<Pair<Integer, Integer>> queue = (EvictingQueue)data;
        if(executorService.getActiveCount()==0)     {

            runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        while (!queue.isEmpty()) {
                            Thread.sleep(500);
                            Pair<Integer, Integer> poll = queue.poll();
                            move(poll.first, poll.second);
                        }
                    } catch (InterruptedException e) {
                    }

                }
            };
           executorService.execute(runnable);
        }
    }
}
