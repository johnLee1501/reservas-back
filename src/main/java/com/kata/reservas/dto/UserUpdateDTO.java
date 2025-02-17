package com.kata.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserUpdateDTO {
    @NotBlank(message = "Username es requerido")
    private String userName;

    @Email(message = "Email inválido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private String name;
    private String lastName;
    private String phone;

}
