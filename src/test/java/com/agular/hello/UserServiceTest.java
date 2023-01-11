package com.agular.hello;

import com.agular.hello.entity.User;
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
        // given
        User user = createUser(registeredUserEmail);
        // when
        userService.addUser(user);
        // then
        Assert.assertTrue(userService.getUser(user.getId()).isPresent());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddUserWhenEmailAlreadyUsed(){
        // given
        User user = createRegisteredUser();
        // when
        userService.addUser(user);
    }

}
