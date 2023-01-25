package com.agular.hello.DTO;

import com.agular.hello.entity.UserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    private Long id;

    @NotBlank(message = "First name must be provided")
    private String firstName;

    @NotBlank(message = "Last name must be provided")
    private String lastName;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email must be provided")
    private String email;

    @NotBlank(message = "Password must be provided")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Street must be provided")
    private String street;

    @NotBlank(message = "City must be provided")
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

    public UserDto(Long id, String firstName, String lastName, String email, String password, String street, String city, Double cityLatitude, Double cityLongitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
        this.cityLatitude = cityLatitude;
        this.cityLongitude = cityLongitude;
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
