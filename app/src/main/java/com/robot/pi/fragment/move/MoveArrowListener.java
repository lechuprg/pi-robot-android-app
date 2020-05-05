package com.robot.pi.fragment.move;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.robot.pi.rest.move.MoveDirection;
import com.robot.pi.rest.move.MoveRestClient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lechu on 17.08.2016.
 */
public class MoveArrowListener implements View.OnTouchListener {
    private static final Logger LOGGER = Logger.getLogger(MoveArrowListener.class.getName());
    private final MoveDirection moveDirection;
    MoveRestClient restClient;
    public MoveArrowListener(Context context, int moveDirection) {
        this.moveDirection =  MoveDirection.getByCode(moveDirection);
        restClient = new MoveRestClient(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                restClient.move(moveDirection);
                LOGGER.log(Level.INFO, "Move done in direction: " + moveDirection);
                return true;
            case MotionEvent.ACTION_UP:
                if(!moveDirection.equals(MoveDirection.STOP)) {
                    restClient.move(MoveDirection.STOP);
                    LOGGER.log(Level.INFO, "Robot stop.");
                }
                return true;
        }
        return false;
    }
}
