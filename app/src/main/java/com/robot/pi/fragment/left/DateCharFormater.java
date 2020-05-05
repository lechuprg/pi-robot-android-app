package com.robot.pi.fragment.left;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lechu on 02.10.2016.
 */
public class DateCharFormater implements XAxisValueFormatter {
    public DateCharFormater() {
    }

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        Double l = Double.valueOf(original) * 1000l;
        Date date = new Date(l.longValue());
        SimpleDateFormat format = new SimpleDateFormat("d HH:mm", Locale.ENGLISH);

        return format.format(date);
    }
}
