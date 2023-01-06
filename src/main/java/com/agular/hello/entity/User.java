package com.agular.hello.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "First name must be provided")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @NotBlank(message = "Last name must be provided")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Email(message = "Email is not correct")
    @NotBlank(message = "Email must be provided")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @NotBlank(message = "Password must be provided")
    @Column(name = "password", nullable = false)
    private String password;

    @NonNull
    @NotBlank(message = "Street must be provided")
    @Column(name = "street", nullable = false)
    private String street;

    @NonNull
    @NotBlank(message = "City must be provided")
    @Column(name = "city", nullable = false)
    private String city;
}
