package com.kata.reservas.service.user;

import com.kata.reservas.dto.UserDto;
import com.kata.reservas.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);
}
