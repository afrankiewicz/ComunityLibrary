package com.agular.hello.user;

import com.agular.hello.shared.exceptions.BadRequestException;
import com.agular.hello.geolocation.GeolocationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeolocationService geolocationService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, GeolocationService geolocationService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.geolocationService = geolocationService;
    }

    public UserDto addUser(AddUserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException(String.format("User with email: '%s' already exists.", userRequest.getEmail()));
        }

        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        UserModel userModel = new UserModel(userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getEmail(), userRequest.getPassword(), userRequest.getStreet(), userRequest.getCity());

        geolocationService.getCoordinates(userRequest.getCity())
                .ifPresent(coordinates -> {
                    userModel.setCityLongitude(coordinates.getLat());
                    userModel.setCityLatitude(coordinates.getLat());
                });

        return userRepository.save(userModel).toDto();
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
