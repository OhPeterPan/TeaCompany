
package com.example.administrator.chadaodiancompany.chart_formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Collection;

/**
 * This formatter is used for passing an array of x-axis labels, on whole x steps.
 */
public class XAxisValueFormatter implements IAxisValueFormatter {
    private String[] mValues = new String[]{};
    private int mValueCount = 0;

    /**
     * An empty constructor.
     * Use `setValues` to set the axis labels.
     */
    public XAxisValueFormatter() {
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public XAxisValueFormatter(String[] values) {
        if (values != null)
            setValues(values);
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public XAxisValueFormatter(Collection<String> values) {
        if (values != null)
            setValues(values.toArray(new String[values.size()]));
    }

    public String getFormattedValue(float value, AxisBase axis) {
        int index = Math.round(value);

 /*       if (index < 0 || index >= mValueCount || index != (int)value||(index>0&&index<6))
            return "";*/

        return mValues[index];
    }

    public String[] getValues() {
        return mValues;
    }

    public void setValues(String[] values) {
        if (values == null)
            values = new String[]{};

        this.mValues = values;
        this.mValueCount = values.length;
    }
}
