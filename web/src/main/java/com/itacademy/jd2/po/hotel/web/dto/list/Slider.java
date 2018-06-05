package com.itacademy.jd2.po.hotel.web.dto.list;

public class Slider {

    public static final String SESSION_SLIDER_NAME = "slider";

    private Double valueMin;

    private Double valueMax;

    private Double max;

    public Slider(final Double valueMin, final Double valueMax, final Double max) {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.max = max;
    }

    public Double getValueMin() {
        return valueMin;
    }

    public void setValueMin(final Double valueMin) {
        this.valueMin = valueMin;
    }

    public Double getValueMax() {
        return valueMax;
    }

    public void setValueMax(final Double valueMax) {
        this.valueMax = valueMax;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(final Double max) {
        this.max = max;
    }

}
