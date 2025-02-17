package com.kata.reservas.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private Long id;

    private String userName;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private String phone;
}
