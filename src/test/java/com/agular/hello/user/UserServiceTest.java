package com.agular.hello.user;

import com.agular.hello.CommunityLibraryApplicationTests;
import com.agular.hello.geolocation.GeolocationService;
import com.agular.hello.geolocation.GeolocationServiceResponse;
import com.agular.hello.shared.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

public class UserServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    private UserService userService;

    @MockBean
    private GeolocationService geolocationService;

    @Test
    public void shouldAddUser() {
        GeolocationServiceResponse geolocation = new GeolocationServiceResponse(
                "Warsaw", Math.random(), Math.random(), "Poland");
        Mockito.when(geolocationService.getCoordinates(anyString())).thenReturn(Optional.of(geolocation));

        UserDto result = userService.addUser(createAddUserRequest(registeredUserEmail));

        Assert.assertNotNull(result.getId());
        Assert.assertNotNull(result.getCityLongitude());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddUserWhenEmailAlreadyUsed() {
        UserDto user = createRegisteredUser();

        userService.addUser(createAddUserRequest(user.getEmail()));
    }

}
