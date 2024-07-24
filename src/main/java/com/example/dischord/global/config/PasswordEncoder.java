package com.example.dischord.global.config;


public interface PasswordEncoder {


    String encode(String password);

    boolean matches(String password, String hashed);
}
