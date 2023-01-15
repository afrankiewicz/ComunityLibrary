package com.agular.hello.service;

import com.agular.hello.DTO.UserDto;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto addUser(UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException(String.format("User with email: '%s' already exists.", user.getEmail()));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user.toModel()).toDto();
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found")).toDto();
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found")).toDto();
    }
}
