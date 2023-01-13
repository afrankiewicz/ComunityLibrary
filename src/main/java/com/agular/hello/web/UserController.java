package com.agular.hello.web;

import com.agular.hello.DTO.UserDto;
import com.agular.hello.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
