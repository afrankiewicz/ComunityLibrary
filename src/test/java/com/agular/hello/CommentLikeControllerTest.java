package com.agular.hello;

import com.agular.hello.DTO.CommentDto;
import com.agular.hello.DTO.CommentLikeDto;
import com.agular.hello.service.CommentLikeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentLikeControllerTest extends CommunityLibraryApplicationTests {

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldLikeComment() throws Exception {
        createRegisteredUser(registeredUserEmail);
        CommentDto comment = createRegisteredComment();

        mockMvc.perform(post("/likes/comments/{commentId}", comment.getId()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author.email").value(registeredUserEmail))
                .andExpect(jsonPath("$.comment.id").value(comment.getId()));
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldReturnCommentLike() throws Exception {
        createRegisteredUser(registeredUserEmail);
        CommentDto comment = createRegisteredComment();
        CommentLikeDto commentLike = commentLikeService.likeComment(comment.getId(), registeredUserEmail);

        mockMvc.perform(get("/likes/{commentLikeId}", commentLike.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author.email").value(registeredUserEmail))
                .andExpect(jsonPath("$.comment.id").value(comment.getId()));
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldReturnAllCommentLikes() throws Exception {
        createRegisteredUser(registeredUserEmail);
        CommentDto comment = createRegisteredComment();
        CommentLikeDto commentLike1 = commentLikeService.likeComment(comment.getId(), registeredUserEmail);

        mockMvc.perform(get("/likes/comments/{commentId}", comment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].comment.id").value(comment.getId()));
    }

    @Test
    @WithMockUser(value = registeredUserEmail)
    public void shouldDeleteCommentLike() throws Exception {
        createRegisteredUser(registeredUserEmail);
        CommentLikeDto commentLike = commentLikeService.likeComment(createRegisteredComment().getId(), registeredUserEmail);

        mockMvc.perform(delete("/likes/{commentLikeId}", commentLike.getId()))
                .andExpect(status().isNoContent());
    }
}
