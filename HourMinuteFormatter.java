package com.example.studyplanner.ui.main;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.concurrent.TimeUnit;

/**
 * A custom formatter for MPAndroidChart that converts a value (in milliseconds)
 * into a human-readable "Xh Ym" format.
 */
public class HourMinuteFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {

        long millis = (long) value;

        if (millis == 0) {
            return "";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;

        return hours + "h " + minutes + "m";
    }
}
