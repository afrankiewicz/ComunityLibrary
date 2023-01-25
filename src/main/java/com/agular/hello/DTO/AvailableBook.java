package com.agular.hello.DTO;

public class AvailableBook {

    private BookDto book;

    private double distance;

    private DistanceUnit distanceUnit;

    public AvailableBook(BookDto book, double distance) {
        this.book = book;
        this.distance = distance;
        this.distanceUnit = DistanceUnit.METER;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }
}
