package com.kata.reservas.service.user;

import com.kata.reservas.dto.UserDto;
import com.kata.reservas.dto.UserUpdateDTO;
import com.kata.reservas.entity.UserEntity;
import com.kata.reservas.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findByUsername(userDto.getUserName()).isPresent()) {
            throw new RuntimeException("Username ya existe");
        }

        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya existe");
        }

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userEntity.setLocked(false);
        userEntity.setDisabled(false);
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(userUpdateDTO.getUserName() != null) {
            user.setUsername(userUpdateDTO.getUserName());
        }
        if(userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if(userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        UserEntity updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}
