package com.agular.hello.book;

public class AvailableBook {

    private BookDto book;
    private Double distance;
    private DistanceUnit distanceUnit;

    public AvailableBook(BookDto book, Distance distance) {
        this.book = book;
        this.distance = distance.getValue();
        this.distanceUnit = distance.getUnit();
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
