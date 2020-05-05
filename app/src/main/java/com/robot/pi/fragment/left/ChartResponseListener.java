package com.robot.pi.fragment.left;

import android.graphics.Color;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXIndexComparator;
import com.robot.msg.response.OperationTypes;
import com.robot.msg.response.RobotResponse;
import com.robot.msg.response.ServiceDetail;
import com.robot.pi.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

/**
 * Created by Lechu on 02.10.2016.
 */

public class ChartResponseListener implements com.android.volley.Response.Listener {
    private static final Logger LOGGER = Logger.getLogger(ChartResponseListener.class.getName());
    private final ViewGroup rootView;
    private final ObjectMapper mapper;

    public ChartResponseListener(ViewGroup rootView) {
        this.rootView = rootView;
        mapper = new ObjectMapper();
    }

    @Override
    public void onResponse(Object response) {
        System.err.println("---------------+++" + response);
        LineChart lineChart = rootView.findViewById(R.id.chart);
        Map<String, String> stringStringMap = readResponse(response);

        LineData lineData = transformResponseToTemperature(stringStringMap);
        lineData.setValueTextColor(R.color.white);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        if (stringStringMap != null) {
            lineChart.getXAxis().setValues(new ArrayList<>(stringStringMap.keySet()));
            lineChart.getLayoutParams().height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            lineChart.setData(lineData);
            System.err.println("---------------+++ Data set");
            lineChart.invalidate(); // r
        }// efresh
    }

    private LineData transformResponseToTemperature(Map<String, String> stringStringMap) {
        List<Entry> entriesTemp = new ArrayList<>();
        List<Entry> entriesHum = new ArrayList<>();
        List<String> xIndex = new ArrayList<>();
        if (stringStringMap != null) {
            int i = 0;
//            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
            DateTimeFormatter expectedXFormat = DateTimeFormatter.ofPattern("EEE HH:mm");

            for (Map.Entry<String, String> data : stringStringMap.entrySet()) {
                try {
                    // turn your data into Entry objects
                    String temp = data.getValue().substring(0, data.getValue().lastIndexOf("|"));
                    String hum = data.getValue().substring(data.getValue().lastIndexOf("|") + 1);
                    String date = data.getKey().replace("CET ", "").replace("UTC ", "");
                    LocalDateTime parse = LocalDateTime.parse(date, dateTimeFormatter);
                    xIndex.add(parse.format(expectedXFormat));
                    entriesTemp.add(new Entry(Float.valueOf(temp), i));
                    entriesHum.add(new Entry(Float.valueOf(hum), i));
                    i++;
                } catch (Exception e) {
                }
            }
        }
        Collections.sort(entriesTemp, new EntryXIndexComparator());
        Collections.sort(entriesHum, new EntryXIndexComparator());
        ILineDataSet dataSetTemp = new LineDataSet(entriesTemp, "Temperature");
        ILineDataSet dataSetHum = new LineDataSet(entriesHum, "Humidity");
        dataSetHum.setValueTextColor(Color.rgb(82, 138, 229));
        dataSetTemp.setValueTextColor(Color.rgb(252, 0, 0));
        ((LineDataSet) dataSetTemp).setColor(Color.rgb(252, 0, 0));
        return new LineData(xIndex, asList(dataSetTemp, dataSetHum));
    }

    private Map<String, String> readResponse(Object response) {
        try {
            RobotResponse robotResponse = mapper.readValue(response.toString(), RobotResponse.class);
            for (ServiceDetail serviceDetail : robotResponse.getServiceDetails()) {
                if (serviceDetail != null && OperationTypes.TEMPERATURE_CHECK.equals(serviceDetail.getServiceName())) {
                    return serviceDetail.getKeyValue();
                }
            }
            LOGGER.info("No temp data provided...");
        } catch (IOException e) {
            LOGGER.warning("Could not parse response {}" + response);
        }
        return null;
    }
}
