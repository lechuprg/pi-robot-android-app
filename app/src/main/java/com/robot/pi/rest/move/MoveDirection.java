package com.robot.pi.rest.move;

import com.robot.pi.R;

/**
 * Created by Lechu on 18.08.2016.
 */
public enum MoveDirection {

    LEFT(R.id.moveLeft),
    RIGHT(R.id.moveRight),
    FORWARD(R.id.moveUp),
    BACKWARD(R.id.moveDown),
    STOP(R.id.moveStop);

    private int moveCode;

    MoveDirection(int moveCode) {
        this.moveCode = moveCode;
    }

    public static MoveDirection getByCode(int moveCode) {
        for (MoveDirection move : MoveDirection.values()) {
            if(move.moveCode == moveCode) {
                return move;
            }
        }
        return null;
    }
}
