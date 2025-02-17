package com.kata.reservas.service.authenntication;


import com.kata.reservas.dto.SignupRequestDTO;
import com.kata.reservas.dto.UserDto;

public interface AuthService {
    UserDto signup(SignupRequestDTO signupRequestDTO);

    Boolean presentByUsername(String username);

}
