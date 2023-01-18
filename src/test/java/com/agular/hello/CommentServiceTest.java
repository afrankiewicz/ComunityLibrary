package com.agular.hello;

import com.agular.hello.DTO.CommentDto;
import com.agular.hello.DTO.UserDto;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.service.CommentService;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class CommentServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    CommentService commentService;

    @Test
    public void shouldAddComment() {
        UserDto author = createRegisteredUser();
        UserDto reviewee = createRegisteredUser();

        CommentDto result = commentService.addComment(RandomString.make(), reviewee.getId(), author.getEmail());

        Assert.assertNotNull(result.getId());
        Assert.assertEquals(result.getAuthor().getEmail(), author.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddCommentWhenAuthorIsReviewee() {
        UserDto author = createRegisteredUser();

        commentService.addComment(RandomString.make(), author.getId(), author.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddCommentWhenCommentFromAuthorToRevieweeAlreadyExists() {
        UserDto author = createRegisteredUser();
        UserDto reviewee = createRegisteredUser();
        commentService.addComment(RandomString.make(), reviewee.getId(), author.getEmail());

        commentService.addComment(RandomString.make(), reviewee.getId(), author.getEmail());
    }

    @Test
    public void shouldUpdateComment() {
        CommentDto comment = createRegisteredComment();

        String commentText = RandomString.make();
        CommentDto updatedComment = commentService.updateComment(commentText, comment.getId(), comment.getAuthor().getEmail());

        Assert.assertEquals(updatedComment.getText(), commentText);
        Assert.assertEquals(updatedComment.getId(), comment.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotUpdateCommentWhenDifferentAuthor() {
        CommentDto comment = createRegisteredComment();

        commentService.updateComment(RandomString.make(), comment.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotUpdateCommentWhenCommentIdDoesNotExist() {
        CommentDto comment = createRegisteredComment();

        commentService.updateComment(RandomString.make(), new Random().nextLong(), comment.getAuthor().getEmail());
    }

    @Test
    public void shouldDeleteComment() {
        CommentDto comment = createRegisteredComment();

        commentService.deleteComment(comment.getId(), comment.getAuthor().getEmail());

        Assert.assertTrue(commentRepository.count() == 0);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotDeleteCommentWhenUserIsNotAuthor() {
        CommentDto comment = createRegisteredComment();

        commentService.deleteComment(comment.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotDeleteCommentWhenCommentIdDoesNotExist() {
        CommentDto comment = createRegisteredComment();

        commentService.deleteComment(new Random().nextLong(), comment.getAuthor().getEmail());
    }
}
