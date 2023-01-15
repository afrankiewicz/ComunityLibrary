package com.agular.hello;

import com.agular.hello.DTO.UserDto;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void shouldAddUser() {
        UserDto user = userService.addUser(createUser(registeredUserEmail));

        Assert.assertEquals(userService.getUser(user.getId()).getId(), user.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddUserWhenEmailAlreadyUsed() {
        UserDto user = createRegisteredUser();

        userService.addUser(user);
    }

}
