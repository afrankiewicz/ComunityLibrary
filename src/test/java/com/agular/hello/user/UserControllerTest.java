package com.agular.hello.user;

import com.agular.hello.CommunityLibraryApplicationTests;
import com.agular.hello.geolocation.GeolocationService;
import com.agular.hello.geolocation.GeolocationServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends CommunityLibraryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    GeolocationService geolocationService;

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldCreateUser() throws Exception {
        GeolocationServiceResponse geolocation = new GeolocationServiceResponse(
                "Warsaw", 1234, 3456, "Poland");
        Mockito.when(geolocationService.getCoordinates("Warsaw")).thenReturn(Optional.of(geolocation));
        AddUserRequest user = new AddUserRequest(RandomString.make(), RandomString.make(),
                registeredUserEmail, RandomString.make(), RandomString.make(), RandomString.make());

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}
