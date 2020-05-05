package com.robot.pi.rest.rq;

import com.robot.msg.response.ServiceDetail;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lechu on 22.09.2016.
 */
public class RobotRequest extends JSONObject {
    List<ServiceDetail> serviceDetails;

    public void addService(ServiceDetail serviceDetail) {
        if(serviceDetails==null) {
            serviceDetails = new ArrayList<>(5);
        }
        serviceDetails.add(serviceDetail);
    }

    public List<ServiceDetail> getServiceDetails() {
        return serviceDetails;
    }

}
