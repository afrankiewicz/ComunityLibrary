package com.agular.hello.commentLike;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeModel, Long> {

    List<CommentLikeModel> getCommentLikesByCommentId(Long commentId);
}
