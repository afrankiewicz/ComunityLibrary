package com.agular.hello.repositiry;

import com.agular.hello.entity.BookModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookModel, Long> {
    boolean existsByIsbn(String isbn);
    List<BookModel> getBooksByOwnerEmail(String email);
    List<BookModel> getBooksByBorrowerEmail(String email);

    @Query(value = "SELECT * FROM books WHERE borrower_id IS null AND owner_id != ?1",
            nativeQuery = true)
    List<BookModel> getAllAvailable(Long ownerId);

}
