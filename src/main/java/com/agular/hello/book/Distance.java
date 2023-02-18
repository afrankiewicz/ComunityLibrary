package com.agular.hello.book;

public class Distance {
    private DistanceUnit unit;
    private Double value;

    public Distance(DistanceUnit unit, Double value) {
        this.unit = unit;
        this.value = value;
    }

    public DistanceUnit getUnit() {
        return unit;
    }

    public Double getValue() {
        return value;
    }
}
