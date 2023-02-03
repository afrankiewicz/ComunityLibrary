package com.agular.hello.comment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<CommentModel, Long> {

    CommentModel findByAuthorIdAndRevieweeId(Long authorId, Long revieweeId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE comments SET text = ?1 WHERE id = ?2 AND author_id = ?3",
            nativeQuery = true)
    int updateCommentText(String text, Long commentId, Long authorId);
}
