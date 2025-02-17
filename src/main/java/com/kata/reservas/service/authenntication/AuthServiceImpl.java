package com.kata.reservas.service.authenntication;

import com.kata.reservas.dto.SignupRequestDTO;
import com.kata.reservas.dto.UserDto;
import com.kata.reservas.entity.RoleEntity;
import com.kata.reservas.entity.UserEntity;
import com.kata.reservas.enums.UserRole;
import com.kata.reservas.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto signup(SignupRequestDTO signupRequestDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(signupRequestDTO.getUserName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        RoleEntity role = new RoleEntity();
        role.setName(UserRole.CLIENT);
        user.setRoles(Set.of(role));
        user.setDisabled(false);
        user.setLocked(false);
        userRepository.save(user);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }


    @Override
    public Boolean presentByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
