package com.agular.hello.shared.security;

public class SecurityConstants {

    public static final int TOKEN_EXPIRATION = 3600 * 24 * 7 * 1000; // 7days
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/users/register"; // Public path that clients can use to register.
}
