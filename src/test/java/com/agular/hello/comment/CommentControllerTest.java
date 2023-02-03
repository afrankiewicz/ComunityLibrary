package com.agular.hello.comment;

import com.agular.hello.CommunityLibraryApplicationTests;
import com.agular.hello.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest extends CommunityLibraryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldAddComment() throws Exception {
        createRegisteredUser(registeredUserEmail);
        UserDto reviewee = createRegisteredUser();
        AddCommentRequest commentRequest = new AddCommentRequest(RandomString.make());

        mockMvc.perform(post("/comments/reviewee/{revieweeId}", reviewee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author.email").value(registeredUserEmail))
                .andExpect(jsonPath("$.reviewee.id").value(reviewee.getId()))
                .andExpect(jsonPath("$.text").value(commentRequest.getText()));
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldReturnComment() throws Exception {
        CommentDto comment = createRegisteredComment(registeredUserEmail);

        mockMvc.perform(get("/comments/{commentId}", comment.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldUpdateComment() throws Exception {
        CommentDto comment = createRegisteredComment(registeredUserEmail);
        AddCommentRequest commentRequest = new AddCommentRequest(RandomString.make());

        mockMvc.perform(put("/comments/{commentId}", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(commentRequest.getText()))
                .andExpect(jsonPath("$.author.id").value(comment.getAuthor().getId()));
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldDeleteComment() throws Exception {
        CommentDto comment = createRegisteredComment(registeredUserEmail);

        mockMvc.perform(delete("/comments/{commentId}", comment.getId()))
                .andExpect(status().isNoContent());
    }
}
