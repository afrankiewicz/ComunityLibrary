package com.agular.hello.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String street;
    private String city;
    private Double cityLatitude;
    private Double cityLongitude;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String email, String password, String street, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
    }

    public UserDto(String firstName, String lastName, String email, String password, String street, String city, Double cityLatitude, Double cityLongitude) {
        this(firstName, lastName, email, password, street, city);
        this.cityLatitude = cityLatitude;
        this.cityLongitude = cityLongitude;
    }

    public UserDto(Long id, String firstName, String lastName, String email, String password, String street, String city, Double cityLatitude, Double cityLongitude) {
        this(firstName, lastName, email, password, street, city, cityLatitude, cityLongitude);
        this.id = id;
    }

    public UserModel toModel() {
        return new UserModel(id, firstName, lastName, email, password, street, city, cityLatitude, cityLongitude);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(Double cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public Double getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(Double cityLongitude) {
        this.cityLongitude = cityLongitude;
    }
}
