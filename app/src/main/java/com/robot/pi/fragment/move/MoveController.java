package com.robot.pi.fragment.move;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.pi.R;
import com.robot.pi.fragment.move.camera.CameraTouch;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MoveController extends Fragment {
    private static final Logger LOGGER = Logger.getLogger(MoveController.class.getName());

    CameraTouch touch;

    public MoveController() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_move_controller, container, false);
        touch = new CameraTouch(inflate.getContext());
        touch.createCameraView(savedInstanceState, inflate);
        setMoveButtonsListener(inflate);
        return inflate;
    }


    private void setMoveButtonsListener(View inflate) {
        setListnerOnButton(inflate, R.id.moveDown);
        setListnerOnButton(inflate, R.id.moveUp);
        setListnerOnButton(inflate, R.id.moveLeft);
        setListnerOnButton(inflate, R.id.moveRight);
        setListnerOnButton(inflate, R.id.moveStop);
        LOGGER.log(Level.INFO, "Move buttons setUp done.");
    }

    private void setListnerOnButton(View inflate, int id) {
        View viewById = inflate.findViewById(id);
        viewById.setOnTouchListener(new MoveArrowListener(inflate.getContext(), id));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}