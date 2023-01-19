package com.agular.hello.repositiry;

import com.agular.hello.entity.CommentLikeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeModel, Long> {

    List<CommentLikeModel> getCommentLikesByCommentId(Long commentId);

//    @Query(value = "SELECT COUNT(*) FROM comments_likes JOIN comments ON comments_likes.comment_id = comments.id " +
//            "WHERE reviewee_id = ?1", nativeQuery = true)
//    int findReceivedCommentLikes(Long userId);
}
