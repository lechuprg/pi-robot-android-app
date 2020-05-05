package com.robot.pi.fragment.move.camera;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.common.collect.EvictingQueue;
import com.robot.pi.R;
import com.robot.pi.rest.camera.CameraRestClient;

import java.util.Map;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Created by Lechu on 20.09.2016.
 */
public class CameraTouch extends Observable {
    private static final Logger LOGGER = Logger.getLogger(CameraTouch.class.getName());
    RelativeLayout layout_joystick;
    JoyStickClass js;
    CameraRestClient restClient;
    Map<Integer, Integer> queue2;
    EvictingQueue moveQueue;

    public CameraTouch(Context context) {
        restClient = new CameraRestClient(context);
        addObserver(restClient);
        moveQueue = EvictingQueue.create(1);
    }
    public void createCameraView(Bundle savedInstanceState, View inflate) {

        layout_joystick = (RelativeLayout) inflate.findViewById(R.id.layout_joystick);
        js = new JoyStickClass(inflate.getContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(50, 50);
        js.setLayoutSize(450, 450);
        js.setLayoutAlpha(130);
        js.setStickAlpha(70);
        js.setOffset(20);
        js.setMinimumDistance(10);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    LOGGER.info("X : " + String.valueOf(js.getX()));
                    LOGGER.info("Y : " + String.valueOf(js.getY()));
                    if(js.getX()!=0 && js.getY()!=0) {
                        LOGGER.info("Move camera");
                        moveQueue.offer(new Pair(transformToAngel(js.getX()), transformToAngelY(js.getY())));
                        System.err.println("Move " + (transformToAngel(js.getX())) + " - " + (transformToAngelY(js.getY())));
                        notifyObservers(moveQueue);
                        setChanged();
                    }

                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    LOGGER.info("END");
                }
                return true;
            }
        });
    }

    private int transformToAngel(int x) {
        if(x<0) {
            return 90 - (int)Math.round((x*-1)/2 * 0.8);
        }
        return (int) Math.round(x/2 * 0.8) + 90;
    }

    private int transformToAngelY(int x) {
        if(x<0) {
            return (int)Math.round((x/2*-1) * 0.9) + 90;
        }
        return 90 - (int)  Math.round(x/2 * 0.9);
    }

}
//                    LOGGER.info("Angle : " + String.valueOf(js.getAngle()));
//                    LOGGER.info("Distance : " + String.valueOf(js.getDistance()));

//                    int direction = js.get8Direction();
//                    if (direction == JoyStickClass.STICK_UP) {
//                        LOGGER.info("Direction : Up");
//                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {
//                        LOGGER.info("Direction : Up Right");
//                    } else if (direction == JoyStickClass.STICK_RIGHT) {
//                        LOGGER.info("Direction : Right");
//                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
//                        LOGGER.info("Direction : Down Right");
//                    } else if (direction == JoyStickClass.STICK_DOWN) {
//                        LOGGER.info("Direction : Down");
//                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
//                        LOGGER.info("Direction : Down Left");
//                    } else if (direction == JoyStickClass.STICK_LEFT) {
//                        LOGGER.info("Direction : Left");
//                    } else if (direction == JoyStickClass.STICK_UPLEFT) {
//                        LOGGER.info("Direction : Up Left");
//                    } else if (direction == JoyStickClass.STICK_NONE) {
//                        LOGGER.info("Direction : Center");
//                    }