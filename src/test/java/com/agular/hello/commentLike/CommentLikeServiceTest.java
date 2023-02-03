package com.agular.hello.commentLike;


import com.agular.hello.CommunityLibraryApplicationTests;
import com.agular.hello.comment.CommentDto;
import com.agular.hello.shared.exceptions.BadRequestException;
import com.agular.hello.user.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class CommentLikeServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private CommentLikeRepository commentLikeRepository;


    @Test
    public void shouldLikeComment() {
        CommentDto comment = createRegisteredComment();
        UserDto user = createRegisteredUser();

        CommentLikeDto commentLike = commentLikeService.likeComment(comment.getId(), user.getEmail());

        Assert.assertNotNull(commentLike.getId());
        Assert.assertEquals(commentLike.getAuthor().getEmail(), user.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotLikeCommentWhenLikeAuthorIsCommentAuthor() {
        CommentDto comment = createRegisteredComment();

        commentLikeService.likeComment(comment.getId(), comment.getAuthor().getEmail());
    }

    @Test
    public void shouldReturnCommentLike() {
        CommentDto comment = createRegisteredComment();
        CommentLikeDto commentLike = commentLikeService.likeComment(comment.getId(), createRegisteredUser().getEmail());

        CommentLikeDto result = commentLikeService.getCommentLike(commentLike.getId());

        Assert.assertEquals(result.getId(), commentLike.getId());
        Assert.assertEquals(result.getComment().getId(), comment.getId());
        Assert.assertEquals(result.getAuthor().getEmail(), commentLike.getAuthor().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnCommentLikeWhenCommentLikeIdDoesNotExist() {
        commentLikeService.getCommentLike(new Random().nextLong());
    }

    @Test
    public void shouldReturnCommentLikes() {
        Long commentId = createRegisteredComment().getId();
        CommentLikeDto commentLike1 = commentLikeService.likeComment(commentId, createRegisteredUser().getEmail());
        commentLikeService.likeComment(commentId, createRegisteredUser().getEmail());

        List<CommentLikeDto> result = commentLikeService.getCommentLikes(commentId);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(1).getComment().getId(), commentLike1.getComment().getId());
    }

    @Test
    public void shouldDeleteCommentLike() {
        UserDto author = createRegisteredUser();
        CommentLikeDto commentLike = commentLikeService.likeComment(createRegisteredComment().getId(), author.getEmail());

        commentLikeService.deleteCommentLike(commentLike.getId(), author.getEmail());

        Assert.assertEquals(0, commentLikeRepository.count());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotDeleteCommentLikeWhenUserIsNotAuthor() {
        CommentLikeDto commentLike = commentLikeService.likeComment(createRegisteredComment().getId(), createRegisteredUser().getEmail());

        commentLikeService.deleteCommentLike(commentLike.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotDeleteCommentLikeWhenCommentLikeIdDoesNotExist() {
        CommentLikeDto commentLike = commentLikeService.likeComment(createRegisteredComment().getId(), createRegisteredUser().getEmail());

        commentLikeService.deleteCommentLike(new Random().nextLong(), commentLike.getAuthor().getEmail());
    }
}
